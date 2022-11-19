package com.exile.commands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import com.exile.commands.annotations.Command;
import com.exile.commands.annotations.CommandOption;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class DiscordCommands {
    public ArrayList<SlashCommandData> commands;
    public HashMap<String, Method> handlerMap;

    DiscordCommands() {
        this.commands = new ArrayList<SlashCommandData>();
        this.handlerMap = new HashMap<String, Method>();
    }

    private static OptionType getOptionType(String typeName) {
        switch (typeName) {
            case "integer":
                return OptionType.INTEGER;
            default:
                return OptionType.STRING;
        }
    }

    public static DiscordCommands get() {
        DiscordCommands discordCommands = new DiscordCommands();
        Class<DiscordCommandHandlers> klass = DiscordCommandHandlers.class;

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
}
