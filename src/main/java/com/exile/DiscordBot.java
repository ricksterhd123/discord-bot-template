package com.exile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class DiscordBot extends ListenerAdapter {
    final private static Logger logger = LoggerFactory.getLogger(DiscordBot.class);

    public static void main(String[] args) {
        try {
            Config config = new ConfigBuilder("discordbot.properties").build();
            start(config);
        } catch (Exception error) {
            logger.error(ExceptionUtils.getMessage(error));
        }
    }

    /**
     * Initialize discord bot with config
     * @param config
     */
    private static void start(Config config) {
        logger.info("Initializing discord bot");
        JDA jda = JDABuilder.createLight(config.token, Collections.emptyList())
                .addEventListeners(new DiscordBot())
                .build();

        jda.updateCommands()
                .addCommands(
                        Commands.slash("hello", "Say hello")
                                .addOption(OptionType.STRING, "message", "The message you want to say", true, true))
                .queue();
    }

    /**
     * This method dispatches slash-command events.
     * Each slash-command should have a handler method inside CommandHandlers class,
     * we know which method handles which command using the @Command(name) annotation
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User author = event.getInteraction().getUser();
        String eventName = event.getName();

        logger.info("Got event {} from {} ({})", eventName, author.getName(), author.getId());

        // Find the correct event handler method by searching through
        // DiscordBotCommandHandler annotations
        Class<CommandHandlers> klass = CommandHandlers.class;
        Method commandHandlerMethod = null;

        for (Method method : klass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command commandHandler = method.getAnnotation(Command.class);
                if (commandHandler.name().equals(eventName)) {
                    commandHandlerMethod = method;
                    break;
                }
            }
        }

        // Invoke handler method with event and logger
        if (commandHandlerMethod != null) {
            try {
                commandHandlerMethod.invoke(klass, event, logger);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                logger.error(ExceptionUtils.getMessage(e));
            }
        } else {
            logger.warn("Cannot find handler for event {}", eventName);
        }
    }
}
