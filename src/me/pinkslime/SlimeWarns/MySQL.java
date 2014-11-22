package me.pinkslime.SlimeWarns;

import java.sql.*;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.Player;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.message.Message;

import de.mrmysteri0us.dbmanager.DBManager;

public class MySQL {
	
	private static String prefix = TextColors.GREEN + "[SlimeWarns] " + TextColors.DARK_AQUA;
	static DBManager dbManager = DBManager.getInstance();
	
	
	public static void createTables() {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			

			conn = dbManager.getConnection(Main.inst());
			
			stmt = conn.createStatement();
			stmt.executeQuery("CREATE TABLE IF NOT EXISTS SlimeWarnsPlayers (" +
					"uuid VARCHAR(36) NOT NULL," +
					"name VARCHAR(26) NOT NULL," +
					"PRIMARY KEY (uuid)" +
			")");
			
			stmt.executeQuery("CREATE TABLE IF NOT EXISTS SlimeWarnWarners (" +
					"uuid VARCHAR(36) NOT NULL," +
					"name VARCHAR(26) NOT NULL," +
					"PRIMARY KEY (uuid)" +
			")");
			
			stmt.executeQuery("CREATE TABLE IF NOT EXISTS SlimeWarnsWarns (" +
					"id INT NOT NULL AUTO_INCREMENT," +
					"pUUID VARCHAR(36) NOT NULL," +
					"wUUID VARCHAR(36) NOT NULL," +
					"warning VARCHAR(60) NOT NULL," +
					"level VARCHAR(60) NOT NULL," +
					"PRIMARY KEY (id)," +
					"FOREIGN KEY (pUUID) REFERENCES SlimeWarnsPlayers(uuid)," +
					"FOREIGN KEY (wUUID) REFERENCES SlimeWarnsWarners(uuid)" +
			")");
			
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	public static void addPlayer(Player player, Player warnedBy) {
		addPlayer(player, warnedBy, "No Set Reason", "");
	}
	
	public static void addPlayer(Player player, Player warnedBy, String reason) {
		addPlayer(player, warnedBy, reason, "");
	}
	
	public static void addPlayer(Player player, Player warnedBy, String reason, int level) {
		String lvl = Integer.toString(level);
		addPlayer(player, warnedBy, reason, lvl);
	}
	
	public static void addPlayer(Player player, Player warnedBy, String reason, String level) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = dbManager.getConnection(Main.inst());
			
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT EXISTS (SELECT * FROM slimewarnsplayers WHERE uuid='" + player.getUniqueId() + "') AS exist");
			int amt = rs.getInt("exist");
			if (amt == 0) {
				stmt.executeQuery("INSERT INTO slimewarnsplayers VALUES ('" + player.getUniqueId() + "', '" + player.getName() + "')");
			}
			rs.close();
			
			rs = stmt.executeQuery("SELECT EXISTS (SELECT * FROM slimewarnswarners WHERE uuid='" + warnedBy.getUniqueId() + "') AS exist");
			amt = rs.getInt("exist");
			if (amt == 0) {
				stmt.executeQuery("INSERT INTO slimewarnswarners VALUES ('" + warnedBy.getUniqueId() + "', '" + warnedBy.getName() + "')");
			}
			rs.close();
			
			stmt.executeQuery("INSERT INTO slimewarnswarns VALUES ('', '" + player.getUniqueId() + "', '" + warnedBy.getUniqueId() + "', '" + reason + "', '" + level + "')");
			
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	public static void clearPlayer(Player player, Player remover) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = dbManager.getConnection(Main.inst());
			
			stmt = conn.createStatement();
			stmt.executeQuery("DELETE FROM slimewarnswarns WHERE uuid='" + player.getUniqueId() + "'");
			
			remover.sendMessage(prefix + "All warns have been cleared for player " + player.getName() + ".");
			
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	public static boolean check(Player player) {
		Connection conn = null;
		Statement stmt = null;
		int amt = 0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = dbManager.getConnection(Main.inst());
			
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) amt FROM slimewarnswarns WHERE pUUID='" + player.getUniqueId() + "'");
			amt = rs.getInt("amt");
			rs.close();
			
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		
		return amt > 0;
	}
	
	public void checkWarner(Player player, Player sender) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = dbManager.getConnection(Main.inst());
			
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) amt FROM slimewarnswarners WHERE wUUID='" + player.getUniqueId() + "'");
			int amt = rs.getInt("amt");
			rs.close();
			
			sender.sendMessage(prefix + player.getName() + " has warned " + amt + " players.");
			
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	public static void seeWarnings(Player player, Player sender) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = dbManager.getConnection(Main.inst());
			
			stmt = conn.createStatement();
			if (check(player)) {
				ResultSet rs = stmt.executeQuery("SELECT w.id, a.name, b.name warner, w.warning, w.level FROM SlimeWarnsPlayers a, SlimeWarnsWarners b, SlimeWarnsWarns w WHERE w.pUUID='" + player.getUniqueId() + "' AND w.pUUID=a.uuid");
				while (rs.next()) {
					String warner = rs.getString("warner");
					String reason = rs.getString("warning");
					String level  = rs.getString("level");
					boolean empty = level.equals("");
					
					sender.sendMessage(prefix + player.getName() + "'s warning from " + warner + " for " + reason + (empty?"":(" at level " + level)));
				}
				rs.close();
			}
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
}
