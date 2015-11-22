package br.ufscar.si.catalogo.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import br.ufscar.si.catalogo.dao.impl.CatalogoJDBCDAO;
import br.ufscar.si.catalogo.dao.impl.DAOException;
import br.ufscar.si.catalogo.modelo.Catalogo;

/*
 * Janela JDialog chamada pela janela AbrirCatálogo.
 * Permite criar um novo catálogo, indicando a capacidade máxima de mídias
 */
public class NovoCatalogo extends JDialog
{
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome;
	NovoCatalogo dialogNovoCatalogo;

	Catalogo catalogo;
	File arquivo = null;

	public NovoCatalogo(final JFrame owner, boolean modal)
	{
		super(owner, modal);
		dialogNovoCatalogo = this;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Novo Cat\u00E1logo");
		setBounds(100, 100, 370, 180);
		setLocationRelativeTo(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 469, 0 };
		gridBagLayout.rowHeights = new int[] { 118, 33, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints gbc_contentPanel = new GridBagConstraints();
		gbc_contentPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_contentPanel.insets = new Insets(10, 10, 5, 10);
		gbc_contentPanel.gridx = 0;
		gbc_contentPanel.gridy = 0;
		getContentPane().add(contentPanel, gbc_contentPanel);

		JLabel lblNome = new JLabel("Nome:");

		JLabel lblQuantidadeMximaDe = new JLabel("Capacidade:");

		txtNome = new JTextField();
		txtNome.setColumns(10);

		final JSpinner spnCapacidade = new JSpinner();
		spnCapacidade.setModel(new SpinnerNumberModel(150, 1, 300, 1));

		JLabel lblMdias = new JLabel("m\u00EDdias");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(
						gl_contentPanel.createParallelGroup(Alignment.TRAILING).addComponent(lblQuantidadeMximaDe)
								.addComponent(lblNome)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(
						gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(
								gl_contentPanel.createSequentialGroup().addComponent(spnCapacidade,
										GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE).addPreferredGap(
										ComponentPlacement.RELATED).addComponent(lblMdias).addContainerGap(152,
										Short.MAX_VALUE)).addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 254,
								Short.MAX_VALUE))));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(
						gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(txtNome,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNome)).addGap(18).addGroup(
						gl_contentPanel.createParallelGroup(Alignment.BASELINE, false).addComponent(
								lblQuantidadeMximaDe).addComponent(spnCapacidade, GroupLayout.PREFERRED_SIZE, 23,
								GroupLayout.PREFERRED_SIZE).addComponent(lblMdias)).addGap(11)));
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			GridBagConstraints gbc_buttonPane = new GridBagConstraints();
			gbc_buttonPane.insets = new Insets(5, 10, 30, 10);
			gbc_buttonPane.anchor = GridBagConstraints.NORTH;
			gbc_buttonPane.fill = GridBagConstraints.HORIZONTAL;
			gbc_buttonPane.gridx = 0;
			gbc_buttonPane.gridy = 1;
			getContentPane().add(buttonPane, gbc_buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						String nome = txtNome.getText();

						if (!nome.isEmpty())
						{
							Catalogo catalogo = new Catalogo(nome, (Integer) spnCapacidade.getValue());
							CatalogoJDBCDAO catalogoDAO = new CatalogoJDBCDAO();
							try
							{
								catalogoDAO.insert(catalogo);
							}
							catch (DAOException e)
							{
								JOptionPane.showMessageDialog(dialogNovoCatalogo, "Erro de operação ao acessar banco de dados.",
										"Excluir catálogo", JOptionPane.ERROR_MESSAGE);
							}
							dispose();
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

	public File getArquivo()
	{
		return arquivo;
	}
}
