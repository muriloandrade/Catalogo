package br.ufscar.si.catalogo.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.ufscar.si.catalogo.dao.impl.CDJDBCDAO;
import br.ufscar.si.catalogo.dao.impl.DAOException;
import br.ufscar.si.catalogo.dao.impl.DVDJDBCDAO;
import br.ufscar.si.catalogo.dao.impl.GenericJDBCDAO;
import br.ufscar.si.catalogo.dao.impl.JogoJDBCDAO;
import br.ufscar.si.catalogo.modelo.ArtistaDVD;
import br.ufscar.si.catalogo.modelo.CD;
import br.ufscar.si.catalogo.modelo.Catalogo;
import br.ufscar.si.catalogo.modelo.DVD;
import br.ufscar.si.catalogo.modelo.DuracaoFaixa;
import br.ufscar.si.catalogo.modelo.FaixaCD;
import br.ufscar.si.catalogo.modelo.Jogo;
import br.ufscar.si.catalogo.modelo.Midia;

/*
 * Janela JDialog que permite editar uma mídia existente no catálogo
 */
public class EditarMidia extends JDialog
{
	private final JPanel cards = new JPanel();

	private JDialog dialogEditar;

	Catalogo catalogo;
	Midia midia;

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

	public EditarMidia(JFrame owner, boolean modal, Catalogo catalogo, final Midia midia)
	{
		super(owner, modal);
		this.dialogEditar = this;
		this.catalogo = catalogo;
		this.midia = midia;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Editar M\u00EDdia");
		setBounds(owner.getBounds().x + 120, owner.getBounds().y + 160, 494, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		cards.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(cards, BorderLayout.CENTER);
		cards.setLayout(new CardLayout(0, 0));

		JPanel card_cd = new JPanel();
		card_cd.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Editar CD",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		scrollPaneCD.setViewportView(tabelaFaixasCD);
		card_cd.setLayout(gl_card_cd);

		JPanel card_dvd = new JPanel();
		card_dvd.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Editar DVD",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		scrollPaneDVD.setViewportView(tabelaArtistasDVD);
		card_dvd.setLayout(gl_card_dvd);

		JPanel card_Jogo = new JPanel();
		card_Jogo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Editar Jogo",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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

		switch (midia.getTipo())
		{
			case CD:
				carregaCD();
				break;
			case DVD:
				carregaDVD();
				break;
			case Jogo:
				carregaJogo();
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						switch (midia.getTipo())
						{
							case CD:
								substituiCD();
								break;
							case DVD:
								substituiDVD();
								break;
							case Jogo:
								substituiJogo();
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

	private void carregaCD()
	{
		((CardLayout) cards.getLayout()).show(cards, "cardCD");

		CD cd = (CD) midia;

		tituloCD.setText(cd.getTitulo());
		anoCD.setText(String.valueOf(cd.getAnoCriacao()));
		artistaCD.setText(cd.getArtista());

		tabelaFaixasCD.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "No.", "Faixa",
				"Dura\u00E7\u00E3o (seg)" })
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
		tabelaFaixasCD.getColumnModel().getColumn(2).setResizable(false);
		tabelaFaixasCD.getColumnModel().getColumn(2).setPreferredWidth(80);
		tabelaFaixasCD.getColumnModel().getColumn(2).setMinWidth(80);
		tabelaFaixasCD.getColumnModel().getColumn(2).setMaxWidth(100);

		FaixaCD[] faixas = cd.getFaixas();

		for (int i = 0; i < faixas.length; i++)
		{
			Object[] valores = { faixas[i].getNumero(), faixas[i].getNome(), faixas[i].getDuracao() };
			((DefaultTableModel) tabelaFaixasCD.getModel()).addRow(valores);
		}

	}

	private void carregaDVD()
	{
		((CardLayout) cards.getLayout()).show(cards, "cardDVD");

		DVD dvd = (DVD) midia;

		tituloDVD.setText(dvd.getTitulo());
		anoDVD.setText(String.valueOf(dvd.getAnoCriacao()));
		diretorDVD.setText(dvd.getDiretor());

		tabelaArtistasDVD.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nome", "Papel" })
		{
			Class[] columnTypes = new Class[] { String.class, String.class };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}
		});
		tabelaArtistasDVD.getColumnModel().getColumn(0).setPreferredWidth(255);
		tabelaArtistasDVD.getColumnModel().getColumn(1).setPreferredWidth(175);

		ArtistaDVD[] artistas = dvd.getArtistas();

		for (int i = 0; i < artistas.length; i++)
		{
			Object[] valores = { artistas[i].getNome(), artistas[i].getPapel() };
			((DefaultTableModel) tabelaArtistasDVD.getModel()).addRow(valores);
		}
	}

