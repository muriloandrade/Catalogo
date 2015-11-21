package br.ufscar.si.catalogo.dao.impl;

import br.ufscar.si.catalogo.dao.spec.ICDDAO;
import br.ufscar.si.catalogo.dao.spec.IDVDDAO;
import br.ufscar.si.catalogo.dao.spec.IJogoDAO;

public class JDBCDAOFactory extends DAOFactory
{
	@Override
	public ICDDAO getCDDAO() throws DAOException
	{
		return new CDJDBCDAO();
	}

	@Override
	public IDVDDAO getDVDDAO() throws DAOException
	{
		return new DVDJDBCDAO();
	}

	@Override
	public IJogoDAO getJogoDAO() throws DAOException
	{
		return new JogoJDBCDAO();
	}
}
