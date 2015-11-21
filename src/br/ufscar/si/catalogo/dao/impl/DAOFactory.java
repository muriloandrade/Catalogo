package br.ufscar.si.catalogo.dao.impl;

import br.ufscar.si.catalogo.dao.spec.ICDDAO;
import br.ufscar.si.catalogo.dao.spec.IDVDDAO;
import br.ufscar.si.catalogo.dao.spec.IJogoDAO;

public abstract class DAOFactory
{
	private static DAOFactory instance = null;

	public static DAOFactory getInstance()
	{
		if (instance == null)
		{
			instance = new JDBCDAOFactory();
		}
		return instance;
	}

	public abstract ICDDAO getCDDAO() throws DAOException;

	public abstract IDVDDAO getDVDDAO() throws DAOException;

	public abstract IJogoDAO getJogoDAO() throws DAOException;
}