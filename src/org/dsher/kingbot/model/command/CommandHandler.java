package org.dsher.kingbot.model.command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.dsher.kingbot.Bot;
import org.dsher.kingbot.model.command.impl.Help;
import org.dsher.kingbot.model.command.impl.Ping;
import org.dsher.kingbot.model.command.impl.Roll;
import org.dsher.kingbot.model.command.impl.Score;
import org.dsher.kingbot.model.command.impl.Scoreboard;
import org.dsher.kingbot.model.command.impl.Stopwatch;
import org.dsher.kingbot.model.command.impl.Uptime;
import org.dsher.kingbot.model.command.impl.Werewolf;
import org.dsher.kingbot.model.content.werewolf.Game;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class CommandHandler {
	
	public static enum CommandData {
		
		
		HELP(Help.class, new String[] {"help"},  "Use \" [PREFIX]help\" to view an explanation for the various functions of [NAME]. Use \"[PREFIX]help [command]\" to view an explanation for that command."),
		PING(Ping.class, new String[] {"ping"}, "[NAME] will measure and output the time it takes in milliseconds to receive a response from its server."),
		ROLL(Roll.class, new String[] {"roll"}, "``[PREFIX]roll x`` will return a random number between 1 and x. If no number is specified, ``[PREFIX]roll`` returns a number between 1 and 6."),
		SCORE(Score.class, new String[] {"score", "half", "penalty", "penaltyhalf", "sc"}, "**BASICS**\nUse \"[PREFIX]score\" to add points for a list of users to the channel's scoreboard. A scoreboard must have been created for the channel with \"[PREFIX]scoreboard create\".\n\"[PREFIX]score\" requires a comma-separated list of names, such as \"[PREFIX]score Crosby, Stills, Nash, Young\".\n**MULTIPLE POINT VALUES**\nBy default, \"[PREFIX]score\" will award one point. In addition, \"[PREFIX]half\" will award 0.5 points, \"[PREFIX]penalty\" will award -1 point, and \"[PREFIX]penaltyhalf\" will award -0.5 points.\nYou may precede a list of names with a number and colon to award that value to the following list, such as \"[PREFIX]score 5: Crosby, Stills, Nash, Young\", which will award 5 points. You may mix point values in a single command, such as \"[PREFIX]score 5: Crosby, Stills 10: Nash, Young\", which will award 5 to Crosby and Stills, then 10 to Nash and Young.\""),
		SCOREBOARD(Scoreboard.class, new String[] {"scoreboard", "sb"}, "**BASICS**\n\"[PREFIX]scoreboard\" displays the current score for this channel's scoreboard.\n**CREATING A SCOREBOARD FOR THE CHANNEL**\nSimply use the command \"[PREFIX]scoreboard create\" to create a scoreboard. If one exists already, this command will overwrite the previous board.\n**CLEARING THE SCOREBOARD**\nThe current scoreboard may be erased from this channel with \"[PREFIX]scoreboard clear\"."),
		STOPWATCH(Stopwatch.class, new String[] {"stopwatch", "sw", "timer"}, "``[PREFIX]sw [number]`` or ``[PREFIX]stopwatch [number]`` will begin a countdown starting at the specified number, and decrementing by 5 every 5 seconds."),
		UPTIME(Uptime.class, new String[] {"uptime", "up"}, "Outputs how long [NAME] has been online."),
		WEREWOLF(Werewolf.class, new String[] {"werewolf", "ww"}, "**ONE NIGHT ULTIMATE WEREWOLF**\nA hidden-role style game. [NAME] will manage assigning roles, and walk the players through the night. For full ONUW rules, please see https://www.fgbradleys.com/rules/rules2/OneNightUltimateWerewolf-rules.pdf.\n**ARGUMENTS**\n``[PREFIX]ww create`` to create a new game instance for the current channel.\n``[PREFIX]ww end`` to remove said instance.\n``[PREFIX]ww join`` to join a game (that hasn't begun yet)\n``[PREFIX]ww leave`` to leave said game\n``[PREFIX]ww start`` to begin a game, once at least three players have joined.");
		
		
		private final String helpEntry;
		private final String[] commands;
		private final Class<? extends Command> commandClass;
		
		CommandData(Class<? extends Command> c, String[] commands, String helpEntry) {
			this.helpEntry = helpEntry;
			this.commands = commands;
			this.commandClass = c;
		}
		
		public boolean isMatchingCommand(String command) {
			for (int i = 0; i < commands.length; i++) {
				if (command.equals(commands[i]))
					return true;
			}
			return false;
		}
		
		public String getHelpEntry() {
			return helpEntry.replaceAll("\\[NAME\\]", Bot.getBotInstance().getName()).replaceAll("\\[PREFIX\\]", Bot.getBotInstance().getPrefix());
		}

		public String[] getCommands() {
			return commands;
		}
		
		public Class<? extends Command> getCommandClass() {
			return commandClass;
		}
		
	}

	/***
	 * Returns a command object with a matching command string.
	 * @param strCmd The string to match to a command.
	 * @return Command object.
	 */
	private static CommandData getCommand(String strCmd) {
		for (CommandData c : CommandData.values()) {
			if (c.isMatchingCommand(strCmd))
				return c;
		}
		return null;
	}

	public static boolean handleUnprefixedCommand(Message message) {
		// ONUW Handling
		for (Game game : Bot.getBotInstance().getWerewolfHandler().getGamesByUser(message.getAuthor())) {
			if (game != null) {
				game.acceptPrivateInput(message.getAuthor(), message.getContentRaw(), message.getChannel());
			}
		}
		return false;
	}

	public static boolean handleCommand(String command, String[] args, MessageChannel channel, User author) {
		final CommandData commandData = getCommand(command);
		if (command != null) {
			
			Constructor<? extends Command> constructor;
			try {
				constructor = commandData.getCommandClass().getConstructor(String.class, String[].class, MessageChannel.class, User.class);
				Command c = constructor.newInstance(command, args, channel, author);
				
				Thread thread = new Thread(c);
				thread.start();
				
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				return false;
			}
		}
		return false;
	}

}
