package nepjr.nepgya;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JDAListCommand extends ListenerAdapter 
{
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
	{
		if(event.getName().equals("list"))
		{
			event.reply("Players Online: " + Nepgya.server.getPlayerList().getFormattedListOfPlayers(false)).setEphemeral(true).queue();
		}
	}
}
