package nepjr.nepgya;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.util.text.TextComponentString;

public class JDADiscordMessageEvent extends ListenerAdapter
{
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		if(Nepgya.server.isServerRunning())
		{
			if(event.getChannel().getId().equals(BotConfig.botInfo.mcChannelId))
			{
				Nepgya.server.getPlayerList().sendMessage(new TextComponentString("[Discord " + event.getAuthor().getEffectiveName() + "] " + event.getMessage().getContentStripped()));
			}
		}
	}
}
