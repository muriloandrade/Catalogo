package br.ufscar.si.catalogo.modelo;

import java.io.Serializable;

public class ArtistaDVD implements Serializable
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
