package com.exile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscordbotConfigBuilder {
    final private static Configurations configs = new Configurations();
    final private static Logger logger = LoggerFactory.getLogger(DiscordBot.class);

    private String filePath;

    DiscordbotConfigBuilder(String filePath) {
        this.filePath = filePath;
    }

    private File getFile() throws Exception {
        try {
            File configFile = new File(filePath);

            if (configFile.createNewFile()) {
                DiscordbotConfig defaultConfig = new DiscordbotConfig();
                FileWriter fw = new FileWriter(configFile);
                fw.write(defaultConfig.toString());
                fw.flush();
                fw.close();
            }

            return configFile;
        } catch (IOException error) {
            logger.error(ExceptionUtils.getMessage(error));
            throw new Exception("Failed to initialize or get config file");
        }
    }

    public DiscordbotConfig build() throws Exception {
        try {
            DiscordbotConfig discordbotConfig = new DiscordbotConfig();

            File configFile = getFile();
            PropertiesConfiguration config = configs.properties(configFile);
            discordbotConfig.token = config.getString("token");

            return discordbotConfig;
        } catch (ConfigurationException cex) {
            logger.error(ExceptionUtils.getStackTrace(cex));
            throw new Exception("Failed to load config from file");
        }
    }
}
