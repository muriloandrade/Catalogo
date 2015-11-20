package br.ufscar.si.catalogo.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.ufscar.si.catalogo.modelo.Catalogo;
import br.ufscar.si.catalogo.modelo.SerializadorCatalogo;

/*
 * Janela JDialog chamada pela janela AbrirCatálogo.
 * Permite criar um novo catálogo, indicando a capacidade máxima de mídias
 */
public class NovoCatalogo extends JDialog
{
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	NovoCatalogo dialogoNovoCatalogo;

	Catalogo catalogo;
	File arquivo = null;

	public NovoCatalogo(final JFrame owner, boolean modal)
	{
		super(owner, modal);
		dialogoNovoCatalogo = this;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Novo Cat\u00E1logo");
		setBounds(100, 100, 503, 180);
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
		gbc_contentPanel.anchor = GridBagConstraints.NORTH;
		gbc_contentPanel.insets = new Insets(10, 10, 5, 10);
		gbc_contentPanel.gridx = 0;
		gbc_contentPanel.gridy = 0;
		getContentPane().add(contentPanel, gbc_contentPanel);

		JLabel lblLocal = new JLabel("Local:");

		JLabel lblQuantidadeMximaDe = new JLabel("Capacidade:");

		textField = new JTextField();
		textField.setColumns(10);

		final JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(150, 1, 300, 1));

		JLabel lblMdias = new JLabel("m\u00EDdias");

		JButton btnProcurar = new JButton("Procurar...");
		btnProcurar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter ext = new FileNameExtensionFilter("Arquivo de Catálogo", "dat");
				fc.setFileFilter(ext);
				fc.setCurrentDirectory(null);
				fc.setDialogType(JFileChooser.SAVE_DIALOG);
				int returnVal = fc.showOpenDialog(owner);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					String nomeArquivo = fc.getSelectedFile().getAbsolutePath();
					nomeArquivo = AbrirCatalogo.converteParaDAT(nomeArquivo);
					textField.setText(nomeArquivo);
				}
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(
						gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(
								gl_contentPanel.createSequentialGroup().addComponent(lblLocal).addPreferredGap(
										ComponentPlacement.UNRELATED).addComponent(textField,
										GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE).addPreferredGap(
										ComponentPlacement.RELATED).addComponent(btnProcurar, GroupLayout.DEFAULT_SIZE,
										113, Short.MAX_VALUE)).addGroup(
								gl_contentPanel.createSequentialGroup().addComponent(lblQuantidadeMximaDe)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinner,
												GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblMdias)))
						.addContainerGap()));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(
						gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblLocal).addComponent(
								textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addComponent(btnProcurar)).addGap(18).addGroup(
						gl_contentPanel.createParallelGroup(Alignment.BASELINE, false).addComponent(
								lblQuantidadeMximaDe).addComponent(spinner, GroupLayout.PREFERRED_SIZE, 23,
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
					public void actionPerformed(ActionEvent e)
					{
						String nomeArquivo = textField.getText();
						if (!nomeArquivo.isEmpty()
								&& !nomeArquivo.substring(nomeArquivo.length() - 1).equals(File.separator))
						{
							nomeArquivo = AbrirCatalogo.converteParaDAT(nomeArquivo);
							arquivo = new File(nomeArquivo);
							if (arquivo.exists())
							{
								JOptionPane.showMessageDialog(owner,
										"O arquivo escolhido já existe.\nEscolha a opção 'Abrir Catálogo'",
										"Arquivo existente", JOptionPane.ERROR_MESSAGE);
								arquivo = null;
								dispose();
							}
							else
							{
								catalogo = new Catalogo((Integer) spinner.getValue());
								catalogo.setArquivo(arquivo);
								try
								{
									SerializadorCatalogo.gravaCatalogo(catalogo, arquivo);
								}
								catch (IOException e1)
								{
									JOptionPane.showMessageDialog(owner, "Não foi possível gravar no arquivo",
											"Erro ao gravar arquivo", JOptionPane.ERROR_MESSAGE);
								}
								dispose();
							}
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
