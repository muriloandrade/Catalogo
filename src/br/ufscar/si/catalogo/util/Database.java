package br.ufscar.si.catalogo.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import br.ufscar.si.catalogo.dao.impl.ConnectionFactory;

public class Database
{
	public static String DB_NAME = "CATALOGO_APP";
	private static Connection conn = null;

	public static void main(String[] args)
	{
		System.out.println("Criando banco de dados " + DB_NAME);

		conn = Database.getConnection();
		
		Statement stmt = null;

		InputStream stream = Database.class.getResourceAsStream("criaDatabase.sql");
		Scanner scanner = new Scanner(stream);
		scanner.useDelimiter("\n\r");

		while (scanner.hasNext())
		{
			try
			{
				String q = scanner.next();
				stmt = conn.createStatement();
				if (q.length() > 0)
				{
					System.out.println(q);
					stmt.execute(q);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			if (conn != null) conn.close();
			if (stream != null) stream.close();
			if (scanner != null) scanner.close();
			if (stmt != null) stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private static Connection getConnection()
	{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";
		String password = "root";

		try
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
}
