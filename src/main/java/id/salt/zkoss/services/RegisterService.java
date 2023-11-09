package id.salt.zkoss.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import id.salt.zkoss.models.User;
import id.salt.zkoss.repositories.UserRepository;

@Service("registerService")
public class RegisterService {

	@Autowired
	private UserRepository repository;
	
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Boolean Register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		if(repository.save(user) != null) {
			return true;
		}
		
		return false;
	}
}
