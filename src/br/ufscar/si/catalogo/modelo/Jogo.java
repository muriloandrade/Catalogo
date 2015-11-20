package br.ufscar.si.catalogo.modelo;

public class Jogo extends Midia
{
	private String genero;

	public Jogo(String titulo, int anoCriacao, String genero)
	{
		super(titulo, anoCriacao);
		this.genero = genero;
	}

	public String getGenero()
	{
		return genero;
	}

	// Sobrescreve metodo de Midia
	public Tipos getTipo()
	{
		return Tipos.Jogo;
	}
}
