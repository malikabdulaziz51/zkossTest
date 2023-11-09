package id.salt.zkoss.services;

import java.util.Optional;
import id.salt.zkoss.models.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import id.salt.zkoss.models.User;
import id.salt.zkoss.repositories.UserRepository;

@Service("userInfoService")
public class UserDetailService implements UserDetailsService{
	
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByUserName(username);
		
		return user.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}
	
	
	public Boolean Register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		if(repository.save(user) != null) {
			return true;
		}
		
		return false;
	}
	
	
}
