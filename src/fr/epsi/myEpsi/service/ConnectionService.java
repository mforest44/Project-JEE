package fr.epsi.myEpsi.service;

import fr.epsi.myEpsi.beans.User;

public class ConnectionService implements IConnectionService {

	IUserService userService = new UserService();
	
	@Override
	public boolean isAuthorized(User user) {
		User user2 = new User();
		user2.setPassword(user.getPassword());
		System.out.println(user.getId());
		if(user2.getPassword().equals(user.getPassword())){
			return true;
		}
		return false;
	}

}
