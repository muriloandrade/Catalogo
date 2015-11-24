package br.ufscar.si.catalogo.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import br.ufscar.si.catalogo.dao.impl.CDJDBCDAO;
import br.ufscar.si.catalogo.dao.impl.DAOException;
import br.ufscar.si.catalogo.dao.impl.DVDJDBCDAO;
import br.ufscar.si.catalogo.dao.impl.GenericJDBCDAO;
import br.ufscar.si.catalogo.dao.impl.JogoJDBCDAO;
import br.ufscar.si.catalogo.modelo.Catalogo;
import br.ufscar.si.catalogo.modelo.Midia;

public class BotoesListener implements ActionListener
{
	public void actionPerformed(ActionEvent event)
	{
		Principal frame = Principal.getFrame();
		String actionCommand;
		JTable tabela = frame.getTabela();
		Catalogo catalogo = frame.getCatalogo();
		
		if (event.getSource() instanceof JMenuItem)
		{
			 actionCommand = ((JMenuItem) event.getSource()).getActionCommand();
		}
		else
		{
			actionCommand = ((JButton) event.getSource()).getActionCommand();
		}

		// VISUALIZAR
		if (actionCommand.equals("Visualizar"))
		{
			VisualizarMidia visualizarMidia = new VisualizarMidia(frame, true, (Midia) tabela.getValueAt(tabela
					.getSelectedRow(), 3));
			visualizarMidia.setVisible(true);
		}

		// EDITAR
		else if (actionCommand.equals("Editar"))
		{
			EditarMidia editarMidia = new EditarMidia(frame, true, catalogo, (Midia) tabela.getValueAt(tabela
					.getSelectedRow(), 3));
			editarMidia.setVisible(true);
			frame.clicaBotaoTodosMenu();
		}

		// REMOVER
		else if (actionCommand.equals("Remover"))
		{
			String titulo = (String) tabela.getValueAt(tabela.getSelectedRow(), 1);

			// Mensagem para confirmar exclusão
			int option = JOptionPane.showConfirmDialog(frame, "Deseja realmente excluir '" + titulo + "' do catálogo?",
					"Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (option == JOptionPane.YES_OPTION)
			{
				Midia midia = (Midia) tabela.getValueAt(tabela.getSelectedRow(), 3);
				catalogo.removeMidia(midia);

				GenericJDBCDAO genericDAO = null;

				switch (midia.getTipo())
				{
					case CD:
						genericDAO = new CDJDBCDAO();
						break;
					case DVD:
						genericDAO = new DVDJDBCDAO();
						break;
					case Jogo:
						genericDAO = new JogoJDBCDAO();
						break;
				}

				try
				{
					genericDAO.delete(midia.getId());
				}
				catch (DAOException e)
				{
					JOptionPane.showMessageDialog(frame, "Erro de operação ao acessar banco de dados.",
							"Excluir catálogo", JOptionPane.ERROR_MESSAGE);
				}

				JOptionPane.showMessageDialog(frame, "Exclusão realizada com sucesso.", "Excluir mídia",
						JOptionPane.INFORMATION_MESSAGE);
				frame.clicaBotaoTodosMenu();
			}
		}

		// INSERIR NOVA MÍDIA
		else if (actionCommand.equals("Inserir"))
		{
			// Somente exibe a janela para inserir nova mídia caso não tenha
			// atingido a capacidade máxima
			if (catalogo.quantidadeDeMidias() < catalogo.getCapacidade())
			{
				InserirMidia d_inserir = new InserirMidia(Principal.getFrame(), true, catalogo);
				d_inserir.setVisible(true);
				frame.clicaBotaoTodosMenu();
			}
			else
			{
				JOptionPane
						.showMessageDialog(frame,
								"Não é possível inserir novas mídias. Capacidade máxima atingida.\nEste catálogo suporta no máximo "
										+ catalogo.getCapacidade() + " mídias", "Inserir nova mídia",
								JOptionPane.ERROR_MESSAGE);
			}

		}

		// LOCALIZAR MÍDIA
		else if (actionCommand.equals("Localizar"))
		{
			Localizar d_localizar = new Localizar(Principal.getFrame(), true, catalogo);
			d_localizar.setVisible(true);
			frame.getLabelListagem().setText("Lista: Resultado da pesquisa por " + d_localizar.getModo() + " = '" + d_localizar.getTermo() + "'");
			frame.limparBotoesTipo();
			tabela.clearSelection();
			frame.botoesVER_SetEnable(false);
			ArrayList<Midia> midiasEncontradas = d_localizar.getMidiasEncontradas();
			frame.recriaTabela(midiasEncontradas);
			frame.getLabelTotalMidias().setText("Total: " + midiasEncontradas.size() + (midiasEncontradas.size() == 1 ? " mídia" : " mídias"));
		}
	}
}
