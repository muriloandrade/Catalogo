package br.ufscar.si.catalogo.modelo;

import java.text.ParseException;

public class CD extends Midia
{
	public static int MAX_FAIXAS = 15;

	private String artista;
	private FaixaCD[] faixas;
	private int qtdFaixasAdicionadas;

	public CD(String titulo, int anoCriacao, String artista)
	{
		super(titulo, anoCriacao);
		this.artista = artista;
		this.faixas = new FaixaCD[MAX_FAIXAS];

		// Inicia a contagem de faixas adicionadas a este CD
		this.qtdFaixasAdicionadas = 0;
	}

	public void adicionaFaixa(int numero, String faixa, DuracaoFaixa duracao) throws ParseException
	{
		// Somente adiciona se o array nao estiver cheio
		if (qtdFaixasAdicionadas < MAX_FAIXAS)
		{
			faixas[qtdFaixasAdicionadas++] = new FaixaCD(numero, faixa, duracao);
		}
	}

	public String getArtista()
	{
		return artista;
	}

	// Sobrescreve metodo de Midia
	public Tipos getTipo()
	{
		return Tipos.CD;
	}

	public FaixaCD[] getFaixas()
	{
		return faixas;
	}
}