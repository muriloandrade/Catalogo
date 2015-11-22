package br.ufscar.si.catalogo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.ufscar.si.catalogo.modelo.Catalogo;
import br.ufscar.si.catalogo.modelo.Midia;

/*
 * Frame principal do programa.
 * Aloca a JMenuBar e um JPanel (contentPane).
 * Todas as outras janelas que abrem são JDialogs modais deste frame.
 */
public class Principal extends JFrame
{
	private static Catalogo catalogo;
	private static AbrirCatalogo abrirCatalogo;

	private static JPanel contentPane;
	private static Principal frame;

	private JTable table;

	private JLabel lblListagem = new JLabel("Lista: Todas as m\u00EDdias");
	private JLabel lblTotalMidias = new JLabel("Total: m\u00EDdias");

	private ButtonGroup grupoBotoesTipo;
	private JRadioButton rdbtnTodos;
	private JRadioButton rdbtnCds;
	private JRadioButton rdbtnDvds;
	private JRadioButton rdbtnJogos;
	private JButton btnLocalizarMdia;
	private JButton btnVisualizar;
	private JButton btnEditar;
	private JButton btnRemover;
	private JButton btnInserirNovaMidia;

	// Inicia a execução do programa na fila do thread
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					frame = new Principal();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Principal()
	{
		// Tela para criar novo arquivo ou abrir existente
		catalogo = abrirCatalogo();

		// Cria a interface principal
		geraInterface();
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					frame.atribuiListeners();

					// Exibe a tabela com todas as midias
					frame.clicaBotaoTodos();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	// Getters
	public static Principal getFrame()
	{
		return frame;
	}

	public Catalogo getCatalogo()
	{
		return catalogo;
	}

	public JTable getTabela()
	{
		return table;
	}

	public JLabel getLabelListagem()
	{
		return lblListagem;
	}

	public JLabel getLabelTotalMidias()
	{
		return lblTotalMidias;
	}

	public void limparBotoesTipo()
	{
		grupoBotoesTipo.clearSelection();
	}

	// Setters
	public void botoesVER_SetEnable(boolean enable)
	{
		btnVisualizar.setEnabled(enable);
		btnEditar.setEnabled(enable);
		btnRemover.setEnabled(enable);
	}

	/*
	 * Abre uma janela para Criar/Abrir catálogo Retorna o catálogo
	 * criado/aberto
	 */
	private Catalogo abrirCatalogo()
	{
		abrirCatalogo = new AbrirCatalogo(this, true);
		abrirCatalogo.setVisible(true);

		Catalogo catalogo = abrirCatalogo.getCatalogo();

		if (catalogo == null)
		{
			System.exit(0);
		}
		return catalogo;
	}

	/*
	 * Gera a interface principal do programa.
	 */
	private void geraInterface()
	{
		// Configurações iniciais
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Cat\u00E1logo");
		setMinimumSize(new Dimension(720, 540));
		setBounds(new Rectangle(250, 250, 720, 480));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Itens do menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);

		JMenuItem mntmNovo = new JMenuItem("Novo");
		mnArquivo.add(mntmNovo);

		JMenuItem mntmAbrir = new JMenuItem("Abrir...");
		mnArquivo.add(mntmAbrir);

		JMenuItem mntmSalvar = new JMenuItem("Salvar");
		mnArquivo.add(mntmSalvar);

		JMenuItem mntmSalvarComo = new JMenuItem("Salvar como...");
		mnArquivo.add(mntmSalvarComo);

		JSeparator separator = new JSeparator();
		mnArquivo.add(separator);

		JMenuItem mntmSair = new JMenuItem("Sair");
		mnArquivo.add(mntmSair);

		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);

		JMenuItem mntmInserirMidia = new JMenuItem("Inserir M\u00EDdia");
		mnEditar.add(mntmInserirMidia);

		JMenuItem mntmRemoverMdia = new JMenuItem("Remover M\u00EDdia");
		mnEditar.add(mntmRemoverMdia);

