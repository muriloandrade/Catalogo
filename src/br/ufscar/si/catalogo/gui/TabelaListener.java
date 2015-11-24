package br.ufscar.si.catalogo.gui;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TabelaListener implements ListSelectionListener
{
	JTable tabela;
	Principal frame;

	public TabelaListener(Principal frame)
	{
		this.tabela = frame.getTabela();
		this.frame = frame;
	}

	public void valueChanged(ListSelectionEvent event)
	{
		if (event.getValueIsAdjusting()) { return; }
		if (tabela.getSelectedRowCount() > 0) frame.botoesVER_SetEnable(true);
	}

}
