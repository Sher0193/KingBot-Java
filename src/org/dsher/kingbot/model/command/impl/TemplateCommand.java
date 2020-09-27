package org.dsher.kingbot.model.command.impl;

import org.dsher.kingbot.model.command.Command;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class TemplateCommand extends Command {

	public TemplateCommand(String command, String[] args, MessageChannel channel, User author) {
		super(command, args, channel, author);
	}

	@Override
	public void run() {
		channel.sendMessage("Command successfully executed.").queue();
		return;
	}

}
