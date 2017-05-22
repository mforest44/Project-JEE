<%@page import="java.util.List"%>
<%@page import="fr.epsi.myEpsi.beans.Message"%>
<%@page import="fr.epsi.myEpsi.beans.Status"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Epsi - Détails Message</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

</head>
<body>
	<div class="row">
		<div class="col-md-push-5 col-md-7">
			<h1>Détail Message</h1>
		</div>

		<div class="col-md-push-2 col-md-10">

			<%
				Message message = (Message) request.getAttribute("message");
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
					out.println(message.getContent());
				%>
			</p>
		</div>
		<div class="col-md-push-2 col-md-10">
			<form action="AddMessage" method="post">
				<input name="action" type="hidden" value="delete" /> <input
					name="id" type="hidden" value="<%out.print(message.getId());%>" />
				<input type="submit" value="Supprimer ce post" />
			</form>
		</div>
		<div class="col-md-push-2 col-md-10">
		<h1>Modifier ce message</h1>
		<form action="AddMessage" method="post">
			<input name="action" type="hidden" value="update" /> <input
				name="id" type="hidden" value="<%out.print(message.getId());%>" />
			<label>Status</label> <select name="status">
				<%
					List<Status> statusList = (List<Status>) request
							.getAttribute("status");
					for (Status status : statusList) {
						out.println("<option>" + status.toString() + "</option>");
					}
				%>
			</select> <input type="submit" value="Ajouter">
		</form>
		</div>
	</div>
</body>
</html>