package com.exile;

import java.lang.reflect.Method;
import java.util.Collections;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class Main {
    final private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static OptionType getOptionType(String typeName) {
        switch (typeName) {
            default:
                return OptionType.STRING;
        }
    }

    private static DiscordCommands getCommands() {
        DiscordCommands discordCommands = new DiscordCommands();
        Class<CommandHandlers> klass = CommandHandlers.class;

        for (Method method : klass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command commandHandler = method.getAnnotation(Command.class);
                CommandOption[] options = commandHandler.options().value();
                SlashCommandData data = Commands.slash(commandHandler.name(), commandHandler.description());

                for (CommandOption option : options) {
                    data.addOption(getOptionType(option.type()), option.name(), option.description(), true, true);
                }

                discordCommands.commands.add(data);
                discordCommands.handlerMap.put(commandHandler.name(), method);
            }
        }

        return discordCommands;
    }

    public static void main(String[] args) {
        final String configFilePath = "discordbot.properties";
        try {
            Config config = new ConfigBuilder(configFilePath).build();
            DiscordCommands discordCommands = getCommands();
            DiscordBot bot = new DiscordBot(logger, discordCommands.handlerMap);

            JDA jda = JDABuilder.createLight(config.token, Collections.emptyList())
                    .addEventListeners(bot)
                    .build();

            jda.updateCommands()
                    .addCommands(discordCommands.commands)
                    .queue();
        } catch (Exception error) {
            logger.error(ExceptionUtils.getMessage(error));
        }
    }
}
