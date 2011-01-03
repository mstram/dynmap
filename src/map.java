import java.util.logging.Logger;
import java.io.IOException;

import java.io.File;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;

public class map extends JavaPlugin {

	protected static final Logger log = Logger.getLogger("Minecraft");

	private WebServer server = null;
	private MapManager mgr = null;
	private MapListener listener = null;

	public map(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File plugin, ClassLoader cLoader) {
		super(pluginLoader, instance, desc, plugin, cLoader);
		registerEvents();
	}

	public World getWorld() {
		return getServer().getWorlds()[0];
	}

	@Override
	public void onEnable() {
		log.info("Map INIT");

		mgr = new MapManager(this);
		mgr.startManager();

		try {
			server = new WebServer(mgr.serverport, mgr);
		} catch(IOException e) {
			log.info("position failed to start WebServer (IOException)");
		}

		listener = new MapListener(mgr);
	}

	@Override
	public void onDisable() {
		log.info("Map UNINIT");

		mgr.stopManager();

		if(server != null) {
			server.shutdown();
			server = null;
		}
	}

	public void registerEvents() {
		getServer().getPluginManager().registerEvent(Event.Type.BLOCK_PLACED, listener, Priority.Normal, this);
		//getServer().getPluginManager().registerEvent(Event.Type.BLOCK_DESTROYED, listener, Priority.Normal, this);
	/*	etc.getLoader().addListener(PluginLoader.Hook.COMMAND, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.BLOCK_CREATED, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.BLOCK_DESTROYED, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.LOGIN, listener, this, PluginListener.Priority.MEDIUM);

		etc.getInstance().addCommand("/map_wait", " [wait] - set wait between tile renders (ms)");
		etc.getInstance().addCommand("/map_stat", " - query number of tiles in render queue");
		etc.getInstance().addCommand("/map_regen", " - regenerate entire map");
		etc.getInstance().addCommand("/map_debug", " - send map debugging messages");
		etc.getInstance().addCommand("/map_nodebug", " - disable map debugging messages");
		etc.getInstance().addCommand("/addsign", " [name] - adds a named sign to the map");
		etc.getInstance().addCommand("/removesign", " [name] - removes a named sign to the map");
		etc.getInstance().addCommand("/listsigns", " - list all named signs");
		etc.getInstance().addCommand("/tpsign", " [name] - teleport to a named sign");*/
	}
}