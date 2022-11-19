package com.exile;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot extends ListenerAdapter {
    final private static Logger logger = LoggerFactory.getLogger(DiscordBot.class);

    public static void main(String[] args) {
        try {
            DiscordbotConfig config = new DiscordbotConfigBuilder("discordbot.properties").build();
            start(config);
        } catch (Exception error) {
            logger.error(ExceptionUtils.getMessage(error));
        }
    }

    private static void start(DiscordbotConfig config) {
        logger.info("Initializing discord bot");
        JDA jda = JDABuilder.createDefault(config.token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        jda.addEventListener(new DiscordBot());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild()) {
            String authorName = event.getAuthor().getName();
            Message message = event.getMessage();
            logger.info("[Discord] {} {}", authorName, message.getContentDisplay());
        }
    }
}
