package nepjr.nepgya;

import org.apache.logging.log4j.Level;

import com.mojang.authlib.GameProfile;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JDAWhitelistCommand extends ListenerAdapter 
{
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
	{
		if(event.getName().equals("whitelist"))
		{
			event.deferReply(true).queue();
			if (event.getOption("username") == null)
			{
				event.getHook().sendMessage("Something went wrong! Did you forget to supply a username?").queue();
			}
			else
			{
				GameProfile gameprofile = Nepgya.server.getPlayerProfileCache().getGameProfileForUsername(event.getOption("username").getAsString());

	            if (gameprofile == null)
	            {
	            	event.getHook().sendMessage("[ERROR] Invalid Username. Try again?").queue();
	            }
	            
	            Nepgya.server.getPlayerList().addWhitelistedPlayer(gameprofile);
	            Nepgya.LOGGER.log(Level.INFO, "Nepgya has added " + gameprofile.getName() + " to the whitelist!");
	            event.getHook().sendMessage("Added to the server whitelist!").queue();
			}
		}
	}
}
