package com.exile;

import java.util.Collections;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import com.exile.commands.DiscordCommands;

public class Main {
    final private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final String configFilePath = "discordbot.properties";
        try {
            Config config = new ConfigBuilder(configFilePath).build();
            DiscordCommands discordCommands = DiscordCommands.get();
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
