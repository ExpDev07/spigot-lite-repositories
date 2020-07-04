# spigot-lite-repositories

[![Maven Central](https://img.shields.io/maven-central/v/com.github.expdev07/spigot-lite-repositories.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.expdev07%22%20AND%20a:%22spigot-lite-repositories%22)

> Tired of creating your own systems for saving data/objects such as players in your plugins? This library has got you covered. Simply 
add this library in your plugin, and you're good to go!

## Support me

<p>
    <a href='https://ko-fi.com/C1C510DUQ' target='_blank'>
	<img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi3.png?v=2' border='0' alt='Buy Me a Coffee at ko-fi.com' />
    </a>
</p>

## Installation

Installing and using this library is very easy, simply include the maven dependency and add the shade plugin. Alternatively, 
you can also [download the jar from maven.org](https://search.maven.org/search?q=g:com.github.expdev07%20AND%20a:spigot-lite-repositories).

### Including dependency

```xml
<dependency>
    <groupId>com.github.expdev07</groupId>
    <artifactId>spigot-lite-repositories</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Shading

To use the library, you'll have to include it in your final plugin jar. This can be achieved using shading.

```xml
<plugin>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.1.1</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### Building

Now build your plugin with ``mvn clean package``.


### Example pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.github.expdev07</groupId>
    <artifactId>invrestore-remastered</artifactId>
    <version>1.0-SNAPSHOT</version>

	<build>
        <plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
			<plugin>
				<artifactId>maven-shade-plugin</artifactId>
				<version>3.1.1</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>shade</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>

	<repositories>
		<repository>
			<id>spigot-repo</id>
			<url>https://hub.spigotmc.org/nexus/content/repositories/snapshots/</url>
		</repository>
	</repositories>

	<dependencies>
		<!--Spigot API-->
		<dependency>
			<groupId>org.spigotmc</groupId>
			<artifactId>spigot-api</artifactId>
			<version>1.15.2-R0.1-SNAPSHOT</version>
			<scope>provided</scope>
		</dependency>
		<!-- Lite Repositories -->
		<dependency>
			<groupId>com.github.expdev07</groupId>
			<artifactId>spigot-lite-repositories</artifactId>
			<version>1.0.0</version>
		</dependency>
	</dependencies>
	
</project>
```

## Usage

At the moment, only storing objects in JSON is supported. Look at an example below for storing a custom user.


### The object

Every object that you store should implement ``Identifiable<ID>``. This gives the object an id which it can be identified by. ``ID`` is 
the type of id and can be e.g ``Integer``, ``UUID``, ``String``, etc. In the example below, our ``CustomPlayer`` uses a ``UUID`` for its 
id.

```java
public class CustomPlayer implements Identifiable<UUID>
{
    
    private UUID id;
    private String name;
    private int deaths;
    
    public CustomPlayer(Player player)
    {
        this.id = player.getUniqueId();
        this.name = player.getName();
        this.deaths = 0;
    }
    
    /**
    * Gets the spigot player associated with this player.
    * 
    * @return The spigot player.
    */
    public Player getSpigotPlayer()
    {
        return Bukkit.getPlayer(this.id);
    }
    
    /* SETTERS & GETTERS */

}
```

### Repositories

Objects are saved and retrieved through something called repositories. Each top-level object you want to save should have their own 
repository. In this example, we'll save each ``CustomPlayer`` to their own ``.json`` files in a ``players`` directory. The implementation 
is the following.

```java
public class CustomPlayerRepository extends JsonRepository<UUID, CustomPlayer>
{
    
    public CustomPlayerRepository(Plugin plugin)
    {
        super(plugin, CustomPlayer.class, "players");
    }

    /**
     * Finds a player by their spigot player instance.
     *
     * @param player The spigot player.
     * @return The player.
     */
    public CustomPlayer findByPlayer(Player player)
    {
        return this.find(player.getUniqueId());
    }

}
```

#### Using the repository

```java
public class MyPlugin extends JavaPlugin
{
    
    /**
    * The custom players repository.
    */
    private CustomPlayerRepository customPlayers;
    
    @Override
    public void onEnable()
    {
        instance = this;
        
        // Create repository.
        this.customPlayers = new CustomPlayerRepository(this);
    }
    
    /**
    * Testing of the repository!
    */
    public void test()
    {
        // Create a new custom player!
        CustomPlayer player = new CustomPlayer(Bukkit.getPlayer("ExpDev07"));
        player.setDeaths(10);
        
        // Save it (returns the object that was saved).
        CustomPlayer saved = this.customPlayers.save(player);
        
        // Retrieving it by player.
        CustomPlayer retrieved = this.customPlayers.findByPlayer(Bukkit.getPlayer("ExpDev07"));
        System.out.println(retrieved.getDeaths()); // = 10
    }
    
}
```

Calling ``save`` would result in the following file being created for the object (``my-plugin/players/7d4a521e-41ed-492f-8e45-dee2e86f7c15.json``).

```json 
{
  "id": "7d4a521e-41ed-492f-8e45-dee2e86f7c15",
  "name": "ExpDev07",
  "deaths": 10
}
``` 

## Contributors

List of people who have made contributions to this project.

* **ExpDev07 (https://github.com/ExpDev07) -** initial work.



