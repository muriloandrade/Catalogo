package br.ufscar.si.catalogo.modelo;

import java.io.Serializable;
import java.text.ParseException;

public class FaixaCD implements Serializable
{
	private int numero;
	private String faixa;
	private DuracaoFaixa duracao;

	public FaixaCD(int numero, String faixa, DuracaoFaixa duracao) throws ParseException
	{
		this.numero = numero;
		this.faixa = faixa;
		this.duracao = duracao;
	}
	
	public int getNumero()
	{
		return numero;
	}

	public String getFaixa()
	{
		return faixa;
	}

	public DuracaoFaixa getDuracao()
	{
		return duracao;
	}
}
