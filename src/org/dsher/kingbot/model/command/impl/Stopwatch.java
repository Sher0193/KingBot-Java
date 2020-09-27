package org.dsher.kingbot.model.command.impl;

import org.dsher.kingbot.model.command.Command;
import org.dsher.kingbot.utils.Utils;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Stopwatch extends Command {

	public Stopwatch(String command, String[] args, MessageChannel channel, User author) {
		super(command, args, channel, author);
	}
	
	private class Timer implements Runnable {
		
		private Message msg;
		private int time;
		
		private Timer(Message msg, int time) {
			this.msg = msg;
			this.time = time;
		}

		@Override
		public void run() {
			for (; time >= 0; time -= 5) {
				msg.editMessage(time + "").queue();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}
			}
			
		}
		
	}

	@Override
	public void run() {
		if (args.length > 0 && Utils.isNumeric(args[0])) {
			int time = Integer.parseInt(args[0]);
			if (time % 5 == 0 && time <= 160) {
				channel.sendMessage(time + "").queue(response -> {
					Timer timer = new Timer(response, time);
					Thread thread = new Thread(timer);
					thread.start();
				});
				return;
			}
		}
		channel.sendMessage("Time must be a denomination of 5, greater than 0").queue();
		return;
	}

}
