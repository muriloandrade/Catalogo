package br.ufscar.si.catalogo.modelo;

public abstract class ObjectDTO
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
