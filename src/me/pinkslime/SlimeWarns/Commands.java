package me.pinkslime.SlimeWarns;

import org.spongepowered.api.entity.Player;
import org.spongepowered.api.text.format.TextColors;

public class Commands implements CommandExecutor {
	
	String title = TextColors.DARK_AQUA + "---------------------[" + TextColors.GREEN + "SlimeWarns" + TextColors.DARK_AQUA + "]--------------------";

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("slimewarns") || commandLabel.equalsIgnoreCase("warn")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("You can only use this command in game!");
				return true;
			}
			Player player = (Player) sender;
			if (player.hasPermission("slimewarns.use")) {
				if (args.length == 0) {
					player.sendMessage(TextColors.DARK_AQUA + "----------------------------------------------------");
					player.sendMessage(TextColors.GOLD + "Developed by: " + Main.inst().getAuthor());
					player.sendMessage(TextColors.GOLD + "Version: " + Main.inst().getVersion());
					player.sendMessage(TextColors.YELLOW + "Type /slimewarns help for a list of commands.");
					return true;
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("help")) {
						player.sendMessage(title);
						player.sendMessage(TextColors.GOLD + "/slimewarns help" + TextColors.DARK_AQUA + " --- Shows this page.");
						player.sendMessage(TextColors.GOLD + "/slimewarns get <params>" + TextColors.DARK_AQUA + " --- Gets warns on a player.");
						player.sendMessage(TextColors.GOLD + "/slimewarns warn <params>" + TextColors.DARK_AQUA + " --- Warn a player.");
						player.sendMessage(TextColors.GOLD + "/slimewanrs clear <params>" + TextColors.DARK_AQUA + " --- Clear all of a player's warns.");
						player.sendMessage(TextColors.GOLD + "/slimewarns params" + TextColors.DARK_AQUA + " --- Lists parameters used.");
						return true;
					} else if (args[0].equalsIgnoreCase("params")) {
						player.sendMessage(title);
						player.sendMessage(TextColors.DARK_AQUA + ": " + TextColors.GOLD + "u:<USER>" + TextColors.RESET + " - Specify the user to grab.");
						player.sendMessage(TextColors.DARK_AQUA + ": " + TextColors.GOLD + "r:<REASON>" + TextColors.RESET + " - Specify the warn reason.");
						player.sendMessage(TextColors.DARK_AQUA + ": " + TextColors.GOLD + "l:<LEVEL>" + TextColors.RESET + " - The level of violation");
						return true;
					} else if (args[0].equalsIgnoreCase("get")) {
						player.sendMessage(TextColors.RED + "Invalid parameters");
						return true;
					} else if (args[0].equalsIgnoreCase("warn")) {
						player.sendMessage(TextColors.RED + "Invalid parameters");
						return true;
					} else {
						player.sendMessage(TextColors.RED + "Invalid parameters");
						return true;
					}
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("get")) {
						if (player.hasPermission("slimewarns.get")) {
							if (args[1].startsWith("u:")) {
								Player p = player.getServer().getPlayer(args[1].split(":")[1]);
								String pName = p.getName();
								if (MySQL.check(p)) {
									player.sendMessage(title);
									player.sendMessage(TextColors.GOLD + "Warns for " + pName + ":");
									MySQL.seeWarnings(p, player);
								} else {
									player.sendMessage(TextColors.GREEN + pName + " has never been warned.");
								}
								return true;
							} else {
								player.sendMessage(TextColors.RED + "Invalid parameters");
								return true;
							}
						}
					} else if (args[0].equalsIgnoreCase("warn")) {
						if (player.hasPermission("slimewarns.basic")) {
							if (args[1].startsWith("u:")) {
								Player p = player.getServer().getPlayer(args[1].split(":")[1]);
								MySQL.addPlayer(p, player);
								return true;
							} else {
								player.sendMessage(TextColors.RED + "Invalid parameters");
								return true;
							}
						}
					} else if (args[0].equalsIgnoreCase("clear")) {
						if (player.hasPermission("slimewarns.clear")) {
							if (args[1].startsWith("u:")) {
								Player p = player.getServer().getPlayer(args[1].split(":")[1]);
								MySQL.clearPlayer(p, player);
								return true;
							} else {
								player.sendMessage(TextColors.RED + "Invalid parameters");
								return true;
							}
						}
					} else {
						player.sendMessage(TextColors.RED + "Invalid parameters");
						return true;
					}
				} else if (args.length == 3) {
					if (args[0].equalsIgnoreCase("warn")) {
						if (player.hasPermission("slimewarns.reasoned")) {
							Player p = null;
							String r = null;
							for (int i = 1; i <= 2; ++i) {
							    String arg = args[i];

							    String[] parts = arg.split(":");
							    String key = parts[0];
							    String value = parts[1];

							    if (key.equals("u")) {
							        p = player.getServer().getPlayer(value);
							    } else if (key.equals("r")) {
							        r = value;
							    }
							}
							if (p != null && r != null) {
							    MySQL.addPlayer(p, player, r);
							    return true;
							}
							return false;
						}
					}
				} else if (args.length == 4) {
					if (args[0].equalsIgnoreCase("warn")) {
						if (player.hasPermission("slimewarns.leveled")) {
							Player p = null;
							String r = null;
							String l = null;
							for (int i = 1; i <= 3; ++i) {
							    String arg = args[i];

							    String[] parts = arg.split(":");
							    String key = parts[0];
							    String value = parts[1];

							    if (key.equals("u")) {
							        p = player.getServer().getPlayer(value);
							    } else if (key.equals("r")) {
							        r = value;
							    } else if (key.equals("l")) {
							        l = value;
							    }
							}
							if (p != null && r != null && l != null) {
							    MySQL.addPlayer(p, player, r, l);
							    return true;
							}
							return false;
						}
					}
				}
			}
		}
		return false;
	}

}
