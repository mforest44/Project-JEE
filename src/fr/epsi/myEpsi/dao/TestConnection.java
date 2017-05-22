package fr.epsi.myEpsi.dao;

import java.sql.*;

public class TestConnection {
	private final static String DB_URL = "jdbc:hsqldb:hsql://localhost:9003";
	private final static String DB_USER = "SA";
	private final static String DB_PASSWORD = "";

	public static void main(String[] args) {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}

		Connection con;

		ResultSet resultats = null;
		String requete = "SELECT * FROM USERS";

		try {
			con = DriverManager.getConnection(DB_URL);
//			PreparedStatement ps = con.prepareStatement("insert into users values(1, 'test', TRUE)");
//			ps.executeUpdate();

			Statement stmt = con.createStatement();
			resultats = stmt.executeQuery(requete);
			ResultSetMetaData rsmd = resultats.getMetaData();
			int nbCols = rsmd.getColumnCount();

			while (resultats.next()) {
				for (int i = 1; i <= nbCols; i++) {
					System.out.print(resultats.getString(i) + " ");
				}
				System.out.println();
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}
}
