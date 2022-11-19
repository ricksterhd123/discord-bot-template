package com.exile;

import java.util.Collections;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
        JDA jda = JDABuilder.createLight(config.token, Collections.emptyList())
            .addEventListeners(new DiscordBot())
            .build();
        jda.upsertCommand("ping", "Calculate ping of the bot").queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        if (!event.getName().equals("ping")) return;

        long time = System.currentTimeMillis();

        event.reply("Pong!").setEphemeral(true)
            .flatMap(v -> event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time))
            .queue();
    }
}
