package org.dsher.kingbot.model.command;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public abstract class Command implements Runnable {
	
	protected String command;
	protected String[] args;
	protected MessageChannel channel;
	protected User author;
	
	public Command(String command, String[] args, MessageChannel channel, User author) {
		this.command = command;
		this.args = args;
		this.channel = channel;
		this.author = author;
	}

}
