package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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

@WebServlet("/AddMessage")
public class AddMessage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger LOGGER = LogManager.getLogger(AddMessage.class.getName());
	IMessageService messageService = new MessageService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("GET /AddMessage");
		LOGGER.debug(request);
		ArrayList<Status> status = Status.getList();
		User user = (User) request.getSession().getAttribute("user");
		Message message = messageService.getMessage(Long.parseLong(request
				.getParameter("id")));
		request.setAttribute("message", message);
		request.setAttribute("status", status);
		request.getRequestDispatcher("addMessage.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("POST /AddMessage");
		LOGGER.debug(request);
		User user = (User) request.getSession().getAttribute("user");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String status = request.getParameter("status");
		if (request.getParameter("action") != null
				&& request.getParameter("action").equals("delete")) {
			this.doDelete(request, response);
		} else if (request.getParameter("action") != null
				&& request.getParameter("action").equals("update")) {
			this.doPut(request, response);
		} else {
			Message message = new Message();
			message.setTitle(title);
			message.setContent(content);
			message.setStatus(Status.valueOf(request.getParameter("status")));
			message.setAuthor(user);
			message.setCreationDate(new Timestamp(new Date().getTime()));
			message.setUpdateDate(new Timestamp(new Date().getTime()));
			messageService.addMessage(message);
			response.sendRedirect("Messages");
		}

	}

	@Override
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("DELETE /ADDMESSAGE");
		LOGGER.debug(request);
		User user = (User) request.getSession().getAttribute("user");
		String id = request.getParameter("id");
		Message message = messageService.getMessage(Long.parseLong(request
				.getParameter("id")));
		messageService.deleteMessage(message);
		response.sendRedirect("Messages");

	}

	@Override
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("PUT /ADDMESSAGE");
		LOGGER.debug(request);
		User user = (User) request.getSession().getAttribute("user");
		String id = request.getParameter("id");
		Message message = messageService.getMessage(Long.parseLong(request
				.getParameter("id")));
		messageService.updateMessageStatus(message,
				Status.valueOf(request.getParameter("status")).ordinal());
		response.sendRedirect("Messages");
	}
}