		JMenuItem mntmAlterarMdia = new JMenuItem("Alterar M\u00EDdia");
		mnEditar.add(mntmAlterarMdia);

		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);

		JMenuItem mntmTpicosDaAjuda = new JMenuItem("T\u00F3picos da Ajuda");
		mnAjuda.add(mntmTpicosDaAjuda);

		JSeparator separator_1 = new JSeparator();
		mnAjuda.add(separator_1);

		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mnAjuda.add(mntmSobre);

		// Definições do ContentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWeights = new double[] { 1.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0 };
		contentPane.setLayout(gbl_contentPane);

		// Label do Título
		JLabel lblCatlogoDeMdias = new JLabel("Cat\u00E1logo de M\u00EDdias");
		lblCatlogoDeMdias.setForeground(Color.GRAY);
		lblCatlogoDeMdias.setFont(new Font("Dialog", Font.PLAIN, 22));
		GridBagConstraints gbc_lblCatlogoDeMdias = new GridBagConstraints();
		gbc_lblCatlogoDeMdias.insets = new Insets(5, 0, 15, 0);
		gbc_lblCatlogoDeMdias.anchor = GridBagConstraints.NORTH;
		gbc_lblCatlogoDeMdias.gridx = 0;
		gbc_lblCatlogoDeMdias.gridy = 0;
		contentPane.add(lblCatlogoDeMdias, gbc_lblCatlogoDeMdias);

		// JPanel de botões para escolher o tipo de mídia
		JPanel pnTipo = new JPanel();
		pnTipo.setPreferredSize(new Dimension(0, 0));
		pnTipo.setMinimumSize(new Dimension(0, 0));
		pnTipo.setBorder(new TitledBorder(null, "Tipo de M\u00EDdia", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnTipo = new GridBagConstraints();
		gbc_pnTipo.fill = GridBagConstraints.BOTH;
		gbc_pnTipo.insets = new Insets(5, 5, 5, 5);
		gbc_pnTipo.gridx = 0;
		gbc_pnTipo.gridy = 1;
		contentPane.add(pnTipo, gbc_pnTipo);

		// Botoes 'Tipos de Mídia'
		rdbtnTodos = new JRadioButton("Todos");
		rdbtnTodos.setActionCommand("Todos");
		rdbtnTodos.setPreferredSize(new Dimension(0, 0));
		rdbtnTodos.setMaximumSize(new Dimension(0, 0));
		rdbtnTodos.setMinimumSize(new Dimension(0, 0));

		rdbtnCds = new JRadioButton("CDs");
		rdbtnCds.setActionCommand("CDs");
		rdbtnCds.setPreferredSize(new Dimension(0, 0));
		rdbtnCds.setMaximumSize(new Dimension(0, 0));
		rdbtnCds.setMinimumSize(new Dimension(0, 0));

		rdbtnDvds = new JRadioButton("DVDs");
		rdbtnDvds.setActionCommand("DVDs");
		rdbtnDvds.setPreferredSize(new Dimension(0, 0));
		rdbtnDvds.setMaximumSize(new Dimension(0, 0));
		rdbtnDvds.setMinimumSize(new Dimension(0, 0));

		rdbtnJogos = new JRadioButton("Jogos");
		rdbtnJogos.setActionCommand("Jogos");
		rdbtnJogos.setPreferredSize(new Dimension(0, 0));
		rdbtnJogos.setMaximumSize(new Dimension(0, 0));
		rdbtnJogos.setMinimumSize(new Dimension(0, 0));

		grupoBotoesTipo = new ButtonGroup();
		grupoBotoesTipo.add(rdbtnTodos);
		grupoBotoesTipo.add(rdbtnCds);
		grupoBotoesTipo.add(rdbtnDvds);
		grupoBotoesTipo.add(rdbtnJogos);

		// Botão para busca personalizada de mídia
		btnLocalizarMdia = new JButton("Localizar m\u00EDdia");
		btnLocalizarMdia.setActionCommand("Localizar");
		btnLocalizarMdia.setPreferredSize(new Dimension(0, 35));

		// Layout dos botões de seleção de mídia
		GroupLayout gl_pnTipo = new GroupLayout(pnTipo);
		gl_pnTipo.setHorizontalGroup(gl_pnTipo.createParallelGroup(Alignment.LEADING).addGroup(gl_pnTipo
				.createSequentialGroup().addContainerGap()
				.addComponent(rdbtnTodos, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(rdbtnCds, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(rdbtnDvds, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(rdbtnJogos, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
				.addComponent(btnLocalizarMdia, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
				.addContainerGap()));
		gl_pnTipo
				.setVerticalGroup(gl_pnTipo
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnTipo
								.createSequentialGroup()
								.addGap(0)
								.addGroup(gl_pnTipo
										.createParallelGroup(Alignment.BASELINE, false)
										.addComponent(rdbtnTodos, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(rdbtnCds, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(rdbtnDvds, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(rdbtnJogos, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnLocalizarMdia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(25)));
		pnTipo.setLayout(gl_pnTipo);

		// JPanel para exibir as mídias do catálogo
		JPanel pnMidias = new JPanel();
		pnMidias.setPreferredSize(new Dimension(0, 0));
		pnMidias.setMinimumSize(new Dimension(0, 0));
		pnMidias.setBorder(new TitledBorder(null, "M\u00EDdias no cat\u00E1logo", TitledBorder.LEFT, TitledBorder.TOP,
				null, null));
		GridBagConstraints gbc_pnMidias = new GridBagConstraints();
		gbc_pnMidias.weighty = 5.0;
		gbc_pnMidias.insets = new Insets(5, 5, 5, 5);
		gbc_pnMidias.fill = GridBagConstraints.BOTH;
		gbc_pnMidias.gridx = 0;
		gbc_pnMidias.gridy = 2;
		contentPane.add(pnMidias, gbc_pnMidias);
		pnMidias.setLayout(new BorderLayout(0, 0));

		// Labels informativas das mídias exibidas
		// Obs: essas Labels foram pré instanciadas, pois os botões de escolha
		// do 'Tipo' alteram seus valores através de seus listeners (cujas
		// instâncias foram criadas acima).
		JPanel panel_1 = new JPanel();
		pnMidias.add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 127, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 16, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		lblListagem.setForeground(UIManager.getColor("OptionPane.errorDialog.border.background"));
		GridBagConstraints gbc_lblListagem = new GridBagConstraints();
		gbc_lblListagem.insets = new Insets(5, 12, 8, 0);
		gbc_lblListagem.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblListagem.gridx = 0;
		gbc_lblListagem.gridy = 0;
		panel_1.add(lblListagem, gbc_lblListagem);

		lblTotalMidias.setForeground(UIManager.getColor("OptionPane.errorDialog.border.background"));
		GridBagConstraints gbc_lblTotalMidias = new GridBagConstraints();
		gbc_lblTotalMidias.insets = new Insets(5, 0, 8, 12);
		gbc_lblTotalMidias.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblTotalMidias.gridx = 2;
		gbc_lblTotalMidias.gridy = 0;
		panel_1.add(lblTotalMidias, gbc_lblTotalMidias);

		// Tabela de mídias dentro de uma janela scroll
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		pnMidias.add(scrollPane, BorderLayout.CENTER);
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(20);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		// Botoes CRUD (Visualizar, Editar, Remover, Inserir)
		btnVisualizar = new JButton("Visualizar");
		btnVisualizar.setActionCommand("Visualizar");
		btnVisualizar.setEnabled(false);
		btnVisualizar.setPreferredSize(new Dimension(100, 35));

		btnEditar = new JButton("Editar");
		btnEditar.setActionCommand("Editar");
		btnEditar.setEnabled(false);
		btnEditar.setPreferredSize(new Dimension(100, 35));

		btnRemover = new JButton("Remover");
		btnRemover.setActionCommand("Remover");
		btnRemover.setEnabled(false);
		btnRemover.setPreferredSize(new Dimension(100, 35));

		JPanel panel = new JPanel();
		pnMidias.add(panel, BorderLayout.SOUTH);
		btnInserirNovaMidia = new JButton("Inserir Nova M\u00EDdia");
		btnInserirNovaMidia.setActionCommand("Inserir");
		btnInserirNovaMidia.setPreferredSize(new Dimension(152, 35));

		// Layout dos botões CRUD
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnVisualizar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnRemover, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
						.addComponent(btnInserirNovaMidia, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel
						.createSequentialGroup()
						.addContainerGap(25, Short.MAX_VALUE)
						.addGroup(gl_panel
								.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnVisualizar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRemover, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnInserirNovaMidia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		panel.setLayout(gl_panel);

		// Ajustes finais de layout do contentPane
		Component horizontalStrut = Box.createHorizontalStrut(10);
		pnMidias.add(horizontalStrut, BorderLayout.EAST);

		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		pnMidias.add(horizontalStrut_1, BorderLayout.WEST);

		// Clica o radioButton 'Todos' para exibir inicialmente todas as mídias
		clicaBotaoTodos();
	}

	public void clicaBotaoTodos()
	{
		rdbtnTodos.doClick();
	}

	public void recriaTabela(ArrayList<Midia> midias)
	{
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Tipo", "T\u00EDtulo", "Ano", "Midia" })
		{
			Class[] columnTypes = new Class[] { String.class, String.class, Integer.class, Midia.class };
			
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}

		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setMinWidth(80);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(1).setMinWidth(250);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setMinWidth(80);
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(0);
		table.getColumnModel().getColumn(3).setMinWidth(0);
		table.getColumnModel().getColumn(3).setMaxWidth(0);

		Midia midia;

		for (int i = table.getRowCount(); i < midias.size(); i++)
		{
			midia = (Midia) ((ArrayList) midias).get(i);
			Object[] valores = { midia.getTipo().toString(), midia.getTitulo(), midia.getAnoCriacao(), midia };
			((DefaultTableModel) table.getModel()).addRow(valores);
		}
	}
	
	private void atribuiListeners()
	{
		rdbtnTodos.addActionListener(new TipoMidiaListener());
		rdbtnCds.addActionListener(new TipoMidiaListener());
		rdbtnDvds.addActionListener(new TipoMidiaListener());
		rdbtnJogos.addActionListener(new TipoMidiaListener());
		btnLocalizarMdia.addActionListener(new BotoesListener());
		table.getSelectionModel().addListSelectionListener(new TabelaListener(this));
		btnVisualizar.addActionListener(new BotoesListener());
		btnEditar.addActionListener(new BotoesListener());
		btnRemover.addActionListener(new BotoesListener());
		btnInserirNovaMidia.addActionListener(new BotoesListener());
	}
	
	public static int getCatalogoID()
	{
		return catalogo.getId();
	}

}
