<%@page import="java.util.List"%>
<%@page import="fr.epsi.myEpsi.beans.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<title>My Epsi - Users</title>
</head>
<body>
			<h1>Liste des utilisateurs</h1>
			<%
				List<User> attribut = (List<User>) request.getAttribute("user");
				for (User user : attribut) {
			%><h3> Utilisateur : 
				<%
					out.println(user.getId());
				%>
			</h3>
			<%
				
			%><p> Password actuel : 
				<%
					out.println(user.getPassword());
				%>
			</p>
			<u>Modification : </u> <br>
			<form action="Users" method="post">
				<input name="action" type="hidden" value="update" /> <input
					name="login" value="<%out.print(user.getId());%>" type="hidden" />
				<label>Password</label><input name="password" type="password"
					value="<%out.print(user.getPassword());%>" /> <label>Retapez
					password</label><input name="repassword" type="password" /> <label>Admin</label><input
					type="checkbox" name="admin"<%if (user.getAdministrator()) {
					out.print("checked='true'");
				}%>"><br>
				<input type="submit" value="Modifier un utilisateur" />
			</form>
			<form action="Users" method="post">
				<input name="action" type="hidden" value="delete" /> <input
					name="login" type="hidden" value="<%out.print(user.getId());%>" />
				<input type="submit" value="Supprimer cet utilisateur" />
			</form>
		<%
			
		%><hr>
		<%
			}
		%>
			<h1>Ajouter un utilisateur</h1>

			<form action="Users" method="post">
				<label>Login</label><input name="login" type="text" /> <label>Password</label><input
					name="password" type="password" /> <label>Retapez password</label><input
					name="repassword" type="password" /> <label>Admin</label><input
					type="checkbox" name="admin"><br> <input type="submit"
					value="Ajouter un utilisateur" />
			</form>
</body>
</html>