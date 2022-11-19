package com.exile;

import org.slf4j.Logger;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CommandHandlers {
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
}
