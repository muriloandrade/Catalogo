package br.ufscar.si.catalogo.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Sobre extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTable table;

	public Sobre()
	{
		setTitle("Sobre");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 370);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 424, 0 };
		gbl_contentPanel.rowHeights = new int[] { 43, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel label_01 = new JLabel("Cat\u00E1logo de M\u00EDdias");
			label_01.setFont(new Font("Tahoma", Font.BOLD, 13));
			label_01.setHorizontalAlignment(JLabel.CENTER);
			GridBagConstraints gbc_label_01 = new GridBagConstraints();
			gbc_label_01.insets = new Insets(0, 0, 5, 0);
			gbc_label_01.fill = GridBagConstraints.BOTH;
			gbc_label_01.gridx = 0;
			gbc_label_01.gridy = 0;
			contentPanel.add(label_01, gbc_label_01);
		}
		{
			JTextArea txtrAtividadeAvaliativaDa = new JTextArea();
			txtrAtividadeAvaliativaDa.setEditable(false);
			txtrAtividadeAvaliativaDa.setBackground(UIManager.getColor("Panel.background"));
			txtrAtividadeAvaliativaDa.setFont(new Font("Tahoma", Font.PLAIN, 13));
			txtrAtividadeAvaliativaDa.setLineWrap(true);
			txtrAtividadeAvaliativaDa
					.setText("Atividade: AA-4.1\r\nDisciplina: Programa\u00E7\u00E3o Orientada a Objetos 2 \r\nCurso: Sistemas de Informa\u00E7\u00E3o\r\nInstitui\u00E7\u00E3o: Universidade Federal de S\u00E3o Carlos");
			GridBagConstraints gbc_txtrAtividadeAvaliativaDa = new GridBagConstraints();
			gbc_txtrAtividadeAvaliativaDa.insets = new Insets(5, 10, 5, 10);
			gbc_txtrAtividadeAvaliativaDa.fill = GridBagConstraints.BOTH;
			gbc_txtrAtividadeAvaliativaDa.gridx = 0;
			gbc_txtrAtividadeAvaliativaDa.gridy = 1;
			contentPanel.add(txtrAtividadeAvaliativaDa, gbc_txtrAtividadeAvaliativaDa);
		}
		{
			JLabel lblAlunos = new JLabel("Alunos:");
			lblAlunos.setHorizontalAlignment(JLabel.LEFT);
			GridBagConstraints gbc_lblAlunos = new GridBagConstraints();
			gbc_lblAlunos.insets = new Insets(15, 10, 5, 10);
			gbc_lblAlunos.anchor = GridBagConstraints.WEST;
			gbc_lblAlunos.gridx = 0;
			gbc_lblAlunos.gridy = 2;
			contentPanel.add(lblAlunos, gbc_lblAlunos);
		}
		{
			{
				JScrollPane scrollPane = new JScrollPane();
				GridBagConstraints gbc_scrollPane = new GridBagConstraints();
				gbc_scrollPane.insets = new Insets(5, 5, 5, 5);
				gbc_scrollPane.fill = GridBagConstraints.BOTH;
				gbc_scrollPane.gridx = 0;
				gbc_scrollPane.gridy = 3;
				contentPanel.add(scrollPane, gbc_scrollPane);
				table = new JTable();
				table.setFillsViewportHeight(true);
				table.setRowHeight(20);
				scrollPane.setViewportView(table);
				table.setModel(new DefaultTableModel(new Object[][] {
						{ "Mauricio Donizeti de Bastos", new Integer(580147) },
						{ "Murilo Branquinho de Andrade Pintor", new Integer(580112) },
						{ "Rodrigo Luiz Volpi", new Integer(580279) }, }, new String[] { "Nome", "RA" })
				{
					Class[] columnTypes = new Class[] { String.class, Integer.class };

					public Class getColumnClass(int columnIndex)
					{
						return columnTypes[columnIndex];
					}

					boolean[] columnEditables = new boolean[] { false, false };

					public boolean isCellEditable(int row, int column)
					{
						return columnEditables[column];
					}
				});
				table.getColumnModel().getColumn(0).setPreferredWidth(250);
				table.getColumnModel().getColumn(0).setMinWidth(175);
				table.getColumnModel().getColumn(1).setPreferredWidth(100);
				table.getColumnModel().getColumn(1).setMinWidth(75);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
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

}
