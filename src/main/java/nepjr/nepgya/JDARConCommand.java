package nepjr.nepgya;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.server.dedicated.DedicatedServer;

public class JDARConCommand extends ListenerAdapter 
{
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
	{
		if(event.getName().equals("rcon"))
		{
			event.deferReply(true).queue();
			if(ArrayUtils.contains(BotConfig.botInfo.admins, event.getUser().getId()))
			{
				if(Nepgya.server instanceof DedicatedServer)
				{
					if (event.getOption("cmd") == null)
					{
						event.getHook().sendMessage("Something went wrong! Did you forget to supply the command?").queue();
					}
					if(event.getOption("cmd").getAsString().substring(0, 2).equals("op"))
					{
						Nepgya.LOGGER.log(Level.INFO, event.getUser().getEffectiveName() + " has attempted to OP someone!!!!!");
						event.getHook().sendMessage("You cannot OP players in the server. Contact the server admin if you believe this is an error").queue();
					}
					else if(event.getOption("cmd").getAsString().substring(0, 4).equals("stop"))
					{
						Nepgya.LOGGER.log(Level.INFO, event.getUser().getEffectiveName() + " has attempted to stop the server!!!!!");
						event.getHook().sendMessage("You cannot stop the server. Contact the server admin if you believe this is an error").queue();
					}
					else
					{
						DedicatedServer server = (DedicatedServer) Nepgya.server;
						server.addPendingCommand(event.getOption("cmd").getAsString(), server);
						Nepgya.LOGGER.log(Level.INFO, event.getUser().getEffectiveName() + " has executed the following: " + event.getOption("cmd").getAsString());
						event.getHook().sendMessage("Command sent to the server!").queue();
					}
				}
			}
			else
			{
				Nepgya.LOGGER.log(Level.INFO, event.getUser().getEffectiveName() + " has attempted to execute the following: " + event.getOption("cmd").getAsString());
				event.getHook().sendMessage("You're not in the Adminlist. Contact the server admin if you believe this is an error").queue();
			}
		}
	}
}
