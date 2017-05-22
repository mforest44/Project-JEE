package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epsi.myEpsi.beans.User;
import fr.epsi.myEpsi.service.IUserService;
import fr.epsi.myEpsi.service.UserService;

/**
 * Servlet implementation class Users
 */

@WebServlet("/Users")
public class Users extends HttpServlet {
	
	IUserService userService = new UserService();
	Logger LOGGER =  LogManager.getLogger(Users.class.getName());
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("GET /Users");
	    LOGGER.debug(request);
		User connected = (User) request.getSession().getAttribute("user");
		if(connected == null){
			  response.sendRedirect("Connection");
		} else if(!connected.getAdministrator()){
			  response.sendRedirect("Messages");
		} else {
			List<User> users = userService.getListOfUsers();
			request.setAttribute("user", users);
			request.getRequestDispatcher("users.jsp").forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("POST /Users");
		LOGGER.debug(request);
		User connected = (User) request.getSession().getAttribute("user");
		if(connected == null){
			  response.sendRedirect("Connection");
		} else if(request.getParameter("action") != null && request.getParameter("action").equals("delete")) {
			this.doDelete(request, response);
		} else if(request.getParameter("action") != null && request.getParameter("action").equals("update")) {
			this.doPut(request, response);
		} else if(request.getParameter("password").equals(request.getParameter("repassword"))){   
			User user = new User();
			user.setId(request.getParameter("login"));
			user.setPassword(request.getParameter("password"));
			user.setAdministrator(Boolean.parseBoolean(request.getParameter("admin")));
			userService.addUser(user);
			response.sendRedirect("Users");
		} else {
			response.sendRedirect("Users");
		}
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("DELETE /USERS");
		LOGGER.debug(request);
		String id = request.getParameter("login");
		User connected = (User) request.getSession().getAttribute("user");
		if(connected == null){
			  response.sendRedirect("Connection");
		} else {
			User user = userService.getUserById(id);
			userService.deleteUser(user);
			response.sendRedirect("Users");
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("PUT /USERS");
		LOGGER.debug(request);
		String id = request.getParameter("login");
		User connected = (User) request.getSession().getAttribute("user");
	 if(request.getParameter("password").equals(request.getParameter("repassword"))) {
			User user = new User();
			user.setId(id);
			user.setPassword(request.getParameter("password"));
			System.out.println(request.getParameter("admin"));
			if(null == request.getParameter("admin") || request.getParameter("admin").equals("null")){
				user.setAdministrator(false);
			}
			else{
				user.setAdministrator(true);
			}
			userService.updateUser(user);
			response.sendRedirect("Users");
		} else {
			response.sendRedirect("Users");
		}
	}

}