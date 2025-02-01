package nepjr.nepgya;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class JDADiscordMessageEvent extends ListenerAdapter
{
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		if(Nepgya.server.isServerRunning())
		{
			if(event.getChannel().getId().equals(BotConfig.botInfo.mcChannelId))
			{
				if(event.getAuthor().isBot() == false)
				{
					Nepgya.server.getPlayerList().sendMessage(new TextComponentString
									(TextFormatting.WHITE + "[" + TextFormatting.DARK_AQUA + "Discord " +
									 TextFormatting.WHITE + event.getAuthor().getEffectiveName() + "] " 
									 + event.getMessage().getContentStripped()));
				}
			}
		}
	}
}
