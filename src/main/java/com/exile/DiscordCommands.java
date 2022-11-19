package com.exile;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class DiscordCommands {
    public ArrayList<SlashCommandData> commands;
    public HashMap<String, Method> handlerMap;

    DiscordCommands() {
        this.commands = new ArrayList<SlashCommandData>();
        this.handlerMap = new HashMap<String, Method>();
    }
}
