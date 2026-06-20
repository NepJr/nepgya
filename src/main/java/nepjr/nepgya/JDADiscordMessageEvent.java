package nepjr.nepgya;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class JDADiscordMessageEvent extends ListenerAdapter
{
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		if(Nepgya.server.isServerRunning())
		{
			if(event.getChannel().getId().equals(Nepgya.cfgChannel))
			{
				if(event.getAuthor().isBot() == false)
				{
					Nepgya.server.getConfigurationManager().sendChatMsg(new ChatComponentText
							(EnumChatFormatting.WHITE + "[" + EnumChatFormatting.DARK_AQUA + "Discord " +
									EnumChatFormatting.WHITE + event.getAuthor().getEffectiveName() + "] " 
									 + event.getMessage().getContentStripped()));
				}
			}
		}
	}
}
