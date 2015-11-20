package br.ufscar.si.catalogo.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import br.ufscar.si.catalogo.modelo.Catalogo;
import br.ufscar.si.catalogo.modelo.Midia;
import br.ufscar.si.catalogo.modelo.SerializadorCatalogo;

public class BotoesListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		Principal frame = Principal.getFrame();
		String actionCommand = ((JButton) e.getSource()).getActionCommand();
		JTable tabela = frame.getTabela();
		Catalogo catalogo = frame.getCatalogo();

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
			frame.clicaBotaoTodos();
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
				try
				{
					SerializadorCatalogo.gravaCatalogo(catalogo, catalogo.getArquivo());
					JOptionPane.showMessageDialog(frame, "Exclusão realizada com sucesso.", "Excluir mídia",
							JOptionPane.INFORMATION_MESSAGE);
				}
				catch (IOException e1)
				{
					JOptionPane.showMessageDialog(frame, "Não foi possível salvar a exclusão.", "Excluir mídia",
							JOptionPane.ERROR_MESSAGE);
				}
				frame.clicaBotaoTodos();
			}
		}

		// INSERIR NOVA MÍDIA
		else if (actionCommand.equals("Inserir"))
		{
			// Somente exibe a janela para inserir nova mídia caso não tenha
			// atingido a capacidade máxima
			if (catalogo.quantidadeDeMidias() < catalogo.quantidadeMaximaDeMidias())
			{
				InserirMidia d_inserir = new InserirMidia(Principal.getFrame(), true, catalogo);
				d_inserir.setVisible(true);
				frame.clicaBotaoTodos();
			}
			else
			{
				JOptionPane.showMessageDialog(frame,
						"Não é possível inserir novas mídias. Capacidade máxima atingida.\nEste catálogo suporta no máximo "
								+ catalogo.quantidadeMaximaDeMidias() + " mídias", "Inserir nova mídia",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		// LOCALIZAR MÍDIA
		else if (actionCommand.equals("Localizar"))
		{
			Localizar d_localizar = new Localizar(Principal.getFrame(), true, catalogo);
			d_localizar.setVisible(true);
			d_localizar.dispose();
			if (d_localizar.getValue() != null)
			{
				frame.getLabelListagem().setText("Lista: Resultado da pesquisa por '" + d_localizar.getValue() + "'");
				frame.limparBotoesTipo();
				tabela.clearSelection();
				frame.botoesVER_SetEnable(false);
			}
		}
	}
}
