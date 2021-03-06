/**
 * 
 */
package com.zenika.business;

import static com.zenika.repository.UserSpecs.*;
import static org.springframework.data.jpa.domain.Specifications.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenika.model.User;
import com.zenika.repository.UserRepository;

/**
 * @author acogoluegnes
 *
 */
@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private UserRepository userRepository;
	
	@Autowired
	public AuthenticationServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	/* (non-Javadoc)
	 * @see com.zenika.business.AuthenticationService#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public User authenticate(String login, String password) {
		User user = null;
		try {
			user = userRepository.findOne(where(loginIs(login)).and(isActivated()));
		} catch(IncorrectResultSizeDataAccessException e) {
			// rien à faire
		}
		if(user == null) {
			return null;
		} else {
			if(user.getPassword().equals(password)) {
				return user;
			} else {
				return null;
			}
		}
	}

}
