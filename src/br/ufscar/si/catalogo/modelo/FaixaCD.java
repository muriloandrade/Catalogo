package br.ufscar.si.catalogo.modelo;

import java.text.ParseException;

public class FaixaCD
{
	private int numero;
	private String nome;
	private DuracaoFaixa duracao;

	public FaixaCD(int numero, String nome, DuracaoFaixa duracao) throws ParseException
	{
		this.numero = numero;
		this.nome = nome;
		this.duracao = duracao;
	}

	public int getNumero()
	{
		return numero;
	}

	public String getNome()
	{
		return nome;
	}

	public DuracaoFaixa getDuracao()
	{
		return duracao;
	}
}
