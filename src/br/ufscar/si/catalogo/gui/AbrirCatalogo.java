package br.ufscar.si.catalogo.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import br.ufscar.si.catalogo.dao.impl.CatalogoJDBCDAO;
import br.ufscar.si.catalogo.dao.impl.DAOException;
import br.ufscar.si.catalogo.modelo.Catalogo;
import br.ufscar.si.catalogo.modelo.ObjectDTO;

/*
 * Janela JDialog para Criar/Abrir catálogo
 */
public class AbrirCatalogo extends JDialog
{

	private Catalogo catalogo = null;
	private AbrirCatalogo dialogoAbrirCatalogo;
	private JTable tableCatalogos;

	private JButton btnNovo = null;
	private JButton btnExcluir = null;
	private JButton btnAbrir = null;

	public AbrirCatalogo(final JFrame owner, boolean modal)
	{
		super(owner, modal);
		setResizable(false);
		dialogoAbrirCatalogo = this;

		setTitle("Abrir Cat\u00E1logo");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 230);
		setLocationRelativeTo(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		getContentPane().add(scrollPane, gbc_scrollPane);

		tableCatalogos = new JTable();
		tableCatalogos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCatalogos.setFillsViewportHeight(true);
		scrollPane.setViewportView(tableCatalogos);
		recriarTabela();

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(5, 5, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(panel, gbc_panel);

		btnNovo = new JButton("Novo...");

		btnAbrir = new JButton("Abrir");
		btnAbrir.setEnabled(false);
		btnAbrir.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				catalogo = (Catalogo) tableCatalogos.getValueAt(tableCatalogos.getSelectedRow(), 2);
				dispose();
			}
		});

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CatalogoJDBCDAO catalogoDAO = new CatalogoJDBCDAO();
				catalogo = (Catalogo) tableCatalogos.getValueAt(tableCatalogos.getSelectedRow(), 2);
				int confirma_exclusao = JOptionPane.showConfirmDialog(dialogoAbrirCatalogo,
						"Tem certeza que deseja excluir " + catalogo.getNome()
								+ "?\nTodas as mídias deste catálogo serão excluídas.", "Excluir mídia",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (confirma_exclusao == JOptionPane.YES_OPTION)
				{
					try
					{
						catalogoDAO.delete(catalogo.getId());
					}
					catch (DAOException e1)
					{
						JOptionPane.showMessageDialog(dialogoAbrirCatalogo, "Erro ao acessar banco de dados.",
								"Excluir catálogo", JOptionPane.ERROR_MESSAGE);
					}
					recriarTabela();
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup().addComponent(btnNovo).addGap(8).addComponent(btnExcluir)
						.addPreferredGap(ComponentPlacement.RELATED, 241, Short.MAX_VALUE).addComponent(btnAbrir)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup().addGap(5).addGroup(
						gl_panel.createParallelGroup(Alignment.BASELINE, false).addComponent(btnAbrir).addComponent(
								btnNovo).addComponent(btnExcluir))));
		panel.setLayout(gl_panel);
		btnNovo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				NovoCatalogo novoCatalogo = new NovoCatalogo(owner, true);
				novoCatalogo.setVisible(true);
				recriarTabela();
			}
		});
	}

	public Catalogo getCatalogo()
	{
		return catalogo;
	}

	public static String converteParaDAT(String nomeArquivo)
	{
		if (!nomeArquivo.substring(nomeArquivo.length() - 3).equals("dat")) nomeArquivo += ".dat";
		return nomeArquivo;
	}

	private void recriarTabela()
	{
		tableCatalogos.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nome", "Capacidade",
				"CatalogoObj" })
		{
			Class[] columnTypes = new Class[] { String.class, Integer.class, Catalogo.class };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		});
		tableCatalogos.getColumnModel().getColumn(0).setPreferredWidth(150);
		tableCatalogos.getColumnModel().getColumn(0).setMinWidth(125);
		tableCatalogos.getColumnModel().getColumn(1).setMinWidth(50);
		tableCatalogos.getColumnModel().getColumn(1).setMaxWidth(75);
		tableCatalogos.getColumnModel().getColumn(2).setMinWidth(0);
		tableCatalogos.getColumnModel().getColumn(2).setMaxWidth(0);
		tableCatalogos.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if (tableCatalogos.getSelectedRow() != -1)
				{
					if (btnExcluir != null) btnExcluir.setEnabled(true);
					if (btnAbrir != null) btnAbrir.setEnabled(true);
				}
			}
		});

		CatalogoJDBCDAO catalogoDAO = new CatalogoJDBCDAO();

		try
		{
			ArrayList<ObjectDTO> catalogos = (ArrayList<ObjectDTO>) catalogoDAO.selectAll();
			for (ObjectDTO catalogo : catalogos)
			{
				String nome = ((Catalogo) catalogo).getNome();
				int capacidade = ((Catalogo) catalogo).getCapacidade();
				((DefaultTableModel) tableCatalogos.getModel()).addRow(new Object[] { nome, capacidade, catalogo });
			}
		}
		catch (DAOException e)
		{
			JOptionPane.showMessageDialog(dialogoAbrirCatalogo, "Erro ao acessar banco de dados.", "Abrir catálogo",
					JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}
}
