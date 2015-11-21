package br.ufscar.si.catalogo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import br.ufscar.si.catalogo.dao.spec.ICDDAO;
import br.ufscar.si.catalogo.modelo.CD;
import br.ufscar.si.catalogo.modelo.DuracaoFaixa;
import br.ufscar.si.catalogo.modelo.FaixaCD;
import br.ufscar.si.catalogo.modelo.ObjectDTO;
import br.ufscar.si.catalogo.util.Database;

class CDJDBCDAO extends GenericJDBCDAO implements ICDDAO
{
	@Override
	public String getTableName()
	{
		return Database.DB_NAME + ".\"CD\"";
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
		String sql = "INSERT INTO " + this.getTableName() + " (TITULO, ANO, ARTISTA) VALUES (?,?,?)";

		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setString(1, cd.getTitulo());
			((PreparedStatement) stmt).setInt(2, cd.getAnoCriacao());
			((PreparedStatement) stmt).setString(3, cd.getArtista());
			((PreparedStatement) stmt).executeUpdate();

			cd.setId(this.selectLastID());

			for (FaixaCD faixa : cd.getFaixas())
			{
				insertFaixa(cd, faixa);
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
		String sql = "INSERT INTO " + Database.DB_NAME + ".FAIXA (NOME, DURACAO, CD_ID) VALUES (?,?,?)";
		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.prepareStatement(sql);
			((PreparedStatement) stmt).setString(1, faixa.getNome());
			((PreparedStatement) stmt).setInt(2, (faixa.getDuracao()).duracaoEmSegundos());
			((PreparedStatement) stmt).setInt(3, cd.getId());
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
		String sql = "SELECT * FROM " + Database.DB_NAME + ".FAIXA WHERE CD_ID = " + cd.getId();
		try
		{
			conn = new ConnectionFactory().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				int numero = rs.getInt("NUMERO");
				String nome = rs.getString("NOME");
				int segundos = rs.getInt("DURACAO");
				DuracaoFaixa duracao = new DuracaoFaixa((segundos % 60) + ":" + (segundos - (60 * (segundos % 60))));
				cd.adicionaFaixa(numero, nome, duracao);
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
}
