package br.ufscar.si.catalogo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.si.catalogo.dao.spec.ICatalogoDAO;
import br.ufscar.si.catalogo.modelo.Catalogo;
import br.ufscar.si.catalogo.modelo.Midia;
import br.ufscar.si.catalogo.modelo.ObjectDTO;
import br.ufscar.si.catalogo.modelo.Tipos;
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

			ArrayList<Midia> midias = new ArrayList<Midia>();

			// CDs
			midias.addAll(selectAllMidias(Tipos.CD, id));
			// DVDs
			midias.addAll(selectAllMidias(Tipos.DVD, id));
			// Jogos
			midias.addAll(selectAllMidias(Tipos.Jogo, id));

			catalogo.adicionaTodasMidias((ArrayList<Midia>) midias);

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

	public List<Midia> selectAllMidias(Tipos tipo, int catalogo_id) throws DAOException
	{
		ArrayList<Midia> midias = new ArrayList<Midia>();

		String sql = "SELECT * FROM " + Database.DB_NAME + "." + tipo + " WHERE CATALOGO_ID = " + catalogo_id;

		try
		{
			Connection conn2 = new ConnectionFactory().getConnection();
			PreparedStatement stmt2 = conn2.prepareStatement(sql);
			ResultSet rs2 = stmt2.executeQuery(sql);

			GenericJDBCDAO genericDAO = null;

			switch (tipo)
			{
				case CD:
					genericDAO = new CDJDBCDAO();
					break;
				case DVD:
					genericDAO = new DVDJDBCDAO();
					break;
				case Jogo:
					genericDAO = new JogoJDBCDAO();
					break;
			}

			while (rs2.next())
			{
				midias.add((Midia) genericDAO.createDTO(rs2));
			}
			rs2.close();
			stmt2.close();
			conn2.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (DAOException e)
		{
			e.printStackTrace();
		}

		return midias;
	}

	public void update(ObjectDTO dto) throws DAOException
	{
		Catalogo catalogo = (Catalogo) dto;
		String sql = "UPDATE " + this.getTableName() + " SET NOME = ?, CAPACIDADE = ? WHERE ID = ?";

		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setString(1, catalogo.getNome());
			((PreparedStatement) stmt).setInt(2, catalogo.getCapacidade());
			((PreparedStatement) stmt).setInt(3, catalogo.getId());
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
}
