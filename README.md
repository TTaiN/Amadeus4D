# Amadeus4D

<p align="center">
  <img src="https://raw.githubusercontent.com/TTaiN/Amadeus4D/master/images/transparent_logo.png">
</p>

Amadeus is a  music bot developed 4D ("for [Discord](https://discordapp.com/)") in Java 8, which utilizes the [Discord4J Java interface](https://github.com/austinv11/Discord4J). It plays music requested by users over VoIP, allowing them to listen to music together.

It is designed for scalablity and portability, and thus utilizes a [MySQL](https://github.com/mysql/mysql-server) database server for persistence (using [HikariCP](https://github.com/brettwooldridge/HikariCP) for connection pooling) and [lavaplayer](https://github.com/sedmelluq/lavaplayer) for audio streaming. 

More information can be found at [my site.](http://ttain.tk/) 

### Features
 
* Allows users to play music from popular, standard websites. Most notably, YouTube and SoundCloud.
* Supports direct YouTube and SoundCloud playlists, which allows users to queue many songs at once.
* Provides a rich command feature set for easy control over the music playlist.
	* You can find the list of commands in **/prompts/music_commands.txt**.
* Contains an intuitive permission/control system for easy user and administration management.
* Equipped with miscellaneous features and commands (such as dice/number rolling.)
* Quick setup if you already have a MySQL server running.
	* However, if you don't, it's still pretty fast.
* Small memory footprint and overheads thanks to the usage of connecting pooling and lavaplayer.
	* Translation: small and fast.


# Setup

### Step 1
Create a database on your MySQL server using the schema provided in **/database/table.sql**.

Then, set your status as developer, insert your Discord bot token, and provide your YouTube API key running the following SQL statements, substituting the appropriate values:
```
INSERT INTO credentials(token, active) VALUES ("YOUR_BOT_TOKEN", true);

INSERT INTO api(purpose, api_key, active) VALUES ("YouTube", "YOUR_API_KEY", true);

INSERT INTO developers(user_id) VALUES ("YOUR_DISCORD_USER_ID");
``` 

### Step 2

Create a file named **database.config** in the path **/config**. Fill in the following template with your database credentials, then save the file:

```
jdbcUrl=jdbc:mysql://localhost/YOUR_DB_NAME?autoReconnect=true&useSSL=false
maximumPoolSize=5
poolName=Amadeus
dataSource.user=YOUR_DB_USERNAME
dataSource.password=YOUR_DB_PASSWORD
dataSource.databaseName=YOUR_DB_NAME
dataSource.portNumber=3306
dataSource.serverName=localhost
````

* This configuration assumes you are running the database server on the same local machine. You can edit the configuration to use a remote MySQL server instead.

### Step 3

Build and run.

If you are having trouble running Amadeus on your JVM, try this command:
```
java -cp Amadeus.jar ttain.amadeus.discord.Amadeus
```

* Any appropriate errors during startup will be printed on the console at runtime.

# FAQ

-> How do I make the bot respond to my music commands?
* The bot will not listen to commands in a channel unless you tell it to do so. Simply have the guild owner or a developer type in "**/listen**" without the quotes in the channel you want. "**/unlisten**" can be used to achieve the opposite effect. Prevents spam!


-> How do I give someone permission to use the music player?
* By default, guild owners and developers always have access. To give someone DJ access, have a guild owner or moderator type in the command "**/addDJ @User**" in a channel. Similarly, "**/removeDJ @User**" achieves the opposite effect.
* An example can be found below:

	![Adding a DJ example.](http://i.imgur.com/1HOrUWI.png)


-> How do I give someone permission to add or remove DJs?
* Initially, only the guild owner can add or remove DJs. However, the guild owner can designate moderators as well. Have the guild owner type in the command "**/addMod @User**" in a channel. Similarly, "**/removeMod @User**" achieves the opposite effect.
* An example can be found below:

	![Adding a Mod example.](http://i.imgur.com/cA2acMM.png)


-> What are the commands? I have no idea how to use the bot.
* By default, you can type in **/commands** or **$commands** in any channel to get the list of commands for the music player and miscellaneous features respectively.


-> How do I change the command precursor?  **$** and **/** are too annoying!
* You can edit these in the source files. More specifically, edit the variable named **key** located in **ttain.amadeus.discord.modules.music.MusicManager** and **ttain.amadeus.discord.modules.general.GeneralManager**.


-> How do I make my bot logout?
* Private message your bot and type "**!terminate**" without the quotes. Additionally, through private messages, you can also change the bot's avatar, status, etc. Refer to **ttain.amadeus.discord.modules.administration.AdministrationManager**.
* Make sure you set yourself as a developer in the database, which you've likely already done as part of the setup.

-> What does Amadeus mean?
* The term [**Amadeus**](http://steins-gate.wikia.com/wiki/Amadeus) references the name of a memory storage and artificial intelligence system developed by [Kurisu Makise](http://steins-gate.wikia.com/wiki/Makise_Kurisu) from [Steins;Gate 0](https://en.wikipedia.org/wiki/Steins;Gate_0). It was selected as the name of my development team.

# Dependencies

You can also view these in the project's Maven **pom.xml** file.

* [Discord4J](https://github.com/austinv11/Discord4J) by austinv11.
* [lavaplayer](https://github.com/sedmelluq/lavaplayer) by segmelluq.
* [HikariCP](https://github.com/brettwooldridge/HikariCP) by brettwooldridge.
* [logback](https://github.com/qos-ch/logback) by qos-ch.
* [joda-time](https://github.com/JodaOrg/joda-time) by JodaOrg.
* [mysql-connector-j](https://github.com/mysql/mysql-connector-j) by MySQL.
* [gson](https://github.com/google/gson) by Google.

# Special Thanks

Special thanks to [Uebios](https://github.com/Uebios) for hosting the initial implementation of my bot, as well as providing some snippets of code that were migrated to this implementation.

Special thanks to the members of the Amadeus development team, who taught me how to design programs for scability and maintainability in the future, as well as giving me the OK to release.
