package nepjr.nepgya;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = BotTags.MODID, version = BotTags.VERSION, name = BotTags.MODNAME, acceptedMinecraftVersions = "[1.12.2]", serverSideOnly = true, acceptableRemoteVersions = "*")
public class Nepgya {

    public static final Logger LOGGER = LogManager.getLogger(BotTags.MODID);
    public static JDA api;
    public static MinecraftServer server;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		api = JDABuilder.createDefault(BotConfig.botInfo.botToken, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
    			.addEventListeners(new JDAWhitelistCommand())
    			.addEventListeners(new JDADiscordMessageEvent())
    			.addEventListeners(new JDARConCommand())
    			.addEventListeners(new JDAListCommand())
    			.build();
		
    	api.updateCommands().addCommands(
    			Commands.slash("whitelist", "Add yourself to the server's whitelist")
    				.addOption(OptionType.STRING, "username", "Your Minecraft Username. Please only add your username and not others please!"),
    			Commands.slash("rcon", "Send a command to the server. NOTE: You must have the required permissions to run this command!")
    				.addOption(OptionType.STRING, "cmd", "The command to execute"),
    			Commands.slash("list", "List currently online players")
    				.setDefaultPermissions(DefaultMemberPermissions.ENABLED)
    			).queue();
    	
    	api.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
    	api.getPresence().setActivity(Activity.playing("Server is starting..."));
    }
    @EventHandler
    public void syncConfig(ConfigChangedEvent.OnConfigChangedEvent event)
    {
    	if(event.getModID().equals(BotTags.MODID))
    	{
    		ConfigManager.sync(BotTags.MODID, Type.INSTANCE);
    	}
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) 
    {
    	server = FMLCommonHandler.instance().getMinecraftServerInstance();
    	if(BotConfig.botInfo.botToken == "empty")
    	{
    		event.getServer().sendMessage(new TextComponentString("You didn't fill out the bot token!"));
    	}
    	else
    	{	
        	api.getTextChannelById(BotConfig.botInfo.mcChannelId).sendMessage("Server is up! Hello Everyone!").queue();
        	api.getPresence().setStatus(OnlineStatus.ONLINE);
        	api.getPresence().setActivity(Activity.playing("Minecraft on " + BotConfig.botInfo.serverIp)
    				.withState("Players Online: " + Nepgya.server.getPlayerList().getCurrentPlayerCount() + " / " + Nepgya.server.getMaxPlayers()));
    	}
    }
    
    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) 
    {
    	if(BotConfig.botInfo.botToken != "empty")
    	{
    		api.getTextChannelById(BotConfig.botInfo.mcChannelId).sendMessage("Server shutting down. Bye bye!").queue();
    		api.getPresence().setStatus(OnlineStatus.OFFLINE);
    	}
    }
    
    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event) 
    {
    	if(BotConfig.botInfo.botToken != "empty")
    	{
    		api.shutdownNow();
    	}
    }
}
