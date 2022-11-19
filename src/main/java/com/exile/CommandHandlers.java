package com.exile;

import org.slf4j.Logger;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CommandHandlers {
    @Command(name = "hello")
    public static void sayHello(SlashCommandInteractionEvent event, Logger logger) {
        User author = event.getInteraction().getUser();
        event.reply(String.format("%s said %s", author.getName(), event.getOption("message").getAsString())).queue();
    }
}
