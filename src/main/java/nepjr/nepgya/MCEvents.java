package nepjr.nepgya;

import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.entities.Activity;
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
    	if(Nepgya.api.getStatus() == Status.CONNECTED)
    	{
    		Nepgya.api.getTextChannelById(BotConfig.botInfo.mcChannelId)
			.sendMessage("[" + event.getUsername() + "] " + event.getMessage()).queue();
    	}
    }
    
    @SubscribeEvent
    public static void playerLeft(PlayerLoggedOutEvent event)
    {
    	if(Nepgya.api.getStatus() == Status.CONNECTED)
    	{
    		Nepgya.api.getTextChannelById(BotConfig.botInfo.mcChannelId).sendMessage(event.player.getGameProfile().getName() + " left the server!").queue();
        	
        	// If you want to know why down below we subtract the current player count by 1 for some reason it doesn't properly update the player count 
        	// when a player disconnects, even though it works when they join. So my ingenious solution is to subtract by one. 
        	Nepgya.api.getPresence().setActivity(Activity.playing("Minecraft on " + BotConfig.botInfo.serverIp)
    				.withState("Players Online: " + (Nepgya.server.getPlayerList().getCurrentPlayerCount() - 1) + " / " + Nepgya.server.getMaxPlayers()));
    	}
    }
    
    @SubscribeEvent
    public static void playerJoined(PlayerLoggedInEvent event)
    {
    	if(Nepgya.api.getStatus() == Status.CONNECTED)
    	{
    	   	Nepgya.api.getTextChannelById(BotConfig.botInfo.mcChannelId).sendMessage(event.player.getGameProfile().getName() + " joined the server!").queue();
        	
        	Nepgya.api.getPresence().setActivity(Activity.playing("Minecraft on " + BotConfig.botInfo.serverIp)
    				.withState("Players Online: " + Nepgya.server.getPlayerList().getCurrentPlayerCount() + " / " + Nepgya.server.getMaxPlayers()));
    	}
    }
    
    @SubscribeEvent
    public static void playerDeath(LivingDeathEvent event)
    {
    	if(Nepgya.api.getStatus() == Status.CONNECTED)
    	{
        	Entity entity = event.getEntity();
        	
        	if(entity instanceof EntityPlayerMP)
        	{
        		Nepgya.api.getTextChannelById(BotConfig.botInfo.mcChannelId).sendMessage(((EntityPlayerMP) entity).getGameProfile().getName() + " died! Rip Bozo!").queue();
        	}
    	}
    }
}
