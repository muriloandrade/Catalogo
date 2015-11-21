package br.ufscar.si.catalogo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufscar.si.catalogo.dao.spec.IJogoDAO;
import br.ufscar.si.catalogo.modelo.Jogo;
import br.ufscar.si.catalogo.modelo.ObjectDTO;
import br.ufscar.si.catalogo.util.Database;

class JogoJDBCDAO extends GenericJDBCDAO implements IJogoDAO
{
	@Override
	public String getTableName()
	{
		return Database.DB_NAME + ".\"JOGO\"";
	}

	@Override
	protected ObjectDTO createDTO(ResultSet rs) throws DAOException
	{
		Jogo jogo = null;
		try
		{
			int id = rs.getInt("ID");
			String titulo = rs.getString("TITULO");
			int ano = rs.getInt("ANO");
			String genero = rs.getString("GENERO");
			jogo = new Jogo(titulo, ano, genero);
			jogo.setId(id);
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return jogo;
	}

	public void insert(ObjectDTO dto) throws DAOException
	{
		Jogo jogo = (Jogo) dto;
		String sql = "INSERT INTO " + this.getTableName() + " (TITULO, ANO, GENERO) VALUES (?,?,?)";

		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setString(1, jogo.getTitulo());
			((PreparedStatement) stmt).setInt(2, jogo.getAnoCriacao());
			((PreparedStatement) stmt).setString(3, jogo.getGenero());
			((PreparedStatement) stmt).executeUpdate();

			jogo.setId(this.selectLastID());
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
