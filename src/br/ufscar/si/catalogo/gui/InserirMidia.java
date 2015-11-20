package br.ufscar.si.catalogo.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.ufscar.si.catalogo.modelo.CD;
import br.ufscar.si.catalogo.modelo.Catalogo;
import br.ufscar.si.catalogo.modelo.DVD;
import br.ufscar.si.catalogo.modelo.DuracaoFaixa;
import br.ufscar.si.catalogo.modelo.Jogo;
import br.ufscar.si.catalogo.modelo.SerializadorCatalogo;

/*
 * Janela JDialog para inserir uma nova mídia no catálogo
 */
public class InserirMidia extends JDialog
{
	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private Catalogo catalogo;

	private static JPanel cards;

	private JTextField tituloCD;
	private JTextField anoCD;
	private JTextField artistaCD;
	private JTextField tituloDVD;
	private JTextField anoDVD;
	private JTextField diretorDVD;
	private JTextField tituloJogo;
	private JTextField anoJogo;
	private JTextField generoJogo;
	private JTable tabelaFaixasCD;
	private JTable tabelaArtistasDVD;

	public InserirMidia(Frame owner, boolean modal, Catalogo catalogo)
	{
		super(owner, modal);
		this.catalogo = catalogo;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Inserir M\u00EDdia");
		setBounds(owner.getBounds().x + 120, owner.getBounds().y + 160, 494, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 478, 0 };
		gbl_contentPanel.rowHeights = new int[] { 23, 274, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);

		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.insets = new Insets(5, 5, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPanel.add(panel, gbc_panel);
		panel.setLayout(new GridLayout(1, 4, 0, 0));

		JLabel lblTipo = new JLabel("Tipo:");
		panel.add(lblTipo);

		JRadioButton rdbtnCd = new JRadioButton("CD");
		rdbtnCd.setMnemonic(1);
		rdbtnCd.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				limparCampos();
				((CardLayout) cards.getLayout()).show(cards, "cardCD");
			}
		});
		buttonGroup.add(rdbtnCd);
		rdbtnCd.setSelected(true);
		panel.add(rdbtnCd);

		JRadioButton rdbtnDvd = new JRadioButton("DVD");
		rdbtnDvd.setMnemonic(2);
		rdbtnDvd.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				limparCampos();
				((CardLayout) cards.getLayout()).show(cards, "cardDVD");
			}
		});
		buttonGroup.add(rdbtnDvd);
		panel.add(rdbtnDvd);

		JRadioButton rdbtnJogo = new JRadioButton("Jogo");
		rdbtnJogo.setMnemonic(3);
		rdbtnJogo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				limparCampos();
				((CardLayout) cards.getLayout()).show(cards, "cardJogo");
			}
		});
		buttonGroup.add(rdbtnJogo);
		panel.add(rdbtnJogo);

		cards = new JPanel();
		GridBagConstraints gbc_cards = new GridBagConstraints();
		gbc_cards.insets = new Insets(5, 5, 5, 5);
		gbc_cards.fill = GridBagConstraints.BOTH;
		gbc_cards.gridx = 0;
		gbc_cards.gridy = 1;
		contentPanel.add(cards, gbc_cards);
		cards.setLayout(new CardLayout(0, 0));

		JPanel card_cd = new JPanel();
		card_cd.setBorder(new TitledBorder(null, "CD", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cards.add(card_cd, "cardCD");

		JLabel lblTituloCD = new JLabel("T\u00EDtulo:");

		tituloCD = new JTextField();
		tituloCD.setColumns(10);

		JLabel lblAnoCD = new JLabel("Ano:");

		anoCD = new JTextField();
		anoCD.setColumns(10);

		JLabel lblArtista = new JLabel("Artista:");

		artistaCD = new JTextField();
		artistaCD.setColumns(10);

		JLabel lblFaixas = new JLabel("Faixas");

		JScrollPane scrollPaneCD = new JScrollPane();
		GroupLayout gl_card_cd = new GroupLayout(card_cd);
		gl_card_cd.setHorizontalGroup(gl_card_cd.createParallelGroup(Alignment.LEADING).addGroup(
				gl_card_cd.createSequentialGroup().addGap(5).addGroup(
						gl_card_cd.createParallelGroup(Alignment.LEADING).addGroup(
								gl_card_cd.createSequentialGroup().addGroup(
										gl_card_cd.createParallelGroup(Alignment.TRAILING).addComponent(lblAnoCD)
												.addComponent(lblTituloCD).addGroup(
														gl_card_cd.createParallelGroup(Alignment.LEADING).addComponent(
																lblFaixas).addComponent(lblArtista))).addPreferredGap(
										ComponentPlacement.RELATED).addGroup(
										gl_card_cd.createParallelGroup(Alignment.TRAILING, false).addComponent(anoCD,
												Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(
												tituloCD, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 325,
												Short.MAX_VALUE).addComponent(artistaCD, Alignment.LEADING))
										.addPreferredGap(ComponentPlacement.RELATED, 86, Short.MAX_VALUE))
								.addComponent(scrollPaneCD, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 451,
										Short.MAX_VALUE)).addContainerGap()));
		gl_card_cd.setVerticalGroup(gl_card_cd.createParallelGroup(Alignment.LEADING).addGroup(
				gl_card_cd.createSequentialGroup().addContainerGap().addGroup(
						gl_card_cd.createParallelGroup(Alignment.BASELINE).addComponent(lblTituloCD).addComponent(
								tituloCD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(
						gl_card_cd.createParallelGroup(Alignment.BASELINE).addComponent(lblAnoCD).addComponent(anoCD,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED).addGroup(
								gl_card_cd.createParallelGroup(Alignment.BASELINE).addComponent(lblArtista)
										.addComponent(artistaCD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)).addPreferredGap(
								ComponentPlacement.UNRELATED).addComponent(lblFaixas).addPreferredGap(
								ComponentPlacement.RELATED).addComponent(scrollPaneCD, GroupLayout.DEFAULT_SIZE, 101,
								Short.MAX_VALUE).addGap(16)));

		tabelaFaixasCD = new JTable();
		tabelaFaixasCD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaFaixasCD.getTableHeader().setReorderingAllowed(false);
		recriarTabelaFaixasCD();
		scrollPaneCD.setViewportView(tabelaFaixasCD);
		card_cd.setLayout(gl_card_cd);

		JPanel card_dvd = new JPanel();
		card_dvd.setBorder(new TitledBorder(null, "DVD", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cards.add(card_dvd, "cardDVD");

		JLabel lblTituloDVD = new JLabel("T\u00EDtulo:");

		tituloDVD = new JTextField();
		tituloDVD.setColumns(10);

		JLabel lblAnoDVD = new JLabel("Ano:");

		anoDVD = new JTextField();
		anoDVD.setColumns(10);

		JLabel lblDiretor = new JLabel("Diretor:");

		diretorDVD = new JTextField();
		diretorDVD.setColumns(10);

		JLabel lblArtistas = new JLabel("Artistas");

		JScrollPane scrollPaneDVD = new JScrollPane();
		GroupLayout gl_card_dvd = new GroupLayout(card_dvd);
		gl_card_dvd.setHorizontalGroup(gl_card_dvd.createParallelGroup(Alignment.LEADING).addGroup(
				gl_card_dvd.createSequentialGroup().addGap(5).addGroup(
						gl_card_dvd.createParallelGroup(Alignment.LEADING).addComponent(lblArtistas).addGroup(
								gl_card_dvd.createSequentialGroup().addGroup(
										gl_card_dvd.createParallelGroup(Alignment.TRAILING).addComponent(lblAnoDVD)
												.addComponent(lblTituloDVD).addComponent(lblDiretor)).addPreferredGap(
										ComponentPlacement.RELATED).addGroup(
										gl_card_dvd.createParallelGroup(Alignment.TRAILING, false).addComponent(anoDVD,
												Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(
												tituloDVD, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 325,
												Short.MAX_VALUE).addComponent(diretorDVD, Alignment.LEADING)))
								.addComponent(scrollPaneDVD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)));
		gl_card_dvd.setVerticalGroup(gl_card_dvd.createParallelGroup(Alignment.LEADING).addGroup(
				gl_card_dvd.createSequentialGroup().addContainerGap().addGroup(
						gl_card_dvd.createParallelGroup(Alignment.BASELINE).addComponent(lblTituloDVD).addComponent(
								tituloDVD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(
						gl_card_dvd.createParallelGroup(Alignment.BASELINE).addComponent(lblAnoDVD).addComponent(
								anoDVD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(
						gl_card_dvd.createParallelGroup(Alignment.BASELINE).addComponent(lblDiretor).addComponent(
								diretorDVD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblArtistas).addPreferredGap(ComponentPlacement.RELATED).addComponent(
								scrollPaneDVD, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)));

		tabelaArtistasDVD = new JTable();
		tabelaArtistasDVD.setFillsViewportHeight(true);
		recriarTabelaArtistasDVD();
		scrollPaneDVD.setViewportView(tabelaArtistasDVD);
		card_dvd.setLayout(gl_card_dvd);

		JPanel card_Jogo = new JPanel();
		card_Jogo.setBorder(new TitledBorder(null, "Jogo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cards.add(card_Jogo, "cardJogo");

		JLabel lblTituloJogo = new JLabel("T\u00EDtulo:");

		tituloJogo = new JTextField();
		tituloJogo.setColumns(10);

		JLabel lblAnoJogo = new JLabel("Ano:");

		anoJogo = new JTextField();
		anoJogo.setColumns(10);

		JLabel lblGenero = new JLabel("G\u00EAnero:");

		generoJogo = new JTextField();
		generoJogo.setColumns(10);
		GroupLayout gl_card_jogo = new GroupLayout(card_Jogo);
		gl_card_jogo.setHorizontalGroup(gl_card_jogo.createParallelGroup(Alignment.LEADING).addGroup(
				gl_card_jogo.createSequentialGroup().addGap(5).addGroup(
						gl_card_jogo.createParallelGroup(Alignment.TRAILING).addComponent(lblAnoJogo).addComponent(
								lblTituloJogo).addComponent(lblGenero)).addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_card_jogo.createParallelGroup(Alignment.TRAILING, false).addComponent(anoJogo,
										Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).addComponent(tituloJogo, Alignment.LEADING,
										GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE).addComponent(generoJogo,
										Alignment.LEADING)).addContainerGap(85, Short.MAX_VALUE)));
		gl_card_jogo.setVerticalGroup(gl_card_jogo.createParallelGroup(Alignment.LEADING).addGroup(
				gl_card_jogo.createSequentialGroup().addContainerGap().addGroup(
						gl_card_jogo.createParallelGroup(Alignment.BASELINE).addComponent(lblTituloJogo).addComponent(
								tituloJogo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(
						gl_card_jogo.createParallelGroup(Alignment.BASELINE).addComponent(lblAnoJogo).addComponent(
								anoJogo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(
						gl_card_jogo.createParallelGroup(Alignment.BASELINE).addComponent(lblGenero).addComponent(
								generoJogo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addContainerGap(258, Short.MAX_VALUE)));
		card_Jogo.setLayout(gl_card_jogo);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(5, 5, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		contentPanel.add(panel_1, gbc_panel_1);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				switch (buttonGroup.getSelection().getMnemonic())
				{
					case 1:
						insereCD();
						break;
					case 2:
						insereDVD();
						break;
					case 3:
						insereJogo();
				}
			}
		});
		panel_1.add(btnOk);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		panel_1.add(btnCancelar);
	}

	private void insereCD()
	{
		String titulo = tituloCD.getText();
		Integer anoCriacao;
		String artista = artistaCD.getText();

		try
		{
			anoCriacao = Integer.parseInt(anoCD.getText());
			if (anoCD.getText().length() != 4) throw new NumberFormatException();
			CD cd = new CD(titulo, anoCriacao, artista);

			String nome;
			DuracaoFaixa duracao;

			for (int i = 0; i < tabelaFaixasCD.getRowCount(); i++)
			{
				nome = (String) tabelaFaixasCD.getValueAt(i, 1);
				duracao = (DuracaoFaixa) tabelaFaixasCD.getValueAt(i, 2);
				cd.adicionaFaixa(i + 1, nome, duracao);
			}
			catalogo.adicionaMidia(cd);
			salvar();
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(contentPanel, "Formato incorreto no campo 'Ano'", "Valor incorreto",
					JOptionPane.ERROR_MESSAGE);
		}
		catch (ParseException e)
		{
			JOptionPane.showMessageDialog(contentPanel, "Formato incorreto no campo 'Duracao'", "Valor incorreto",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void insereDVD()
	{
		String titulo = tituloDVD.getText();
		Integer anoCriacao;
		String diretor = diretorDVD.getText();

		try
		{
			anoCriacao = Integer.parseInt(anoDVD.getText());
			if (anoDVD.getText().length() != 4) throw new NumberFormatException();
			DVD dvd = new DVD(titulo, anoCriacao, diretor);

			String artista;
			String papel;

			for (int i = 0; i < tabelaArtistasDVD.getRowCount(); i++)
			{
				artista = (String) tabelaArtistasDVD.getValueAt(i, 0);
				papel = (String) tabelaArtistasDVD.getValueAt(i, 1);
				dvd.adicionaArtista(artista, papel);
			}
			catalogo.adicionaMidia(dvd);
			salvar();
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(contentPanel, "Formato incorreto no campo 'Ano'", "Valor incorreto",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void insereJogo()
	{
		String titulo = tituloJogo.getText();
		Integer anoCriacao;
		String genero = generoJogo.getText();

		try
		{
			anoCriacao = Integer.parseInt(anoJogo.getText());
			if (anoJogo.getText().length() != 4) throw new NumberFormatException();
			Jogo jogo = new Jogo(titulo, anoCriacao, genero);

			catalogo.adicionaMidia(jogo);
			salvar();
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(contentPanel, "Formato incorreto no campo 'Ano'", "Valor incorreto",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void salvar()
	{

		try
		{
			dispose();
			SerializadorCatalogo.gravaCatalogo(catalogo, catalogo.getArquivo());
			JOptionPane.showMessageDialog(contentPanel, "Mídia inserida com sucesso.", "Inserir Mídia",
					JOptionPane.INFORMATION_MESSAGE);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(contentPanel, "Não foi possível salvar a inserção da mídia.",
					"Inserir Mídia", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limparCampos()
	{
		tituloCD.setText("");
		anoCD.setText("");
		artistaCD.setText("");;
		tituloDVD.setText("");;
		anoDVD.setText("");;
		diretorDVD.setText("");;
		tituloJogo.setText("");;
		anoJogo.setText("");;
		generoJogo.setText("");;
		recriarTabelaFaixasCD();
		recriarTabelaArtistasDVD();
	}

	private void recriarTabelaFaixasCD()
	{
		Object[][] faixas = new Object[CD.MAX_FAIXAS][3];

		for (int i = 0; i < CD.MAX_FAIXAS; i++)
		{
			faixas[i][0] = i + 1;
			faixas[i][1] = null;
			faixas[i][2] = null;
		}

		tabelaFaixasCD.setModel(new DefaultTableModel(faixas, new String[] { "No.", "Faixa",
				"Dura\u00E7\u00E3o (mm:ss)" })
		{
			Class[] columnTypes = new Class[] { Integer.class, String.class, DuracaoFaixa.class };

			boolean[] columnEditables = new boolean[] { false, true, true };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		});
		tabelaFaixasCD.getColumnModel().getColumn(0).setPreferredWidth(28);
		tabelaFaixasCD.getColumnModel().getColumn(0).setMinWidth(25);
		tabelaFaixasCD.getColumnModel().getColumn(0).setMaxWidth(33);
		tabelaFaixasCD.getColumnModel().getColumn(1).setPreferredWidth(215);
		tabelaFaixasCD.getColumnModel().getColumn(1).setMinWidth(115);
		tabelaFaixasCD.getColumnModel().getColumn(2).setPreferredWidth(120);
		tabelaFaixasCD.getColumnModel().getColumn(2).setMinWidth(120);
		tabelaFaixasCD.getColumnModel().getColumn(2).setMaxWidth(140);

	}

	private void recriarTabelaArtistasDVD()
	{
		Object[][] artistas = new Object[DVD.MAX_ARTISTAS][2];

		for (int i = 0; i < DVD.MAX_ARTISTAS; i++)
		{
			artistas[i][0] = null;
			artistas[i][1] = null;
		}
		tabelaArtistasDVD.setModel(new DefaultTableModel(artistas, new String[] { "Nome", "Papel" })
		{
			Class[] columnTypes = new Class[] { String.class, String.class };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}
		});
		tabelaArtistasDVD.getColumnModel().getColumn(0).setPreferredWidth(255);
		tabelaArtistasDVD.getColumnModel().getColumn(1).setPreferredWidth(175);
	}
}