package br.ufscar.si.catalogo.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import br.ufscar.si.catalogo.modelo.Catalogo;

/*
 * Janela JDialog que permite localizar uma m�dia existente no cat�logo pelo T�tulo ou pelo Ano
 */
public class Localizar extends JDialog
{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private static Localizar localizar;

	private String value;

	public Localizar(Frame owner, boolean modal, Catalogo catalogo)
	{
		super(owner, modal);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		localizar = this;
		setResizable(false);
		setTitle("Localizar m\u00EDdia");
		setBounds(owner.getBounds().x + 120, owner.getBounds().y + 160, 480, 160);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblModo = new JLabel("Modo:");

		JLabel lblTermo = new JLabel("Termo:");

		final JRadioButton rdbtnPorTtulo = new JRadioButton("por T\u00EDtulo");
		buttonGroup.add(rdbtnPorTtulo);
		rdbtnPorTtulo.setSelected(true);

		final JRadioButton rdbtnPorAno = new JRadioButton("por Ano");
		buttonGroup.add(rdbtnPorAno);

		textField = new JTextField();
		textField.setColumns(10);

		JLabel lblTipo = new JLabel("Tipo:");

		JPanel panel = new JPanel();

		JPanel panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(0, 0));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(
						gl_contentPanel.createParallelGroup(Alignment.TRAILING).addComponent(lblTermo).addComponent(
								lblModo).addComponent(lblTipo)).addGap(10).addGroup(
						gl_contentPanel.createParallelGroup(Alignment.LEADING, false).addGroup(
								gl_contentPanel.createSequentialGroup().addComponent(rdbtnPorTtulo).addGap(30)
										.addComponent(rdbtnPorAno)).addComponent(textField, GroupLayout.DEFAULT_SIZE,
								250, Short.MAX_VALUE).addComponent(panel, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE).addContainerGap()));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPanel.createSequentialGroup().addGap(15).addGroup(
						gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(panel_1,
								GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
								.addGroup(
										gl_contentPanel.createSequentialGroup().addGroup(
												gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(
														lblModo).addComponent(rdbtnPorTtulo).addComponent(rdbtnPorAno))
												.addGap(10).addGroup(
														gl_contentPanel.createParallelGroup(Alignment.BASELINE)
																.addComponent(lblTermo).addComponent(textField,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)).addGap(10)
												.addGroup(
														gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
																.addComponent(panel, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE).addGroup(
																		gl_contentPanel.createSequentialGroup()
																				.addPreferredGap(
																						ComponentPlacement.RELATED,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE).addComponent(
																						lblTipo))).addGap(2))).addGap(
						13)));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWeights = new double[] { 1.0 };
		gbl_panel_1.rowWeights = new double[] { 1.0, 1.0 };
		panel_1.setLayout(gbl_panel_1);

		JButton btnPesquisar = new JButton("Localizar");
		btnPesquisar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				value = textField.getText();

				if (rdbtnPorAno.isSelected())
				{
					try
					{
						Integer.parseInt(value);
						if (value.length() != 4)
						{
							JOptionPane.showMessageDialog(localizar, "Digitar n�mero de 4 digitos.", "Valor incorreto",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						localizar.setVisible(false);
					}
					catch (Exception e2)
					{
						JOptionPane.showMessageDialog(localizar, "Digitar n�mero de 4 digitos.", "Valor incorreto",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				else
				{
					localizar.setVisible(false);
				}

			}
		});
		GridBagConstraints gbc_btnPesquisar = new GridBagConstraints();
		gbc_btnPesquisar.fill = GridBagConstraints.BOTH;
		gbc_btnPesquisar.insets = new Insets(10, 10, 5, 10);
		gbc_btnPesquisar.gridx = 0;
		gbc_btnPesquisar.gridy = 0;
		panel_1.add(btnPesquisar, gbc_btnPesquisar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				value = null;
				localizar.setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(5, 10, 10, 10);
		gbc_btnCancelar.fill = GridBagConstraints.BOTH;
		gbc_btnCancelar.gridx = 0;
		gbc_btnCancelar.gridy = 1;
		panel_1.add(btnCancelar, gbc_btnCancelar);
		panel.setLayout(new GridLayout(1, 3, 0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(0, 20));
		panel_2.setMinimumSize(new Dimension(0, 0));
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(0);
		flowLayout.setHgap(10);
		panel.add(panel_2);

		JCheckBox chckbxCd = new JCheckBox("CD");
		chckbxCd.setSelected(true);
		chckbxCd.setMinimumSize(new Dimension(0, 0));
		panel_2.add(chckbxCd);

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(0, 20));
		panel_3.setMinimumSize(new Dimension(0, 0));
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		panel.add(panel_3);

		JCheckBox chckbxDvd = new JCheckBox("DVD");
		chckbxDvd.setSelected(true);
		panel_3.add(chckbxDvd);

		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(0, 20));
		panel_4.setMinimumSize(new Dimension(0, 0));
		FlowLayout flowLayout_2 = (FlowLayout) panel_4.getLayout();
		flowLayout_2.setAlignment(FlowLayout.RIGHT);
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(10);
		panel.add(panel_4);

		JCheckBox chckbxJogo = new JCheckBox("Jogo");
		chckbxJogo.setSelected(true);
		panel_4.add(chckbxJogo);
		contentPanel.setLayout(gl_contentPanel);
	}

	public String getValue()
	{
		return value;
	}

}
