package fr.epsi.myEpsi.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epsi.myEpsi.beans.User;

public class UserDao implements IUserDao {

	private static final Logger LOGGER = LogManager.getLogger(UserDao.class.getName());

	public Connection getConnection(){
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}

		try {
			return DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9003");
		}catch (SQLException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getListOfUsers() {
		Connection con = getConnection();
		ResultSet resultats = null;
		String requete = "SELECT * FROM USERS";
		ArrayList users = new ArrayList<User>();
		try {
			   Statement stmt = con.createStatement();
			   resultats = stmt.executeQuery(requete);
			    while (resultats.next()) {
			    	users.add(new User(resultats.getString(1), resultats.getString(2), resultats.getBoolean(3)));
			     }	   

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return users;
	}

	@Override
	public User getUserById(String id) {
		Connection con = getConnection();
		ResultSet result;
		User user = new User();
		try {
			PreparedStatement ps = con.prepareStatement("select * from USERS where ID = '" + id+"'");
			Statement stmt = con.createStatement();
			result = ps.executeQuery();
			result.next();
			user = new User(result.getString(1), result.getString(2),
					result.getBoolean(3));

		} catch (SQLException e) {

			LOGGER.debug("Id inexistante", e);
		}
		return user;
	}

	@Override
	public void addUser(User user) {
		Connection con = getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into users values(?, ?, ?)");
			ps.setString(1, user.getId());
			ps.setString(2, user.getPassword());
			ps.setBoolean(3, user.getAdministrator());
			ps.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Exception", e);
		}
	}

	@Override
	public void updateUser(User user) {
		Connection con = getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE users set PASSWORD = ?, ISADMINISTRATOR = ? WHERE id = ?");
			ps.setString(1, user.getPassword());
			ps.setBoolean(2, user.getAdministrator());
			ps.setString(3, user.getId());
			ps.executeUpdate();
			con.close();

		} catch (Exception e) {
			LOGGER.error("Exception", e);
		}
	}

	@Override
	public void deleteUser(User user) {
		Connection con = getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("delete from users where id = '" + user.getId()+"'");
			ps.executeUpdate();
			LOGGER.info("User " + user.getId() + " supprimé");
		} catch (SQLException e) {
			LOGGER.error("Exception", e);

		}

	}
}
