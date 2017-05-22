<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bienvenue sur My Epsi !</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
</head>
<body>
	<div class="row">
		ID admin : 1 - Password : test
		<form action="Connection" method="post">
			<div class="col-md-push-5 col-md-7">
				<input type="text" name="username">
			</div>
			<div class="col-md-push-5 col-md-7">
				<input type="password" name="password">
			</div>
			<div class="col-md-push-6 col-md-6">
			<input type="submit" value="login"> <span class="error">${error}</span>
			</div>
		</form>
	</div>
</body>
</html>