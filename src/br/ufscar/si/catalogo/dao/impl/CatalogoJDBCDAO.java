package br.ufscar.si.catalogo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufscar.si.catalogo.dao.spec.ICatalogoDAO;
import br.ufscar.si.catalogo.modelo.Catalogo;
import br.ufscar.si.catalogo.modelo.ObjectDTO;
import br.ufscar.si.catalogo.util.Database;

public class CatalogoJDBCDAO extends GenericJDBCDAO implements ICatalogoDAO
{
	@Override
	public String getTableName()
	{
		return Database.DB_NAME + ".`CATALOGO`";
	}
	
	@Override
	protected ObjectDTO createDTO(ResultSet rs) throws DAOException
	{
		Catalogo catalogo = null;
		try
		{
			int id = rs.getInt("ID");
			String nome = rs.getString("NOME");
			int capacidade = rs.getInt("CAPACIDADE");
			catalogo = new Catalogo(nome, capacidade);
			catalogo.setId(id);
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return catalogo;
	}

	public void insert(ObjectDTO dto) throws DAOException
	{
		Catalogo catalogo = (Catalogo) dto;
		String sql = "INSERT INTO " + this.getTableName() + " (NOME, CAPACIDADE) VALUES (?,?)";

		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setString(1, catalogo.getNome());
			((PreparedStatement) stmt).setInt(2, catalogo.getCapacidade());
			((PreparedStatement) stmt).executeUpdate();

			catalogo.setId(this.selectLastID());
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
}

