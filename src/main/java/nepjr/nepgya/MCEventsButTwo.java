package nepjr.nepgya;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.dv8tion.jda.api.JDA.Status;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class MCEventsButTwo
{
	@SubscribeEvent
    public void messageSent(ServerChatEvent event)
    {
    	if(Nepgya.api.getStatus() == Status.CONNECTED)
    	{
    		Nepgya.api.getTextChannelById(Nepgya.cfgChannel)
			.sendMessage("[" + event.username + "] " + event.message).queue();
    	}
    }
	
	@SubscribeEvent
    public void playerDeath(LivingDeathEvent event)
    {
    	if(Nepgya.api.getStatus() == Status.CONNECTED)
    	{
        	Entity entity = event.entity;
        	
        	if(entity instanceof EntityPlayerMP)
        	{
        		Nepgya.api.getTextChannelById(Nepgya.cfgChannel).sendMessage(((EntityPlayerMP) entity).getGameProfile().getName() + " died! Rip Bozo!").queue();
        	}
    	}
    }
}