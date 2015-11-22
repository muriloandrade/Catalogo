package br.ufscar.si.catalogo.modelo;

public class ArtistaDVD extends ObjectDTO
{
	private String nome;
	private String papel;

	public ArtistaDVD(String nome, String papel)
	{
		this.nome = nome;
		this.papel = papel;
	}

	public String getNome()
	{
		return nome;
	}

	public String getPapel()
	{
		return papel;
	}
}
