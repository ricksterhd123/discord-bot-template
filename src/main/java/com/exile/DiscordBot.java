package com.exile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import com.exile.commands.DiscordCommandHandlers;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordBot extends ListenerAdapter {
    private Logger logger;
    private Map<String, Method> handlerMap;

    DiscordBot(Logger logger, Map<String, Method> handlerMap) {
        this.logger = logger;
        this.handlerMap = handlerMap;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User author = event.getInteraction().getUser();
        String eventName = event.getName();

        logger.info("Got event {} from {} ({})", eventName, author.getName(), author.getId());

        Method commandHandlerMethod = handlerMap.get(eventName);

        if (commandHandlerMethod != null) {
            try {
                commandHandlerMethod.invoke(DiscordCommandHandlers.class, event, logger);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                logger.error(ExceptionUtils.getMessage(e));
            }
        } else {
            logger.warn("Cannot find handler for event {}", eventName);
        }
    }
}
