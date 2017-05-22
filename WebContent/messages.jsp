<%@page import="java.util.List"%>
<%@page import="fr.epsi.myEpsi.beans.Message"%>
<%@page import="fr.epsi.myEpsi.beans.Status"%>
<%@page import="fr.epsi.myEpsi.beans.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Epsi - Messages</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
</head>
<body>
	<div class="row">
		<div class="col-md-push-5 col-md-7">
			<h1>Liste des messages</h1>
		</div>
		<div class="col-md-push-2 col-md-10">
			<%
				User user = (User) request.getAttribute("user");
			%>
			<br> <br>

			<%
				List<Message> messages = (List<Message>) request
						.getAttribute("messages");
				for (Message message : messages) {
			%>
			<h3>
				<%
					out.println(message.getId() + " - " + message.getTitle());
				%>
			</h3>
			<%
				
			%>
			<p>
				<%
					out.println("Ecrit par : " + message.getAuthor().getId());
				%>
				<i> <%
 	out.println("<br>date : " + message.getCreationDate());
 		out.println("<br>status : " + message.getStatus());
 %>
				</i>
				<%
					
				%>
			</p>
			<%
				if (user.getAdministrator()
							|| user.getId() == message.getAuthor().getId()) {
			%><a href='AddMessage?id=<%out.println(message.getId());%>'>Détails</a>
			<%
				}
				}
			%>
		</div>

		<div class="col-md-push-2 col-md-10">
			<h1>Créer un message</h1>
			<form action="AddMessage" method="post">
				<div class="col-md-12">
					<label>Titre </label><input type="text" name="title">
				</div>
				<div class="col-md-12">
					<label>Contenu </label><input type="textarea" name="content">
				</div>
				<div class="col-md-12">
					<label>Status </label> <select name="status">
						<%
							List<Status> statusList = (List<Status>) request
									.getAttribute("status");
							for (Status status : statusList) {
								out.println("<option>" + status.toString() + "</option>");
							}
						%>
					
				</div>
				</select> <input type="submit" value="Ajouter">
			</form>
		</div>
		<div class="col-md-12">
			<%
				if (user.getAdministrator()) {
			%>


			<a href='Users'>Gérez les utilisateurs</a>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>