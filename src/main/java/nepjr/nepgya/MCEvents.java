package nepjr.nepgya;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.entities.Activity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class MCEvents
{   
    @SubscribeEvent
    public void playerLeft(PlayerLoggedOutEvent event)
    {
    	if(Nepgya.api.getStatus() == Status.CONNECTED)
    	{
    		Nepgya.api.getTextChannelById(Nepgya.cfgChannel).sendMessage(event.player.getGameProfile().getName() + " left the server!").queue();
        	
        	// If you want to know why down below we subtract the current player count by 1 for some reason it doesn't properly update the player count 
        	// when a player disconnects, even though it works when they join. So my ingenious solution is to subtract by one. 
        	Nepgya.api.getPresence().setActivity(Activity.playing("Minecraft on " + Nepgya.cfgIp)
    				.withState("Players Online: " + (Nepgya.server.getCurrentPlayerCount() - 1) + " / " + Nepgya.server.getMaxPlayers()));
    	}
    }
    
    @SubscribeEvent
    public void playerJoined(PlayerLoggedInEvent event)
    {
    	if(Nepgya.api.getStatus() == Status.CONNECTED)
    	{
    	   	Nepgya.api.getTextChannelById(Nepgya.cfgChannel).sendMessage(event.player.getGameProfile().getName() + " joined the server!").queue();
        	
        	Nepgya.api.getPresence().setActivity(Activity.playing("Minecraft on " + Nepgya.cfgIp)
    				.withState("Players Online: " + Nepgya.server.getCurrentPlayerCount() + " / " + Nepgya.server.getMaxPlayers()));
    	}
    }
}