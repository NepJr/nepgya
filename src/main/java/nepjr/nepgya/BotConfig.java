package nepjr.nepgya;

import net.minecraftforge.common.config.Config;

@Config(modid = BotTags.MODID)
public class BotConfig 
{
	@Config.Name("Bot Information")
	public static BotInfo botInfo = new BotInfo();
	
	public static class BotInfo
	{
		@Config.Name("Bot Token")
		@Config.Comment("Set the Token for the bot. DO NOT SHARE THIS CFG FILE WITH THIS FIELD FILLED OUT")
		@Config.RequiresMcRestart
		public String botToken = "empty";
		
		@Config.Name("Minecraft Channel")
		@Config.Comment("Set the channel to put the minecraft chat into, and where to pull discord messages to send to the discord chat")
		public String mcChannelId = "empty";
		
		@Config.Name("Admins")
		@Config.Comment("List the Admin's Discord IDs to allow them to use the RCON command")
		public String[] admins = { "empty", "empty2" };
	}
}
