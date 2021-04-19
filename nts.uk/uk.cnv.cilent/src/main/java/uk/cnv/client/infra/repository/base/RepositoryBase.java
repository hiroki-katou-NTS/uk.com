package uk.cnv.client.infra.repository.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uk.cnv.client.LogManager;
import uk.cnv.client.UkConvertProperty;

public abstract class RepositoryBase {
	private String jdbcDriverName;
	private String connectionString;
	private Connection conn;

	public RepositoryBase(String jdbcDriverName, String connectionStringKey) {
		this.jdbcDriverName = jdbcDriverName;
		this.connectionString = UkConvertProperty.getProperty(connectionStringKey);
		loadJdbc();
	}

	private void loadJdbc() {
    	try {
			Class.forName(this.jdbcDriverName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("DB接続設定が無効です。:" + this.jdbcDriverName);
		}
	}

	public void connect() throws SQLException {
		try {
			this.conn = DriverManager.getConnection(this.connectionString);
		}
		catch (SQLException e) {
			LogManager.err("DB接続に失敗しました。");
			LogManager.err(e);
			throw e;
		}
	}
	public void disConnect() {
		try {
			if(this.conn == null || this.conn.isClosed()) return;
			this.conn.close();
		} catch (SQLException e) {
			LogManager.err("DB接続の切断に失敗しました。");
			LogManager.err(e);
		}
	}

	public Connection connection() throws SQLException {
		if( this.conn == null || this.conn.isClosed()) {
			this.connect();
		}
		return this.conn;
	}

	public <T> Optional<T> getSingle(PreparedStatement ps, SelectRequire<T> require) throws SQLException {
		List<T> result = new ArrayList<>();
		ResultSet rset = null;
		try {
			if(conn == null || conn.isClosed()) {
				this.connect();
			}

			rset = ps.executeQuery();
			while (rset.next()) {

				T domain = require.toEntity(rset);
				result.add(domain);
			}
		}
		catch (SQLException ex) {
			throw ex;
		}
		finally {
			if(rset != null) {
				try {
					rset.close();
				}
				catch (SQLException ex) {
					System.err.println(ex.getMessage());
				}
			}
			this.disConnect();
		}

		return result.stream().findFirst();
	}

	public <T> Optional<T> getSingle(String sql, SelectRequire<T> require) throws SQLException {

		List<T> result = new ArrayList<>();
		Statement stmt;
		ResultSet rset = null;
		try {
			if(conn == null || conn.isClosed()) {
				this.connect();
			}

			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {

				T domain = require.toEntity(rset);
				result.add(domain);
			}
		}
		catch (SQLException ex) {
			throw ex;
		}
		finally {
			if(rset != null) {
				try {
					rset.close();
				}
				catch (SQLException ex) {
					System.err.println(ex.getMessage());
				}
			}
			this.disConnect();
		}

		return result.stream().findFirst();
	}

	public <T> List<T> getList(PreparedStatement ps, SelectRequire<T> require) throws SQLException {
		List<T> result = new ArrayList<>();
		ResultSet rset = null;
		try {
			if(conn == null || conn.isClosed()) {
				this.connect();
			}

			rset = ps.executeQuery();
			while (rset.next()) {

				T domain = require.toEntity(rset);
				result.add(domain);
			}
		}
		catch (SQLException ex) {
			throw ex;
		}
		finally {
			if(rset != null) {
				try {
					rset.close();
				}
				catch (SQLException ex) {
					System.err.println(ex.getMessage());
				}
			}
			this.disConnect();
		}

		return result;
	}

	public <T> List<T> getList(String sql, SelectRequire<T> require) throws SQLException {

		List<T> result = new ArrayList<>();
		Statement stmt;
		ResultSet rset = null;
		try {
			if(conn == null || conn.isClosed()) {
				this.connect();
			}

			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {

				T domain = require.toEntity(rset);
				result.add(domain);
			}
		}
		catch (SQLException ex) {
			throw ex;
		}
		finally {
			if(rset != null) {
				try {
					rset.close();
				}
				catch (SQLException ex) {
					System.err.println(ex.getMessage());
				}
			}
			this.disConnect();
		}

		return result;
	}

	public <T> void insert(InsertRequire require) throws SQLException {
		PreparedStatement ps = null;
		try {
			if(conn == null || conn.isClosed()) {
				this.connect();
			}

			conn.setAutoCommit(false);
			ps = require.insert(conn);
			ps.executeUpdate();

			conn.commit();
		}
		catch (SQLException ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				LogManager.err(e);
			}
			throw ex;
		}
		finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					LogManager.err(e);
				}
			}
			this.disConnect();
		}
	}

	public <T> void truncateTable(TruncateTableRequire require) throws SQLException {
		PreparedStatement ps = null;
		try {
			if(conn == null || conn.isClosed()) {
				this.connect();
			}

			conn.setAutoCommit(false);
			ps = require.truncateTable(conn);
			ps.executeUpdate();

			conn.commit();
		}
		catch (SQLException ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				LogManager.err(e);
			}
			throw ex;
		}
		finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					LogManager.err(e);
				}
			}
			this.disConnect();
		}
	}

	public interface SelectRequire <T> {
		T toEntity(ResultSet rs) throws SQLException;
	}

	public interface InsertRequire {
		PreparedStatement insert(Connection conn) throws SQLException;
	}

	public interface TruncateTableRequire {
		PreparedStatement truncateTable(Connection conn) throws SQLException;
	}
}
