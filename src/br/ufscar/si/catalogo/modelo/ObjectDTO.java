package br.ufscar.si.catalogo.modelo;

import java.io.Serializable;

public abstract class ObjectDTO implements Serializable
{
	private int id;

	public ObjectDTO()
	{
		super();
	}

	public ObjectDTO(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
