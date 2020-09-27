package org.dsher.kingbot.model.command.impl;

import org.dsher.kingbot.model.command.Command;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Ping extends Command {

	public Ping(String command, String[] args, MessageChannel channel, User author) {
		super(command, args, channel, author);
	}

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		channel.sendMessage("Pong!") /* => RestAction<Message> */
		.queue(response /* => Message */ -> {
			response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
		});
		return;
	}

}
