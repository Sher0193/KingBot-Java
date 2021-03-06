package org.dsher.kingbot.model.command.impl;

import org.dsher.kingbot.model.command.Command;
import org.dsher.kingbot.utils.Utils;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Roll extends Command {

	public Roll(String command, String[] args, MessageChannel channel, User author) {
		super(command, args, channel, author);
	}

	private String scattergoriesRoll() {
		char[] validScattergoryLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T', 'W'};
		int roll = Utils.getRandom(0, validScattergoryLetters.length - 1); 
		return ":capital_abcd: **" + validScattergoryLetters[roll] + "**";
	}

	@Override
	public void run() {
		if (args.length > 0 && (args[0].toLowerCase().equals("s") || args[0].toLowerCase().equals("scattergories"))) {
			channel.sendMessage(scattergoriesRoll()).queue();
			return;
		} else {
			int high = 6, low = 1;
			if (args.length > 0 && Utils.isNumeric(args[0])) {
				int arg0 = Integer.parseInt(args[0]);
				if (args.length > 1 && Utils.isNumeric(args[1])) {
					int arg1 = Integer.parseInt(args[1]);
					high = arg0 > arg1 ? arg0 : arg1;
					low = arg0 < arg1 ? arg0 : arg1;
				} else {
					high = arg0 > low ? arg0 : low;
					low = arg0 < low ? arg0 : low;
				}
			}
			int roll = Utils.getRandom(low, high);
			channel.sendMessage("(" + low + " -> " + high + ") :game_die: **" + roll + "**").queue();
			return;
		}
	}

}
