package fr.epsi.myEpsi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import fr.epsi.myEpsi.beans.Message;
import fr.epsi.myEpsi.beans.Status;
import fr.epsi.myEpsi.beans.User;
import fr.epsi.myEpsi.service.UserService;

public class MessageDao implements IMessageDao {

	private static final Logger LOGGER = LogManager.getLogger(MessageDao.class.getName());
	
	UserService userService = new UserService();
	
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

	@Override
	public List<Message> getListOfMessages(User user) {
		Connection con = getConnection();
		List<Message> messages = new ArrayList<>();
		ResultSet result;
		try {
			PreparedStatement ps;
			ps = con.prepareStatement("SELECT * FROM MESSAGES");
			if(!user.getAdministrator()){
				ps = con.prepareStatement("SELECT * FROM MESSAGES WHERE USER_ID = ? OR STATUS = ? AND STATUS != ?");
				ps.setString(1, user.getId());
				ps.setInt(2, Status.PUBLIC.ordinal());
				ps.setInt(3, Status.ARCHIVED.ordinal());
			}	
			result = ps.executeQuery();
			    while (result.next()) {
			    	Message message = new Message();
			    	message.setId(result.getLong(1));
			    	message.setTitle(result.getString(2));
			    	message.setContent(result.getString(3));
			    	message.setAuthor(userService.getUserById(result.getString(4)));
			    	message.setCreationDate(result.getTimestamp(5));
			    	message.setUpdateDate(result.getTimestamp(6));
			    	message.setStatus(Status.fromOrdinal(result.getInt(7)));
			    	messages.add(message);
			     }	   

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return messages;
	}

	@Override
	public Message getMessage(Long id) {
		Connection con = getConnection();
		ResultSet result;
		Message message = null;
		User user = new User();
		try {
			PreparedStatement stmt = con.prepareStatement("select * from messages where ID = ?");
			stmt.setLong(1, id);
			LOGGER.debug("select message " + id);
			result = stmt.executeQuery();
			result.next();
			String id_user = result.getString(4);
			IUserDao userDao = new UserDao();
			user = userDao.getUserById(id_user);
			message = new Message();
			message.setId(result.getLong(1));
			message.setTitle(result.getString(2));
			message.setContent(result.getString(3));
			message.setAuthor(userService.getUserById(result.getString(4)));
			message.setCreationDate(result.getTimestamp(5));
			message.setUpdateDate(result.getTimestamp(6));
			message.setStatus(Status.fromOrdinal(result.getInt(7)));
		} catch (Exception e) {

			LOGGER.error("Exception", e);
		}
		return message;
	}

	@Override
	public void addMessage(Message message) {
		Connection con = getConnection();
		long id = 0;
		try{
			ResultSet resultats = null;
			String requete = "SELECT MAX(ID) FROM MESSAGES";
			Statement stmt = con.createStatement();
			resultats = stmt.executeQuery(requete);
			while(resultats.next()){
				id = resultats.getLong(1) + 1;
			}
		}
		catch(Exception e){
			LOGGER.error("Exception", e);
		}
		try {

			PreparedStatement ps = con.prepareStatement("insert into messages values(?, ?, ?, ?, ?, ?, ?)");
			ps.setLong(1, id);
			ps.setString(2, message.getTitle());
			ps.setString(3, message.getContent());
			ps.setObject(4, message.getAuthor().getId());
			ps.setTimestamp(5, message.getCreationDate());
			ps.setTimestamp(6, message.getUpdateDate());
			ps.setInt(7, message.getStatus().ordinal());
			LOGGER.debug("insert message " + message.getId());
			ps.executeUpdate();
			con.close();

		} catch (Exception e) {
			LOGGER.error("Exception", e);
		}

	}

	@Override
	public void updateMessageStatus(Message message, int status) {
		Connection con = getConnection();

		try {
			PreparedStatement ps = con.prepareStatement("UPDATE messages set STATUS = ? WHERE id = ?");
			ps.setInt(1, status);
			ps.setLong(2, message.getId());

			LOGGER.debug("Update du message " + message.getId());
			ps.executeUpdate();
			con.close();

		} catch (Exception e) {
			LOGGER.error("Exception", e);
		}
	}

	@Override
	public void deleteMessage(Message message) {
		Connection con = getConnection();

		try {
			PreparedStatement ps = con.prepareStatement("delete from messages where id = '"
					+ message.getId() + "'");
			ps.executeUpdate();
			LOGGER.debug("Message " + message.getId() + " supprimé");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Message> getListOfMessages() {
		Connection con = getConnection();
		ResultSet resultats = null;

		ArrayList messages = new ArrayList<Message>();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM MESSAGES");
			resultats = ps.executeQuery();
			    while (resultats.next()) {
			    	Message message = new Message();
			    	message.setId(resultats.getLong(1));
			    	message.setTitle(resultats.getString(2));
			    	message.setContent(resultats.getString(3));
			    	message.setAuthor(userService.getUserById(resultats.getString(4)));
			    	message.setCreationDate(resultats.getTimestamp(5));
			    	message.setUpdateDate(resultats.getTimestamp(6));
			    	message.setStatus(Status.fromOrdinal(resultats.getInt(7)));
			    	messages.add(message);
			     }	   

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return messages;
	}

}
