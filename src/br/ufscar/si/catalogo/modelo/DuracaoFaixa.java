package br.ufscar.si.catalogo.modelo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DuracaoFaixa
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

	public int duracaoEmSegundos()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(duracao);
		return (c.get(Calendar.MINUTE) * 60) + c.get(Calendar.SECOND);
	}
}
