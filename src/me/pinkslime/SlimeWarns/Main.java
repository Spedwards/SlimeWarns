package me.pinkslime.SlimeWarns;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id="SlimeWarns", name="SlimeWarns", version="1.0.0-SNAPSHOT")
public class Main {
	private static Main instance;
	
	@Subscribe
	public void onEnable(PreInitializationEvent e) {
		instance = this;
		
		MySQL.createTables();
		
		getCommand("warn").setExecutor(new Commands());
	}
	
	public void onDisable(){
		instance = null;
	}
	
	public static Main inst() {
		return instance;
	}
	
	public String getVersion(){									// Returns plugin version
		PluginDescriptionFile pdfFile = this.getDescription();
		return pdfFile.getVersion();
	}
	public String getAuthor(){									// Returns plugin author
		PluginDescriptionFile pdfFile = this.getDescription();
		return listToString(pdfFile.getAuthors());
	}
	public static String listToString(List<String> list) {
	    StringBuilder sb = new StringBuilder();
	    for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
	        sb.append(iterator.next()).append(iterator.hasNext() ? ", " : "");
	    return sb.toString();
	}
}
