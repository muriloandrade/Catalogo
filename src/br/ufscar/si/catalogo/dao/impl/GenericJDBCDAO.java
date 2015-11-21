package br.ufscar.si.catalogo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.si.catalogo.dao.spec.IGenericDAO;
import br.ufscar.si.catalogo.modelo.ObjectDTO;

public abstract class GenericJDBCDAO implements IGenericDAO
{
	protected abstract String getTableName();

	protected abstract ObjectDTO createDTO(ResultSet rs) throws DAOException;

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	public final List<ObjectDTO> selectAll() throws DAOException
	{
		List<ObjectDTO> list = new ArrayList<ObjectDTO>();
		try
		{
			String sql = "SELECT * FROM " + this.getTableName();
			conn = new ConnectionFactory().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				list.add(this.createDTO(rs));
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			fechaRecursos();
		}
		return list;
	}

	public final ObjectDTO selectByID(int id) throws DAOException
	{
		ObjectDTO dto = null;

		try
		{
			String sql = "SELECT * FROM " + this.getTableName() + " WHERE ID = " + id;
			conn = new ConnectionFactory().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
			{
				dto = this.createDTO(rs);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			fechaRecursos();
		}
		return dto;
	}

	public final int selectLastID() throws DAOException
	{
		int lastID = 0;

		try
		{
			String sql = "SELECT MAX(ID) FROM " + this.getTableName();
			conn = new ConnectionFactory().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
			{
				lastID = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			fechaRecursos();
		}
		return lastID;
	}

	public final void delete(int id) throws DAOException
	{
		try
		{
			String sql = "DELETE FROM " + this.getTableName() + " WHERE ID = ?";
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setInt(1, id);
			((PreparedStatement) stmt).executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			fechaRecursos();
		}
	}

	protected void fechaRecursos() throws DAOException
	{
		try
		{
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
	}
}
