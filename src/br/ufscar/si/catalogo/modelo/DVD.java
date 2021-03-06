package br.ufscar.si.catalogo.modelo;

public class DVD extends Midia
{
	public static int MAX_ARTISTAS = 5;

	private String diretor;
	private ArtistaDVD[] artistas;
	private int qtdArtistasAdicionados;

	public DVD(String titulo, int anoCriacao, String diretor)
	{
		super(titulo, anoCriacao);

		this.artistas = new ArtistaDVD[MAX_ARTISTAS];
		this.diretor = diretor;

		// Inicia a contagem de artistas adicionadas a este DVD
		this.qtdArtistasAdicionados = 0;
	}

	public void adicionaArtista(String nome, String papel)
	{
		// Somente adiciona se o array nao estiver cheio
		if (qtdArtistasAdicionados < MAX_ARTISTAS)
		{
			artistas[qtdArtistasAdicionados++] = new ArtistaDVD(nome, papel);
		}
	}

	public String getDiretor()
	{
		return diretor;
	}

	// Sobrescreve metodo de Midia
	public Tipos getTipo()
	{
		return Tipos.DVD;
	}

	public ArtistaDVD[] getArtistas()
	{
		return artistas;
	}
}