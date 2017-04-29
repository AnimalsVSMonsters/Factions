package com.animalsvsmonsters.factions.utils.database;

import java.sql.*;
import java.util.logging.Level;

import com.animalsvsmonsters.factions.Files;
import com.animalsvsmonsters.factions.Main;

public class Database {

	private static Database instance;

	public static Database get() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	Connection con;
	Integer retry = 1;

	private void createConnection() {
		Main.getInstance().registerDatabaseConfig();
		Files files = new Files(Main.getInstance().getDataFolder(), "database");
		String url = "jdbc:mysql://" + files.getConfig().getString("sql.serverHost") + ":"
				+ files.getConfig().getString("sql.port") + "/" + files.getConfig().getString("sql.databaseName");
		String username = files.getConfig().getString("sql.username");
		String password = files.getConfig().getString("sql.password");

		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException ex) {
			if (retry <= 3) {
				Main.getInstance().getLogger().log(Level.INFO, "Retrying connection... " + retry + "/3");
				retry++;
				this.createConnection();
			} else {
				Main.getInstance().getLogger().log(Level.INFO, "Connection refused, terminating.");
				ex.printStackTrace();
			}
		}
	}

	public Database() {
		this.createConnection();

		/* CREATE TABLES */

		createTable("user_info", "uuid VARCHAR(36), team VARCHAR(8), kit INT");
		createTable("team_info", "team VARCHAR(8), kills INT, deaths INT");
		createTable("reset_queue", "resetkey INT PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36)");

	}

	/**
	 * @param query
	 * @param parameters
	 * @param callback
	 */
	public void syncQuery(String query, Object[] parameters, Callback callback) {
		if (checkClosed()) {
			return;
		}
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = con.prepareStatement(query);
			injectParameters(stat, parameters);
			stat.execute();
			rs = stat.getResultSet();
			callback.read(rs);
			tryClose(rs);
			tryClose(stat);
			callback.digestSync();
		} catch (SQLException e) {
			e.printStackTrace();
			Main.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
			try {
				Main.getInstance().getLogger().log(Level.SEVERE, stat.toString());
			} catch (Exception e1) {
			}
		} finally {
			tryClose(rs);
			tryClose(stat);
		}
	}

	/**
	 * @param query
	 * @param parameters
	 * @return integer
	 */
	public int syncUpdate(final String query, final Object[] parameters) {
		PreparedStatement stat = null;
		try {
			stat = con.prepareStatement(query);
			injectParameters(stat, parameters);
			return stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			Main.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
			try {
				Main.getInstance().getLogger().log(Level.SEVERE, stat.toString());
			} catch (Exception e1) {
			}
		} finally {
			tryClose(stat);
		}
		return -1;
	}

	/**
	 * @param query
	 * @param parameters
	 * @return integer
	 */
	public int syncInsert(final String query, final Object[] parameters) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			injectParameters(stat, parameters);
			rs = stat.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			Main.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
			try {
				Main.getInstance().getLogger().log(Level.SEVERE, stat.toString());
			} catch (Exception e1) {
			}
		} finally {
			tryClose(rs);
			tryClose(stat);
		}
		return -1;
	}

	/**
	 * @return connection closed
	 */
	private boolean checkClosed() {
		try {
			if (con.isClosed()) {
				this.createConnection();
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * @param tableName
	 * @param fields
	 */
	private void createTable(String tableName, String fields) {
		syncUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + fields + ")", null);
	}

	/**
	 * @param stat
	 * @param parameters
	 * @throws java.sql.SQLException
	 */
	private void injectParameters(PreparedStatement stat, Object[] parameters) throws SQLException {
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				Object param = parameters[i];
				if (param instanceof String) {
					stat.setString((i + 1), String.valueOf(param));
				} else if (param instanceof Integer) {
					stat.setInt((i + 1), (Integer) param);
				} else if (param instanceof Long) {
					stat.setLong((i + 1), (Long) param);
				} else if (param instanceof Timestamp) {
					stat.setTimestamp((i + 1), (Timestamp) param);
				} else if (param instanceof Boolean) {
					stat.setBoolean((i + 1), (Boolean) param);
				}
			}
		}
	}

	private void tryClose(AutoCloseable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
			}
		}
	}
}
