package com.exile.commands;

import org.slf4j.Logger;

import com.exile.commands.annotations.Command;
import com.exile.commands.annotations.CommandOption;
import com.exile.commands.annotations.CommandOptions;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class DiscordCommandHandlers {
    @Command(
        name = "hello",
        description = "Say hello!",
        options = @CommandOptions(value ={
            @CommandOption(name = "message", description = "The message you want to say")
        })
    )
    public static void echo(SlashCommandInteractionEvent event, Logger logger) {
        String message = event.getOption("message").getAsString();
        event.reply(String.format("%s", message)).queue();
    }

    @Command(
        name = "add",
        description = "Add two numbers",
        options = @CommandOptions(value ={
            @CommandOption(name = "num1", type = "integer", description = "The first number"),
            @CommandOption(name = "num2", type = "integer", description = "The second number")
        })
    )
    public static void add(SlashCommandInteractionEvent event, Logger logger) {
        Integer num1 = event.getOption("num1").getAsInt();
        Integer num2 = event.getOption("num2").getAsInt();
        event.reply(String.format("%d", num1 + num2)).queue();
    }
}
