package br.ufscar.si.catalogo.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import br.ufscar.si.catalogo.util.Database;

public class ConnectionFactory
{
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/" + Database.DB_NAME;
	private static String USER = "root";
	private static String PASSWORD = "root";

	public Connection getConnection()
	{
		Connection conn = null;
		try
		{
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			return conn;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
}
