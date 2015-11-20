package br.ufscar.si.catalogo.modelo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DuracaoFaixa implements Serializable
{
	private static String DURACAO = "mm:ss";
	private static DateFormat FORMATO = new SimpleDateFormat(DURACAO);

	private Date duracao;

	public DuracaoFaixa(String duracao) throws ParseException
	{
		this.duracao = FORMATO.parse(duracao);
	}

	@Override
	public String toString()
	{
		return FORMATO.format(duracao);
	}
}
