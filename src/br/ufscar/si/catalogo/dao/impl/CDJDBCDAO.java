package br.ufscar.si.catalogo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;

import br.ufscar.si.catalogo.dao.spec.ICDDAO;
import br.ufscar.si.catalogo.gui.Principal;
import br.ufscar.si.catalogo.modelo.CD;
import br.ufscar.si.catalogo.modelo.DuracaoFaixa;
import br.ufscar.si.catalogo.modelo.FaixaCD;
import br.ufscar.si.catalogo.modelo.ObjectDTO;
import br.ufscar.si.catalogo.util.Database;

public class CDJDBCDAO extends GenericJDBCDAO implements ICDDAO
{
	@Override
	public String getTableName()
	{
		return Database.DB_NAME + ".CD";
	}

	@Override
	protected ObjectDTO createDTO(ResultSet rs) throws DAOException
	{
		CD cd = null;
		try
		{
			int id = rs.getInt("ID");
			String titulo = rs.getString("TITULO");
			int ano = rs.getInt("ANO");
			String artista = rs.getString("ARTISTA");
			cd = new CD(titulo, ano, artista);
			cd.setId(id);
			this.selectFaixas(cd);
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return cd;
	}

	public void insert(ObjectDTO dto) throws DAOException
	{
		CD cd = (CD) dto;
		String sql = "INSERT INTO " + this.getTableName() + " (TITULO, ANO, ARTISTA, CATALOGO_ID) VALUES (?,?,?,?)";

		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setString(1, cd.getTitulo());
			((PreparedStatement) stmt).setInt(2, cd.getAnoCriacao());
			((PreparedStatement) stmt).setString(3, cd.getArtista());
			((PreparedStatement) stmt).setInt(4, Principal.getCatalogoID());
			((PreparedStatement) stmt).executeUpdate();

			cd.setId(this.selectLastID());

			for (FaixaCD faixa : cd.getFaixas())
			{
				if (faixa.getNome() != null)
				{
					insertFaixa(cd, faixa);
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

	public void insertFaixa(CD cd, FaixaCD faixa) throws DAOException
	{
		String sql = "INSERT INTO " + Database.DB_NAME + ".FAIXA_CD (NUMERO, NOME, DURACAO, CD_ID) VALUES (?,?,?,?)";
		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setInt(1, faixa.getNumero());
			((PreparedStatement) stmt).setString(2, faixa.getNome());
			DuracaoFaixa duracao = faixa.getDuracao();
			if (duracao == null)
			{
				((PreparedStatement) stmt).setNull(3, Types.INTEGER);
			}
			else
			{
				((PreparedStatement) stmt).setInt(3, duracao.duracaoEmSegundos());
			}
			((PreparedStatement) stmt).setInt(4, cd.getId());
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

	public final void selectFaixas(CD cd) throws DAOException
	{
		String sql = "SELECT * FROM " + Database.DB_NAME + ".FAIXA_CD WHERE CD_ID = " + cd.getId();
		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			FaixaCD faixa = null;

			while (rs.next())
			{
				int id = rs.getInt("ID");
				int numero = rs.getInt("NUMERO");
				String nome = rs.getString("NOME");
				int segundos = rs.getInt("DURACAO");
				DuracaoFaixa duracao = segundos == 0 ? null : new DuracaoFaixa((segundos % 60) + ":"
						+ (segundos - (60 * (segundos % 60))));

				faixa = new FaixaCD(numero, nome, duracao);
				faixa.setId(id);
				cd.setFaixa(numero - 1, faixa);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		finally
		{
			fechaRecursos();
		}
	}

	public void update(ObjectDTO dto) throws DAOException
	{
		CD cd = (CD) dto;
		String sql = "UPDATE " + this.getTableName() + " SET TITULO = ?, ANO = ?, ARTISTA = ? WHERE ID = ?";

		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setString(1, cd.getTitulo());
			((PreparedStatement) stmt).setInt(2, cd.getAnoCriacao());
			((PreparedStatement) stmt).setString(3, cd.getArtista());
			((PreparedStatement) stmt).setInt(4, cd.getId());
			((PreparedStatement) stmt).executeUpdate();

			deleteFaixas(cd);

			for (FaixaCD faixa : cd.getFaixas())
			{
				if (faixa.getNome() != null)
				{
					insertFaixa(cd, faixa);
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

	public void deleteFaixas(CD cd) throws DAOException
	{
		String sql = "DELETE FROM " + Database.DB_NAME + ".FAIXA_CD WHERE CD_ID = ?";

		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setInt(1, cd.getId());
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
