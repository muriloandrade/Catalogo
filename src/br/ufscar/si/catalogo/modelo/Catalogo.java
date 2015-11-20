package br.ufscar.si.catalogo.modelo;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Catalogo implements ColecaoMidia, Serializable
{
	private static final int MAX_MIDIAS = 300;

	private ArrayList<Midia> midias;
	private int tamMaximo;
	private File arquivo;

	public Catalogo(int tamMaximo)
	{
		// O tamanho maximo se limita a MAX_MIDIAS
		this.tamMaximo = tamMaximo < MAX_MIDIAS ? tamMaximo : MAX_MIDIAS;

		// Cria a colecao
		midias = new ArrayList<Midia>();
	}

	public boolean adicionaMidia(Midia midia)
	{
		// Somente adiciona se nao atingiu limite
		if (midias.size() < tamMaximo) { return midias.add(midia); }

		return false;
	}

	public boolean removeMidia(Midia midia)
	{
		return midias.remove(midia);
	}

	public Midia obtemMidia(String titulo)
	{
		for (Midia midia : midias)
		{
			// Retorna a midia se o titulo for igual ao pretendido
			if (titulo.equals(midia.getTitulo())) return midia;
		}
		// Retorna null se a midia nao for encontrada
		return null;
	}

	public int quantidadeMaximaDeMidias()
	{
		return tamMaximo;
	}

	public int quantidadeDeMidias()
	{
		return midias.size();
	}

	public int quantidadeDeCDs()
	{
		return colecaoPorTipo(Tipos.CD).size();
	}

	public int quantidadeDeDVDs()
	{
		return colecaoPorTipo(Tipos.DVD).size();
	}

	public int quantidadeDeJogos()
	{
		return colecaoPorTipo(Tipos.Jogo).size();
	}

	public Collection<Midia> colecao()
	{
		ArrayList<Midia> colecao = midias;

		// Faz a ordenacao padrao (ano/titulo) da colecao
		Collections.sort(colecao);

		return colecao;
	}

	public Collection<Midia> colecaoPorTipo(Tipos tipo)
	{
		ArrayList<Midia> colecaoPorTipo = new ArrayList<Midia>();

		// Adiciona elementos do tipo selecionado
		for (Midia midia : midias)
		{
			if (midia.getTipo() == tipo) colecaoPorTipo.add(midia);
		}

		// Faz a ordenacao padrao (ano/titulo) da colecao por tipo
		Collections.sort(colecaoPorTipo);

		return colecaoPorTipo;
	}

	public void setArquivo(File arquivo)
	{
		this.arquivo = arquivo;
	}

	public File getArquivo()
	{
		return arquivo;
	}
}
