package nepjr.nepgya;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

@Mod.EventBusSubscriber(modid = BotTags.MODID)
public class MCEvents 
{
    @SubscribeEvent
    public static void messageSent(ServerChatEvent event)
    {
    	Nepgya.api.getTextChannelById(BotConfig.botInfo.mcChannelId).sendMessage("[" + event.getUsername() + "] " + event.getMessage()).queue();
    }
    
    @SubscribeEvent
    public static void playerLeft(PlayerLoggedOutEvent event)
    {
    	Nepgya.api.getTextChannelById(BotConfig.botInfo.mcChannelId).sendMessage(event.player.getGameProfile().getName() + " left the server!").queue();
    }
    
    @SubscribeEvent
    public static void playerJoined(PlayerLoggedInEvent event)
    {
    	Nepgya.api.getTextChannelById(BotConfig.botInfo.mcChannelId).sendMessage(event.player.getGameProfile().getName() + " joined the server!").queue();
    }
    
    @SubscribeEvent
    public static void playerDeath(LivingDeathEvent event)
    {
    	Entity entity = event.getEntity();
    	
    	if(entity instanceof EntityPlayerMP)
    	{
    		Nepgya.api.getTextChannelById(BotConfig.botInfo.mcChannelId).sendMessage(((EntityPlayerMP) entity).getGameProfile().getName() + " died! Rip Bozo!").queue();
    	}
    }
}
