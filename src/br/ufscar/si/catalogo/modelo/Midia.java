package br.ufscar.si.catalogo.modelo;

import java.io.Serializable;

public abstract class Midia implements Comparable<Midia>, Serializable
{
	private String titulo;
	private Integer anoCriacao;

	public Midia(String titulo, int anoCriacao)
	{
		this.titulo = titulo;
		this.anoCriacao = anoCriacao;
	}

	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}

	public void setAnoCriacao(int anoCriacao)
	{
		this.anoCriacao = anoCriacao;
	}

	public String getTitulo()
	{
		return titulo;
	}

	public int getAnoCriacao()
	{
		return anoCriacao;
	}

	public int compareTo(Midia m)
	{
		int compAnoCriacao = this.anoCriacao.compareTo(m.getAnoCriacao());

		// Se ano de criacao for diferente, retorna o resultado dessa
		// comparacao.
		// Se ano de criacao for igual, retorna a comparacao pelo titulo.
		return compAnoCriacao != 0 ? compAnoCriacao : this.getTitulo().compareTo(m.getTitulo());
	}

	// Metodos a serem sobrescritos por cada tipo de midia
	public abstract Tipos getTipo();
}