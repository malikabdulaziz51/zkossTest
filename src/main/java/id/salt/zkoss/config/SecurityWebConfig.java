package id.salt.zkoss.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.DispatcherTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import id.salt.zkoss.filter.JwtAuthFilter;
//import id.salt.zkoss.filter.JwtAuthFilter;
import id.salt.zkoss.models.User;
import id.salt.zkoss.services.UserDetailService;
import jakarta.servlet.DispatcherType;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityWebConfig {
	
 	private static final String ZUL_FILES = "/zkau/web/**/*.zul";
    private static final String ZK_RESOURCES = "/zkres/**";
    // allow desktop cleanup after logout or when reloading login page
    private static final String REMOVE_DESKTOP_REGEX = "/zkau\\?dtid=.*&cmd_0=rmDesktop&.*";

    @Autowired
    private JwtAuthFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		return http.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers(new AndRequestMatcher(new DispatcherTypeRequestMatcher(DispatcherType.ERROR), AntPathRequestMatcher.antMatcher("/error"))).permitAll() // allow default error dispatcher
			            .requestMatchers(new AndRequestMatcher(new DispatcherTypeRequestMatcher(DispatcherType.FORWARD), AntPathRequestMatcher.antMatcher(ZUL_FILES))).permitAll() // allow forwarded access to zul under class path web resource folder
			            .requestMatchers(AntPathRequestMatcher.antMatcher(ZUL_FILES)).denyAll() // block direct access to zul under class path web resource folder
			            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/zkau")).permitAll() // permit direct access to zul under class path web resource folder
			            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, ZK_RESOURCES)).permitAll() // allow zk resources
			            .requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, REMOVE_DESKTOP_REGEX)).permitAll() // allow desktop cleanup
			            .requestMatchers(req -> "rmDesktop".equals(req.getParameter("cmd_0"))).permitAll() // allow desktop cleanup from ZATS
						.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/favicon.ico")).permitAll() // allow favicon.ico
//						.requestMatchers(mvc.pattern("/login")).permitAll()
						.requestMatchers(mvc.pattern("/auth/book")).authenticated()
						.anyRequest().authenticated()
						)
				.formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/auth/book", true).permitAll())
				.authenticationProvider(authenticationProvider())
                .build(); 
				
	}
	
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
	    return new MvcRequestMatcher.Builder(introspector);
	}
	
//	@Bean
//    public SessionRegistry sessionRegistry() {
//        SessionRegistry sessionRegistry = new SessionRegistryImpl();
//        return sessionRegistry;
//    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	 public AuthenticationProvider authenticationProvider() { 
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
        authenticationProvider.setUserDetailsService(userDetailsService()); 
        authenticationProvider.setPasswordEncoder(passwordEncoder()); 
        return authenticationProvider; 
    } 
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
        return config.getAuthenticationManager(); 
    }
	

	//User Registration
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailService();
	}
	
	@Bean
	public User user() {
		return new User();
	}
	
	
}
