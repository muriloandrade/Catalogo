package br.ufscar.si.catalogo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufscar.si.catalogo.dao.spec.IDVDDAO;
import br.ufscar.si.catalogo.gui.Principal;
import br.ufscar.si.catalogo.modelo.ArtistaDVD;
import br.ufscar.si.catalogo.modelo.DVD;
import br.ufscar.si.catalogo.modelo.ObjectDTO;
import br.ufscar.si.catalogo.util.Database;

public class DVDJDBCDAO extends GenericJDBCDAO implements IDVDDAO
{
	@Override
	public String getTableName()
	{
		return Database.DB_NAME + ".DVD";
	}

	@Override
	protected ObjectDTO createDTO(ResultSet rs) throws DAOException
	{
		DVD dvd = null;
		try
		{
			int id = rs.getInt("ID");
			String titulo = rs.getString("TITULO");
			int ano = rs.getInt("ANO");
			String diretor = rs.getString("DIRETOR");
			dvd = new DVD(titulo, ano, diretor);
			dvd.setId(id);
			this.selectArtistas(dvd);
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return dvd;
	}

	public void insert(ObjectDTO dto) throws DAOException
	{
		DVD dvd = (DVD) dto;
		String sql = "INSERT INTO " + this.getTableName() + " (TITULO, ANO, DIRETOR, CATALOGO_ID) VALUES (?,?,?,?)";

		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setString(1, dvd.getTitulo());
			((PreparedStatement) stmt).setInt(2, dvd.getAnoCriacao());
			((PreparedStatement) stmt).setString(3, dvd.getDiretor());
			((PreparedStatement) stmt).setInt(4, Principal.getCatalogoID());
			((PreparedStatement) stmt).executeUpdate();

			dvd.setId(this.selectLastID());

			for (ArtistaDVD artista : dvd.getArtistas())
			{
				if (artista.getNome() != null || artista.getPapel() != null)
				{					
					insertArtista(dvd, artista);
				}
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
	}

	public void insertArtista(DVD dvd, ArtistaDVD artista) throws DAOException
	{
		String sql = "INSERT INTO " + Database.DB_NAME + ".ARTISTA_DVD (NOME, PAPEL, DVD_ID) VALUES (?,?,?)";
		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setString(1, artista.getNome());
			((PreparedStatement) stmt).setString(2, artista.getPapel());
			((PreparedStatement) stmt).setInt(3, dvd.getId());
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

	public final void selectArtistas(DVD dvd) throws DAOException
	{
		String sql = "SELECT * FROM " + Database.DB_NAME + ".ARTISTA_DVD WHERE DVD_ID = " + dvd.getId();
		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			int pos = 0;
			ArtistaDVD artista = null;

			while (rs.next())
			{
				int id = rs.getInt("ID");
				String nome = rs.getString("NOME");
				String papel = rs.getString("PAPEL");

				artista = new ArtistaDVD(nome, papel);
				artista.setId(id);
				dvd.setArtista(pos++, artista);
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
	}

	public void update(ObjectDTO dto) throws DAOException
	{
		DVD dvd = (DVD) dto;
		String sql = "UPDATE " + this.getTableName() + " SET TITULO = ?, ANO = ?, DIRETOR = ? WHERE ID = ?";

		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setString(1, dvd.getTitulo());
			((PreparedStatement) stmt).setInt(2, dvd.getAnoCriacao());
			((PreparedStatement) stmt).setString(3, dvd.getDiretor());
			((PreparedStatement) stmt).setInt(4, dvd.getId());
			((PreparedStatement) stmt).executeUpdate();
			
			deleteArtistas(dvd);

			for (ArtistaDVD artista : dvd.getArtistas())
			{
				insertArtista(dvd, artista);
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
	}

	public void deleteArtistas(DVD dvd) throws DAOException
	{
		String sql = "DELETE FROM " + Database.DB_NAME + ".ARTISTA_DVD WHERE DVD_ID = ?";

		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setInt(1, dvd.getId());
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
