package br.ufscar.si.catalogo.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.ufscar.si.catalogo.modelo.Catalogo;
import br.ufscar.si.catalogo.modelo.SerializadorCatalogo;

/*
 * Janela JDialog para Criar/Abrir catálogo
 */
public class AbrirCatalogo extends JDialog
{

	private Catalogo catalogo = null;
	private AbrirCatalogo dialogoAbrirCatalogo;

	public AbrirCatalogo(final JFrame owner, boolean modal)
	{
		super(owner, modal);
		dialogoAbrirCatalogo = this;

		setTitle("Inicializar Cat\u00E1logo");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		// Botão para Criar novo catálogo
		JButton btnNovoCatlogo = new JButton("Novo Cat\u00E1logo...");
		btnNovoCatlogo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				NovoCatalogo novoCatalogo = new NovoCatalogo(owner, true);
				novoCatalogo.setVisible(true);
				File arquivoCatalogo = novoCatalogo.getArquivo();
				if (arquivoCatalogo != null)
				{
					try
					{
						catalogo = SerializadorCatalogo.carregaCatalogo(arquivoCatalogo);
						dispose();
					}
					catch (ClassNotFoundException e1)
					{
						JOptionPane.showMessageDialog(dialogoAbrirCatalogo, "Classe serializadora não encontrada.",
								"Classe não encontrada", JOptionPane.ERROR_MESSAGE);
					}
					catch (IOException e1)
					{
						JOptionPane.showMessageDialog(dialogoAbrirCatalogo, "Não foi possível abrir o arquivo.",
								"Erro ao abrir arquivo", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		GridBagConstraints gbc_btnNovoCatlogo = new GridBagConstraints();
		gbc_btnNovoCatlogo.fill = GridBagConstraints.BOTH;
		gbc_btnNovoCatlogo.insets = new Insets(20, 20, 20, 10);
		gbc_btnNovoCatlogo.gridx = 0;
		gbc_btnNovoCatlogo.gridy = 0;
		getContentPane().add(btnNovoCatlogo, gbc_btnNovoCatlogo);

		// Botão para Abrir catálogo existente
		JButton btnAbrirCatlogo = new JButton("Abrir Cat\u00E1logo...");
		btnAbrirCatlogo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter ext = new FileNameExtensionFilter("Arquivo de Catálogo", "dat");
				fc.setFileFilter(ext);
				fc.setCurrentDirectory(null);
				fc.setDialogType(JFileChooser.OPEN_DIALOG);
				int returnVal = fc.showOpenDialog(owner);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					String nomeArquivo = fc.getSelectedFile().getAbsolutePath();
					nomeArquivo = converteParaDAT(nomeArquivo);
					File arquivoCatalogo = new File(nomeArquivo);
					if (arquivoCatalogo.isFile())
					{
						try
						{
							catalogo = SerializadorCatalogo.carregaCatalogo(arquivoCatalogo);
							dispose();
						}
						catch (ClassNotFoundException e1)
						{
							JOptionPane.showMessageDialog(dialogoAbrirCatalogo, "Erro na abertura do arquivo.",
									"Classe não encontrada", JOptionPane.ERROR_MESSAGE);
						}
						catch (IOException e1)
						{
							JOptionPane.showMessageDialog(dialogoAbrirCatalogo, "Formato de arquivo inválido.",
									"Erro ao abrir arquivo", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		GridBagConstraints gbc_btnAbrirCatlogo = new GridBagConstraints();
		gbc_btnAbrirCatlogo.insets = new Insets(20, 10, 20, 20);
		gbc_btnAbrirCatlogo.fill = GridBagConstraints.BOTH;
		gbc_btnAbrirCatlogo.gridx = 1;
		gbc_btnAbrirCatlogo.gridy = 0;
		getContentPane().add(btnAbrirCatlogo, gbc_btnAbrirCatlogo);
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

}
