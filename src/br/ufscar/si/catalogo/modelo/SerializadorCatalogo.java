package br.ufscar.si.catalogo.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializadorCatalogo
{
	public static void gravaCatalogo(Catalogo catalogo, File arquivo) throws IOException
	{
		ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream(arquivo));
		saida.writeObject(catalogo);
		saida.close();
	}

	public static Catalogo carregaCatalogo(File arquivo) throws IOException, ClassNotFoundException
	{
		ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(arquivo));
		Catalogo catalogo = (Catalogo) entrada.readObject();
		entrada.close();

		return catalogo;
	}
}
