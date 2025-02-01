# Nepgya bot
Discord bot using (JDA)[https://github.com/discord-jda/JDA]

This is a server-side mod for 1.12.2 (at the moment) that allows you to have a discord chat be relayed to a minecraft server, and vice versa. It also adds two discord slash commands

/whitelist: lets users in the discord whitelist themselves on the server
/rcon: lets admins configured in the mod's config file execute all operator commands except for op and stop. Don't include the slash when running commands. i.e. /gamemode c will not work, you have to just type gamemode c

You need to create and invite a discord bot yourself. The bot's OAuth2 scopes are bot and applications.commands with the bot permissions being whatever's needed to see what you have set as your minecraft channel. Then you need to copy the bot token and set it in the mod's config file along with the minecraft channel's ID. If for some reason it's not working, create an issue on the Github, if you send me a direct message I'll just ignore it
