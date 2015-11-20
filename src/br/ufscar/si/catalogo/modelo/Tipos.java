package br.ufscar.si.catalogo.modelo;

public enum Tipos
{
	NULO(0), CD(1), DVD(2), Jogo(3);

	private int indice;

	Tipos(int indice)
	{
		this.indice = indice;
	}

	public int getIndice()
	{
		return indice;
	}

	@Override
	public String toString()
	{
		return this.name();
	}
}
