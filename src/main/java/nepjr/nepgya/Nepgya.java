package nepjr.nepgya;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;


@Mod(modid = "nepgya", version = "1.1.2", name = "nepgya", acceptedMinecraftVersions = "[1.7.10]", acceptableRemoteVersions = "*")
public class Nepgya {

    public static final Logger LOGGER = LogManager.getLogger("nepgya");
    public static JDA api;
    public static Configuration config;
    public static MinecraftServer server;
    
    public static String cfgBotToken;
    public static String[] cfgAdmins;
    public static String cfgChannel;
    public static String cfgIp;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	File configFile = event.getSuggestedConfigurationFile();
    	config = new Configuration(configFile);
    	
    	config.load();
    	
    	cfgBotToken = config.get(Configuration.CATEGORY_GENERAL, "botToken", "0", "Sets the bot token for the mod to use").getString();
    	cfgAdmins = config.getStringList("admins", Configuration.CATEGORY_GENERAL, new String[] {"USER0", "USER1"}, "Set the Discord User ID of those you want to have access to the RCON command");
    	cfgChannel = config.get(Configuration.CATEGORY_GENERAL, "channel", "0", "Sets the channel for the bot to send messages to").getString();
    	cfgIp = config.get(Configuration.CATEGORY_GENERAL, "serverIp", "127.0.0.1:25565", "Sets the IP for the bot to display").getString();
    	
    	if(config.hasChanged())
    	{
    		config.save();
    	}
    	
		try 
		{
			api = JDABuilder.createDefault(cfgBotToken, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
	    			.addEventListeners(new JDAWhitelistCommand())
	    			.addEventListeners(new JDADiscordMessageEvent())
	    			.addEventListeners(new JDARConCommand())
	    			.addEventListeners(new JDAListCommand())
	    			.build();
			
			api.updateCommands().addCommands(
        			Commands.slash("whitelist", "Add yourself to the server's whitelist")
						.setDefaultPermissions(DefaultMemberPermissions.ENABLED)
        				.addOption(OptionType.STRING, "username", "Your Minecraft Username. Please only add your username and not others please!"),
        			Commands.slash("rcon", "Send a command to the server. NOTE: You must have the required permissions to run this command!")
        				.addOption(OptionType.STRING, "cmd", "The command to execute"),
        			Commands.slash("list", "List currently online players")
        				.setDefaultPermissions(DefaultMemberPermissions.ENABLED)
        			).queue();
        	
        	api.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        	api.getPresence().setActivity(Activity.playing("Server is starting..."));
		} 
		catch (InvalidTokenException e)
		{
			LOGGER.log(Level.ERROR, "Could not connect to Discord! Perhaps your bot token is invalid?");
		}
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	FMLCommonHandler.instance().bus().register(new MCEvents());
    	MinecraftForge.EVENT_BUS.register(new MCEventsButTwo());
    }
    
    @EventHandler
    public void syncConfig(ConfigChangedEvent.OnConfigChangedEvent event)
    {
    	if(event.modID == "nepgya")
    	{
    		config.load();
    	}
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartedEvent event) 
    {
    	server = FMLCommonHandler.instance().getMinecraftServerInstance();
    	try
    	{
    		api.getTextChannelById(cfgChannel).sendMessage("Server is up! Hello Everyone!").queue();
    		api.getPresence().setStatus(OnlineStatus.ONLINE);
    		api.getPresence().setActivity(Activity.playing("Minecraft on " + cfgIp)
    				.withState("Players Online: " + Nepgya.server.getCurrentPlayerCount() + " / " + Nepgya.server.getMaxPlayers()));
    	}
    	catch (NullPointerException e)
    	{
    		// why do I have to do this. If I don't do this it doesn't crash when starting or stopping the server if no token is provided
    		// Perhaps I'm doing something wrong, or forge is very dumb. the apiEnabled boolean just straight up doesn't work here
    	}
    }
    
    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) 
    {
    	try
    	{
    		api.getTextChannelById(cfgChannel).sendMessage("Server shutting down. Bye bye!").queue();
    		api.getPresence().setStatus(OnlineStatus.OFFLINE);
    	}
    	catch (NullPointerException e)
    	{
    		// *sigh*
    	}
    }
    
    
    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event) 
    {
    	try
    	{
    		api.shutdownNow();
    	}
    	catch (NullPointerException e)
    	{
    		// Again, why?
    	}
    }
}
