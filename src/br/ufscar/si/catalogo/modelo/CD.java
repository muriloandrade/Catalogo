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

		// Inicia o array de faixas
		for (int i = 0; i < MAX_FAIXAS; i++)
		{
			try
			{
				adicionaFaixa(new FaixaCD(i + 1, null, null));
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void adicionaFaixa(FaixaCD faixa) throws ParseException
	{
		// Somente adiciona se o array nao estiver cheio
		if (qtdFaixasAdicionadas < MAX_FAIXAS)
		{
			faixas[qtdFaixasAdicionadas++] = faixa;
		}
	}

	public void setFaixa(int pos, FaixaCD faixa)
	{
		faixas[pos] = faixa;
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