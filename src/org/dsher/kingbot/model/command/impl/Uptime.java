package org.dsher.kingbot.model.command.impl;

import org.dsher.kingbot.Bot;
import org.dsher.kingbot.model.command.Command;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Uptime extends Command {

	public Uptime(String command, String[] args, MessageChannel channel, User author) {
		super(command, args, channel, author);
	}

	@Override
	public void run() {
		long curTime = System.currentTimeMillis();
	    long uptime = curTime - Bot.getBotInstance().getLaunchTime();

	    int days = (int)Math.floor(uptime / 86400000);
	    uptime %= 86400000;

	    int hours = (int)Math.floor(uptime / 3600000);
	    uptime %= 3600000;

	    int minutes = (int)Math.floor(uptime / 60000);

	    //TODO: format method in utils
	    channel.sendMessage(Bot.getBotInstance().getName() + " has been online for " + (days > 0 ? (days + " day" + (days == 1 ? "" : "s") + ", ") : "") + (hours > 0 ? (hours + " hour" + (hours == 1 ? "" : "s") + ", ") : "") + minutes + " minute" + (minutes == 1 ? ("") : "s") + ".").queue();
	    return;
	}
	

}
