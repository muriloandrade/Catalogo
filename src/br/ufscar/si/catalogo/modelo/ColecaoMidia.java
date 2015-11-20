package br.ufscar.si.catalogo.modelo;

import java.util.Collection;

public interface ColecaoMidia
{
	public Collection<Midia> colecao();

	public Collection<Midia> colecaoPorTipo(Tipos tipo);
}
