package br.ufscar.si.catalogo.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.ufscar.si.catalogo.modelo.ArtistaDVD;
import br.ufscar.si.catalogo.modelo.CD;
import br.ufscar.si.catalogo.modelo.DVD;
import br.ufscar.si.catalogo.modelo.FaixaCD;
import br.ufscar.si.catalogo.modelo.Jogo;
import br.ufscar.si.catalogo.modelo.Midia;

/*
 * Janela JDialog que permite visualizar uma mídia
 */
public class VisualizarMidia extends JDialog
{

	private final JPanel cards = new JPanel();

	private Midia midia;

	private JLabel tituloCD;
	private JLabel anoCD;
	private JLabel artistaCD;
	private JLabel tituloDVD;
	private JLabel anoDVD;
	private JLabel diretorDVD;
	private JLabel tituloJogo;
	private JLabel anoJogo;
	private JLabel generoJogo;
	private JTable tabelaFaixasCD;
	private JTable tabelaArtistasDVD;

	public VisualizarMidia(JFrame owner, boolean modal, Midia midia)
	{
		super(owner, modal);
		this.midia = midia;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Visualizar M\u00EDdia");
		setBounds(owner.getBounds().x + 120, owner.getBounds().y + 160, 415, 455);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		cards.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(cards, BorderLayout.CENTER);
		cards.setLayout(new CardLayout(0, 0));

		JPanel card_cd = new JPanel();
		card_cd.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "CD", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		cards.add(card_cd, "cardCD");

		JLabel lblTituloCD = new JLabel("T\u00EDtulo:");

		tituloCD = new JLabel();
		tituloCD.setText("Teste teste");

		JLabel lblAnoCD = new JLabel("Ano:");

		anoCD = new JLabel();
		anoCD.setText("Teste teste");

		JLabel lblArtista = new JLabel("Artista:");

		artistaCD = new JLabel();
		artistaCD.setText("Teste teste");

		JLabel lblFaixas = new JLabel("Faixas");

		JScrollPane scrollPaneCD = new JScrollPane();
		GroupLayout gl_card_cd = new GroupLayout(card_cd);
		gl_card_cd
				.setHorizontalGroup(gl_card_cd.createParallelGroup(Alignment.LEADING).addGroup(
						gl_card_cd.createSequentialGroup().addGap(5).addGroup(
								gl_card_cd.createParallelGroup(Alignment.LEADING).addGroup(
										gl_card_cd.createSequentialGroup().addGroup(
												gl_card_cd.createParallelGroup(Alignment.TRAILING).addComponent(
														lblAnoCD).addComponent(lblTituloCD).addGroup(
														gl_card_cd.createParallelGroup(Alignment.LEADING).addComponent(
																lblFaixas).addComponent(lblArtista))).addPreferredGap(
												ComponentPlacement.RELATED).addGroup(
												gl_card_cd.createParallelGroup(Alignment.TRAILING, false).addComponent(
														tituloCD, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 325,
														Short.MAX_VALUE).addComponent(artistaCD, Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE).addComponent(anoCD, Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED,
												94, Short.MAX_VALUE)).addComponent(scrollPaneCD, Alignment.TRAILING,
										GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)).addContainerGap()));
		gl_card_cd.setVerticalGroup(gl_card_cd.createParallelGroup(Alignment.LEADING).addGroup(
				gl_card_cd.createSequentialGroup().addContainerGap().addGroup(
						gl_card_cd.createParallelGroup(Alignment.BASELINE).addComponent(lblTituloCD).addComponent(
								tituloCD)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(
						gl_card_cd.createParallelGroup(Alignment.BASELINE).addComponent(lblAnoCD).addComponent(anoCD))
						.addPreferredGap(ComponentPlacement.UNRELATED).addGroup(
								gl_card_cd.createParallelGroup(Alignment.BASELINE).addComponent(lblArtista)
										.addComponent(artistaCD)).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblFaixas).addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollPaneCD,
								GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE).addGap(16)));

		tabelaFaixasCD = new JTable();
		tabelaFaixasCD.setFillsViewportHeight(true);
		tabelaFaixasCD.setEnabled(false);
		tabelaFaixasCD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaFaixasCD.getTableHeader().setReorderingAllowed(false);
		scrollPaneCD.setViewportView(tabelaFaixasCD);
		card_cd.setLayout(gl_card_cd);

		JPanel card_dvd = new JPanel();
		card_dvd.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "DVD", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		cards.add(card_dvd, "cardDVD");

		JLabel lblTituloDVD = new JLabel("T\u00EDtulo:");

		tituloDVD = new JLabel();
		tituloDVD.setText("Teste teste");

		JLabel lblAnoDVD = new JLabel("Ano:");

		anoDVD = new JLabel();
		anoDVD.setText("Teste teste");

		JLabel lblDiretor = new JLabel("Diretor:");

		diretorDVD = new JLabel();
		diretorDVD.setText("Teste teste");

		JLabel lblArtistas = new JLabel("Artistas");

		JScrollPane scrollPaneDVD = new JScrollPane();
		GroupLayout gl_card_dvd = new GroupLayout(card_dvd);
		gl_card_dvd.setHorizontalGroup(gl_card_dvd.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_card_dvd.createSequentialGroup().addGap(5).addGroup(
								gl_card_dvd.createParallelGroup(Alignment.LEADING).addComponent(lblArtistas).addGroup(
										gl_card_dvd.createSequentialGroup().addGroup(
												gl_card_dvd.createParallelGroup(Alignment.TRAILING).addComponent(
														lblAnoDVD).addComponent(lblTituloDVD).addComponent(lblDiretor))
												.addPreferredGap(ComponentPlacement.RELATED).addGroup(
														gl_card_dvd.createParallelGroup(Alignment.TRAILING, false)
																.addComponent(anoDVD, Alignment.LEADING).addComponent(
																		tituloDVD, Alignment.LEADING,
																		GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
																.addComponent(diretorDVD, Alignment.LEADING)))
										.addComponent(scrollPaneDVD)).addGap(0)));
		gl_card_dvd.setVerticalGroup(gl_card_dvd.createParallelGroup(Alignment.LEADING).addGroup(
				gl_card_dvd.createSequentialGroup().addContainerGap().addGroup(
						gl_card_dvd.createParallelGroup(Alignment.BASELINE).addComponent(lblTituloDVD).addComponent(
								tituloDVD)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(
						gl_card_dvd.createParallelGroup(Alignment.BASELINE).addComponent(lblAnoDVD)
								.addComponent(anoDVD)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(
						gl_card_dvd.createParallelGroup(Alignment.BASELINE).addComponent(lblDiretor).addComponent(
								diretorDVD)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblArtistas)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollPaneDVD,
								GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)));

		tabelaArtistasDVD = new JTable();
		tabelaArtistasDVD.setEnabled(false);
		tabelaArtistasDVD.setFillsViewportHeight(true);
		scrollPaneDVD.setViewportView(tabelaArtistasDVD);
		card_dvd.setLayout(gl_card_dvd);

		JPanel card_Jogo = new JPanel();
		card_Jogo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Jogo", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		cards.add(card_Jogo, "cardJogo");

		JLabel lblTituloJogo = new JLabel("T\u00EDtulo:");

		tituloJogo = new JLabel();
		tituloJogo.setText("Teste teste");

		JLabel lblAnoJogo = new JLabel("Ano:");

		anoJogo = new JLabel();
		anoJogo.setText("Teste teste");

		JLabel lblGenero = new JLabel("G\u00EAnero:");

		generoJogo = new JLabel();
		generoJogo.setText("Teste teste");

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
				carregarCD();
				break;
			case DVD:
				carregarDVD();
				break;
			case Jogo:
				carregarJogo();
				break;
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
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	private void carregarCD()
	{
		((CardLayout) cards.getLayout()).show(cards, "cardCD");

		CD cd = (CD) midia;

		tituloCD.setText(cd.getTitulo());
		anoCD.setText(String.valueOf(cd.getAnoCriacao()));
		artistaCD.setText(cd.getArtista());

		tabelaFaixasCD.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "No.", "Faixa",
				"Dura\u00E7\u00E3o (mm:ss)" })
		{
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}
		});
		tabelaFaixasCD.getColumnModel().getColumn(0).setPreferredWidth(28);
		tabelaFaixasCD.getColumnModel().getColumn(0).setMinWidth(25);
		tabelaFaixasCD.getColumnModel().getColumn(0).setMaxWidth(33);
		tabelaFaixasCD.getColumnModel().getColumn(1).setPreferredWidth(180);
		tabelaFaixasCD.getColumnModel().getColumn(1).setMinWidth(115);
		tabelaFaixasCD.getColumnModel().getColumn(2).setPreferredWidth(120);
		tabelaFaixasCD.getColumnModel().getColumn(2).setMinWidth(120);
		tabelaFaixasCD.getColumnModel().getColumn(2).setMaxWidth(130);

		FaixaCD[] faixas = cd.getFaixas();
	
		for (int i = 0; i < faixas.length; i++)
		{
			if (faixas[i].getFaixa() != null)
			{
				Object[] valores = { faixas[i].getNumero(), faixas[i].getFaixa(), faixas[i].getDuracao() };
				((DefaultTableModel) tabelaFaixasCD.getModel()).addRow(valores);
			}
		}
	}

	private void carregarDVD()
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
		tabelaArtistasDVD.getColumnModel().getColumn(0).setPreferredWidth(150);
		tabelaArtistasDVD.getColumnModel().getColumn(1).setPreferredWidth(150);

		ArtistaDVD[] artistas = dvd.getArtistas();

		for (int i = 0; i < artistas.length; i++)
		{
			Object[] valores = { artistas[i].getNome(), artistas[i].getPapel() };
			((DefaultTableModel) tabelaArtistasDVD.getModel()).addRow(valores);
		}
	}

	private void carregarJogo()
	{
		((CardLayout) cards.getLayout()).show(cards, "cardJogo");

		Jogo jogo = (Jogo) midia;

		tituloJogo.setText(jogo.getTitulo());
		anoJogo.setText(String.valueOf(jogo.getAnoCriacao()));
		generoJogo.setText(jogo.getGenero());
	}

}
