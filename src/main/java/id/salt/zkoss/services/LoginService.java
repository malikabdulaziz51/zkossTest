package id.salt.zkoss.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import id.salt.zkoss.models.Login;
import id.salt.zkoss.models.User;
import id.salt.zkoss.repositories.UserRepository;

@Service("loginService")
public class LoginService implements UserDetailsService {


	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserRepository repository;
	

	@Autowired
	private UserDetailService userInfoService;
	
	public Boolean Login(Login login) {
		Authentication auth = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
				);
		if(auth.isAuthenticated()) {
			UserDetails userDetails = userInfoService.loadUserByUsername(login.getUsername());
			UsernamePasswordAuthenticationToken authToken = 
					new UsernamePasswordAuthenticationToken
					(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);
			return true;
		}else {
			throw new UsernameNotFoundException("Invalid user");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByUserName(username);
				
		return user.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}
	
	public Boolean IsValidToken(String token, String username) {
		var userDetails = loadUserByUsername(username);
		return jwtService.validateToken(token, userDetails);
	}
	
}
