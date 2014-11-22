package me.pinkslime.SlimeWarns;

import java.io.File;
import java.io.IOException;

import org.spongepowered.api.entity.Player;


public class Config {
	static File configFile = new File("plugins/SlimeWarns/config.yml");
	static File folder = new File("plugins/SlimeWarns");
	static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
	


	public static void createConfig() throws IOException{
		if(!folder.exists())				// If plugin folder doesn't exist,
			folder.mkdirs();				// create it.
		
		if(!configFile.exists())			// If plugin config doesn't exist,
			configFile.createNewFile();		// create it.
		
	}
	
	public static void saveConfig(){
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addPlayer(Player player, Player sender){
		String name = player.getName();
		String s = sender.getName();
		String r = "No Set Reason";
		if(!checkPlayer(name, 1)){
			config.set(name + ".firstWarn.reason", r);
			config.set(name + ".firstWarn.warner", s);
			player.sendMessage(ChatColor.DARK_RED + "[FirstWarn] " + ChatColor.DARK_AQUA + "You have been warned by " + s + " for reason: " + r);
			sender.sendMessage(ChatColor.GREEN + "[SlimeWarns] " + ChatColor.DARK_AQUA + name + " is on his first warning for: " + r);
			saveConfig();
		}else if(!checkPlayer(name, 2)){
			config.set(name + ".secondWarn.reason", r);
			config.set(name + ".secondWarn.warner", s);
			player.sendMessage(ChatColor.DARK_RED + "[SecondWarn] " + ChatColor.DARK_AQUA + "You have been warned by " + s + " for reason: " + r);
			sender.sendMessage(ChatColor.GREEN + "[SlimeWarns] " + ChatColor.DARK_AQUA + name + " is on his second warning for: " + r);
			saveConfig();
		}else if(!checkPlayer(name, 3)){
			config.set(name + ".thirdWarn.reason", r);
			config.set(name + ".thirdWarn.warner", s);
			player.sendMessage(ChatColor.DARK_RED + "[ThirdWarn] " + ChatColor.DARK_AQUA + "You have been warned by " + s + " for reason: " + r);
			sender.sendMessage(ChatColor.GREEN + "[SlimeWarns] " + ChatColor.DARK_AQUA + name + " is on his second warning for: " + r);
			saveConfig();
		}else if(checkThird(name)){
			sender.sendMessage(ChatColor.RED + name + " already has 3 warnings!");
		}
	}
	public static void addPlayer(Player player, String r, Player sender){
		String name = player.getName();
		String s = sender.getName();
		if(!checkPlayer(name, 1)){
			config.set(name + ".firstWarn.reason", r);
			config.set(name + ".firstWarn.warner", s);
			player.sendMessage(ChatColor.DARK_RED + "[FirstWarn] " + ChatColor.DARK_AQUA + "You have been warned by " + s + " for reason: " + r);
			sender.sendMessage(ChatColor.GREEN + "[SlimeWarns] " + ChatColor.DARK_AQUA + name + " is on his first warning for: " + r);
			saveConfig();
		}else if(!checkPlayer(name, 2)){
			config.set(name + ".secondWarn.reason", r);
			config.set(name + ".secondWarn.warner", s);
			player.sendMessage(ChatColor.DARK_RED + "[SecondWarn] " + ChatColor.DARK_AQUA + "You have been warned by " + s + " for reason: " + r);
			sender.sendMessage(ChatColor.GREEN + "[SlimeWarns] " + ChatColor.DARK_AQUA + name + " is on his second warning for: " + r);
			saveConfig();
		}else if(!checkPlayer(name, 3)){
			config.set(name + ".thirdWarn.reason", r);
			config.set(name + ".thirdWarn.warner", s);
			player.sendMessage(ChatColor.DARK_RED + "[ThirdWarn] " + ChatColor.DARK_AQUA + "You have been warned by " + s + " for reason: " + r);
			sender.sendMessage(ChatColor.GREEN + "[SlimeWarns] " + ChatColor.DARK_AQUA + name + " is on his second warning for: " + r);
			saveConfig();
		}else if(checkThird(name)){
			sender.sendMessage(ChatColor.RED + name + " already has 3 warnings!");
		}
	}
	public static void addPlayer(Player player, String r, String l, Player sender){
		String name = player.getName();
		String s = sender.getName();
		if(!checkPlayer(name, 1)){
			config.set(name + ".firstWarn.reason", r);
			config.set(name + ".firstWarn.warner", s);
			config.set(name + ".firstWarn.level", l);
			player.sendMessage(ChatColor.DARK_RED + "[FirstWarn] " + ChatColor.DARK_AQUA + "You have been warned by " + s + " for reason: " + r);
			sender.sendMessage(ChatColor.GREEN + "[SlimeWarns] " + ChatColor.DARK_AQUA + name + " is on his first warning for: " + r + " at level: " + l);
			saveConfig();
		}else if(!checkPlayer(name, 2)){
			config.set(name + ".secondWarn.reason", r);
			config.set(name + ".secondWarn.warner", s);
			config.set(name + ".secondWarn.level", l);
			player.sendMessage(ChatColor.DARK_RED + "[SecondWarn] " + ChatColor.DARK_AQUA + "You have been warned by " + s + " for reason: " + r);
			sender.sendMessage(ChatColor.GREEN + "[SlimeWarns] " + ChatColor.DARK_AQUA + name + " is on his second warning for: " + r + " at level: " + l);
			saveConfig();
		}else if(!checkPlayer(name, 3)){
			config.set(name + ".thirdWarn.reason", r);
			config.set(name + ".thirdWarn.warner", s);
			config.set(name + ".thirdWarn.level", l);
			player.sendMessage(ChatColor.DARK_RED + "[ThirdWarn] " + ChatColor.DARK_AQUA + "You have been warned by " + s + " for reason: " + r);
			sender.sendMessage(ChatColor.GREEN + "[SlimeWarns] " + ChatColor.DARK_AQUA + name + " is on his second warning for: " + r + " at level: " + l);
			saveConfig();
		}else if(checkThird(name)){
			sender.sendMessage(ChatColor.RED + name + " already has 3 warnings!");
		}
	}
	public static void clearPlayer(Player player, Player sender){
		String name = player.getName();
		config.set(name, null);
		sender.sendMessage(ChatColor.GREEN + "[SlimeWarns] " + ChatColor.DARK_AQUA + "All warns have been cleared for player " + name + ".");
	}
	
	public static boolean checkPlayer(String name, int amount){
		if(amount == 1){
			if(config.getString(name) == null && config.getString(name + ".firstWarn.reason") == null){
				return false;
			}else{
				return true;
			}
		}else if(amount == 2){
			if(checkPlayer(name, 1)){
				if(config.getString(name + ".secondWarn.reason") == null){
					return false;
				}else{
					return true;
				}
			}
		}else if(amount == 3){
			if(checkPlayer(name, 2)){
				if(config.getString(name + ".thirdWarn.reason") == null){
					return false;
				}else{
					return true;
				}
			}
		}else if(amount == 4){
			return false;
		}else{
			return false;
		}
		return false;
	}
	
	public static boolean check(String name){
		if(config.getString(name + ".firstWarn.reason") == null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean checkThird(String name){
		if(checkPlayer(name, 3)){
			return true;
		}
		return false;
	}
	
	public static void getFirst(String name, Player sender){
		if(check(name)){
			String reason = config.getString(name + ".firstWarn.reason");
			if(config.getString(name + ".firstWarn.level") != null){
				String level = config.getString(name + ".firstWarn.level");
				sender.sendMessage(ChatColor.DARK_RED + "FirstWarn: " + ChatColor.WHITE + reason + "." + ChatColor.RED + " Level: " + level + ".");
				return;
			}else{
				sender.sendMessage(ChatColor.DARK_RED + "FirstWarn: " + ChatColor.WHITE + reason + ".");
				return;
			}
		}
		return;
	}
	public static void getSecond(String name, Player sender){
		if(config.getString(name + ".secondWarn.reason") != null){
			String reason = config.getString(name + ".secondWarn.reason");
			if(config.getString(name + ".secondWarn.level") != null){
				String level = config.getString(name + ".secondWarn.level");
				sender.sendMessage(ChatColor.DARK_RED + "SecondWarn: " + ChatColor.WHITE + reason + "." + ChatColor.RED + " Level: " + level + ".");
				return;
			}else{
				sender.sendMessage(ChatColor.DARK_RED + "SecondWarn: " + ChatColor.WHITE + reason + ".");
				return;
			}
		}
		return;
	}
	public static void getThird(String name, Player sender){
		if(config.getString(name + ".thirdWarn.reason") != null){
			String reason = config.getString(name + ".thirdWarn.reason");
			if(config.getString(name + ".thirdWarn.level") != null){
				String level = config.getString(name + ".thirdWarn.level");
				sender.sendMessage(ChatColor.DARK_RED + "ThirdWarn: " + ChatColor.WHITE + reason + "." + ChatColor.RED + " Level: " + level + ".");
				return;
			}else{
				sender.sendMessage(ChatColor.DARK_RED + "ThirdWarn: " + ChatColor.WHITE + reason + ".");
				return;
			}
		}
		return;
	}
}
