package org.dsher.kingbot.model.command.impl;

import java.awt.Color;

import org.dsher.kingbot.Bot;
import org.dsher.kingbot.model.command.Command;
import org.dsher.kingbot.model.command.CommandHandler;
import org.dsher.kingbot.model.command.CommandHandler.CommandData;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Help extends Command {


	public Help(String command, String[] args, MessageChannel channel, User author) {
		super(command, args, channel, author);
	}

	@Override
	public void run() {
		if (args.length > 0) {
			for (CommandData c : CommandHandler.CommandData.values()) {
				if (c.isMatchingCommand(args[0])) {
					channel.sendMessage(c.getHelpEntry()).queue();
					return;
				}
			}
			channel.sendMessage("No command found matching \"" + args[0] + "\".").queue();
			return;
		} else {
			String commandList = "";
			for (CommandData c : CommandHandler.CommandData.values()) {
				int i;
				for (i = 0; i < c.getCommands().length; i++) {
					commandList += Bot.getBotInstance().getPrefix() + c.getCommands()[i];
					if (i < c.getCommands().length - 1)
						commandList += i == 0 ? " (" : ", ";
				}
				commandList += (i > 1 ? ")" : "") + "\n";
			}
			String prefix = Bot.getBotInstance().getPrefix();
			EmbedBuilder builder = new EmbedBuilder()
					.setTitle("Commands")
					.setColor(Color.MAGENTA)
					.setDescription(commandList)
					.setFooter("Type \"" + prefix + "help [command]\" to learn more about a command, eg \"" + prefix + "help help\".");;
					channel.sendMessage(builder.build()).queue();
					return;
		}
		
	}

}
