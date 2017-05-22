package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epsi.myEpsi.beans.User;
import fr.epsi.myEpsi.service.ConnectionService;
import fr.epsi.myEpsi.service.UserService;

/**
 * Servlet implementation class Messages
 */
@WebServlet("/Connection")
public class Connection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger LOGGER = LogManager.getLogger(Connection.class.getName());

	private UserService userService = new UserService();
	private ConnectionService connectionService = new ConnectionService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("GET /Connection");
		LOGGER.debug(request);
		request.getRequestDispatcher("accueil.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("POST /Connection");
		LOGGER.debug(request);
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = new User(username, password, false);
		System.out.println(connectionService.isAuthorized(user));
		if (user.getPassword() != null && user.getId() != null
				&& connectionService.isAuthorized(user)) {
			System.out.println(userService.getUserById(username).getId());
			request.getSession().setAttribute("user",
					userService.getUserById(username));
			response.sendRedirect("Messages");
		} else {
			LOGGER.error("Connection error", username, password, request);
			request.getRequestDispatcher("accueil.jsp").forward(request,
					response);
		}
	}

}