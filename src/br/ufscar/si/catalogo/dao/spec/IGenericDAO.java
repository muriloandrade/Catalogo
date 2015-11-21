package br.ufscar.si.catalogo.dao.spec;

import java.util.List;

import br.ufscar.si.catalogo.dao.impl.DAOException;
import br.ufscar.si.catalogo.modelo.ObjectDTO;

public interface IGenericDAO
{
	List<ObjectDTO> selectAll() throws DAOException;

	void insert(ObjectDTO dto) throws DAOException;

	int selectLastID() throws DAOException;

	ObjectDTO selectByID(int id) throws DAOException;

	void delete(int id) throws DAOException;
}
