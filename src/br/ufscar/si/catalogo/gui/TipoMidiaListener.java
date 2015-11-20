package br.ufscar.si.catalogo.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JRadioButton;

import br.ufscar.si.catalogo.modelo.Catalogo;
import br.ufscar.si.catalogo.modelo.Midia;
import br.ufscar.si.catalogo.modelo.Tipos;

public class TipoMidiaListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		Principal frame = Principal.getFrame();
		Catalogo catalogo = frame.getCatalogo();
		JLabel lblListagem = frame.getLabelListagem();
		JLabel lblTotalMidias = frame.getLabelTotalMidias();
		
		//Limpa item selecionado da tabela
		frame.getTabela().clearSelection();
		
		//Desabilita os botoes: Visualizar, Editar e Remover
		frame.botoesVER_SetEnable(false);
		
		String actionCommand = ((JRadioButton) e.getSource()).getActionCommand();

		if (actionCommand.equals("Todos"))
		{
			lblListagem.setText("Lista: Todas as mídias");
			ArrayList<Midia> todasMidias = (ArrayList<Midia>) catalogo.colecao();
			lblTotalMidias.setText("Total: " + todasMidias.size() + (todasMidias.size() == 1 ? " mídia" : " mídias"));
			frame.recriaTabela(todasMidias);
		}
		else if (actionCommand.equals("CDs"))
		{
			lblListagem.setText("Lista: CDs");
			ArrayList<Midia> todosCDs = (ArrayList<Midia>) catalogo.colecaoPorTipo(Tipos.CD);
			lblTotalMidias.setText("Total: " + todosCDs.size() + (todosCDs.size() == 1 ? " CD" : " CDs"));
			frame.recriaTabela(todosCDs);
		}
		else if (actionCommand.equals("DVDs"))
		{
			lblListagem.setText("Lista: DVDs");
			ArrayList<Midia> todosDVDs = (ArrayList<Midia>) catalogo.colecaoPorTipo(Tipos.DVD);
			lblTotalMidias.setText("Total: " + todosDVDs.size() + (todosDVDs.size() == 1 ? " DVD" : " DVDs"));
			frame.recriaTabela(todosDVDs);
		}
		else if (actionCommand.equals("Jogos"))
		{
			lblListagem.setText("Lista: Jogos");
			ArrayList<Midia> todosJogos = (ArrayList<Midia>) catalogo.colecaoPorTipo(Tipos.Jogo);
			lblTotalMidias.setText("Total: " + todosJogos.size() + (todosJogos.size() == 1 ? " Jogo" : " Jogos"));
			frame.recriaTabela(todosJogos);
		}
	}

}
