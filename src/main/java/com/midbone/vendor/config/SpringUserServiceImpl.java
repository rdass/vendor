package com.midbone.vendor.config;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.midbone.vendor.model.user.CurrentUser;
import com.midbone.vendor.model.user.Role;
import com.midbone.vendor.model.user.User;
import com.midbone.vendor.service.UserService;


@Service("userDetailsService")
public class SpringUserServiceImpl implements UserDetailsService {

	static final Logger logger = LoggerFactory.getLogger(SpringUserServiceImpl.class);

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		logger.info("User : {}", user);
		if(user==null) {
			logger.info("User not found");
			throw new UsernameNotFoundException("User doesn't exist");
		}
		Set<Role> roles = user.getRoles();
		CurrentUser currentUser = new CurrentUser(user.getUsername(), user.getPassword(), true, true, true, true, userService.getGrantedAuthorities(roles));

		// Adding additional minimum information to current user
		currentUser.setId(user.getId());
		currentUser.setFirstname(user.getFirstname());
		currentUser.setLastname(user.getLastname());
		currentUser.setEmail(user.getEmail());
		currentUser.setPhone(user.getPhone());
		currentUser.setType(user.getType());
		currentUser.setRoles(roles);
		currentUser.setActive(user.isActive());
		return currentUser;
	}

}
