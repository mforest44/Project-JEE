package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epsi.myEpsi.beans.Message;
import fr.epsi.myEpsi.beans.Status;
import fr.epsi.myEpsi.beans.User;
import fr.epsi.myEpsi.service.IMessageService;
import fr.epsi.myEpsi.service.MessageService;

/**
 * Servlet implementation class Messages
 */
@WebServlet("/Messages")
public class Messages extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IMessageService messageService = new MessageService();
	Logger LOGGER = LogManager.getLogger(Messages.class.getName());

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("GET /Messages");
		LOGGER.debug(request);
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			LOGGER.error("user not connected", request);
			response.sendRedirect("Connection");
		} else {
			ArrayList<Status> listStatus;
			if (user.getAdministrator()) {
				listStatus = new ArrayList<Status>();
				listStatus.add(Status.PUBLIC);
			} else {
				listStatus = Status.getList();
			}

			List<Message> messages = messageService.getListOfMessages(user);
			request.setAttribute("user", user);
			request.setAttribute("messages", messages);

			request.setAttribute("status", listStatus);
			request.getRequestDispatcher("messages.jsp").forward(request,
					response);
		}
	}
}