	private void carregaJogo()
	{
		((CardLayout) cards.getLayout()).show(cards, "cardJogo");

		Jogo jogo = (Jogo) midia;

		tituloJogo.setText(jogo.getTitulo());
		anoJogo.setText(String.valueOf(jogo.getAnoCriacao()));
		generoJogo.setText(jogo.getGenero());
	}

	private void substituiCD()
	{
		String titulo = tituloCD.getText();
		Integer anoCriacao;
		String artista = artistaCD.getText();

		try
		{
			anoCriacao = Integer.parseInt(anoCD.getText());
			if (anoCD.getText().length() != 4) throw new NumberFormatException();
			CD cd = new CD(titulo, anoCriacao, artista);
			cd.setId(midia.getId());

			FaixaCD faixa;
			String nome;
			DuracaoFaixa duracao;

			for (int i = 0; i < tabelaFaixasCD.getRowCount(); i++)
			{
				nome = (String) tabelaFaixasCD.getValueAt(i, 1);
				duracao = (DuracaoFaixa) tabelaFaixasCD.getValueAt(i, 2);
				if (nome != null && !nome.isEmpty())
				{
					faixa = new FaixaCD(i + 1, nome, duracao);
					cd.setFaixa(i, faixa);
				}
			}
			catalogo.removeMidia(midia);
			catalogo.adicionaMidia(cd);
			salvar(cd);
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(dialogEditar, "Formato incorreto no campo 'Ano'", "Valor incorreto",
					JOptionPane.ERROR_MESSAGE);
		}
		catch (ParseException e)
		{
			JOptionPane.showMessageDialog(dialogEditar, "Formato incorreto no campo 'Duracao'", "Valor incorreto",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void substituiDVD()
	{
		String titulo = tituloDVD.getText();
		Integer anoCriacao;
		String diretor = diretorDVD.getText();

		try
		{
			anoCriacao = Integer.parseInt(anoDVD.getText());
			if (anoDVD.getText().length() != 4) throw new NumberFormatException();
			DVD dvd = new DVD(titulo, anoCriacao, diretor);
			dvd.setId(midia.getId());

			ArtistaDVD artista;
			String nome;
			String papel;
			int count = 0;

			for (int i = 0; i < tabelaArtistasDVD.getRowCount(); i++)
			{
				nome = (String) tabelaArtistasDVD.getValueAt(i, 0);
				papel = (String) tabelaArtistasDVD.getValueAt(i, 1);
				if ((nome != null && !nome.isEmpty()) || (papel != null && !papel.isEmpty()))
				{
					artista = new ArtistaDVD(nome, papel);
					dvd.setArtista(count++, artista);
				}
			}
			catalogo.removeMidia(midia);
			catalogo.adicionaMidia(dvd);
			salvar(dvd);
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(dialogEditar, "Formato incorreto no campo 'Ano'", "Valor incorreto",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void substituiJogo()
	{
		String titulo = tituloJogo.getText();
		Integer anoCriacao;
		String genero = generoJogo.getText();

		try
		{
			anoCriacao = Integer.parseInt(anoJogo.getText());
			if (anoJogo.getText().length() != 4) throw new NumberFormatException();
			Jogo jogo = new Jogo(titulo, anoCriacao, genero);
			jogo.setId(midia.getId());
			catalogo.removeMidia(midia);
			catalogo.adicionaMidia(jogo);
			salvar(jogo);
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(dialogEditar, "Formato incorreto no campo 'Ano'", "Valor incorreto",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void salvar(Midia midiaAtualizada)
	{
		dispose();
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
			genericDAO.update(midiaAtualizada);
		}
		catch (DAOException e)
		{
			JOptionPane.showMessageDialog(dialogEditar, "Erro de operação ao acessar banco de dados.",
					"Excluir catálogo", JOptionPane.ERROR_MESSAGE);
		}

		JOptionPane.showMessageDialog(dialogEditar, "Mídia alterada com sucesso.", "Editar Mídia",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
