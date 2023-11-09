package id.salt.zkoss.zulController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;
import id.salt.zkoss.models.Login;
import id.salt.zkoss.services.JwtService;
import id.salt.zkoss.services.LoginService;
import id.salt.zkoss.services.UserDetailService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class LoginComponent extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	Textbox userName;
	@Wire
	Textbox userPassword;
	
	@WireVariable("loginService")
	private LoginService service;
	
	
	@Listen("onClick = #btnLogin; onOk = window")
	public void Login() {
		var user = new Login();
		user.setUsername(userName.getValue());
		user.setPassword(userPassword.getValue());
		var token = service.Login(user);
		if(service.IsValidToken(token, user.getUsername())) {			
			Executions.sendRedirect("/auth/book");
		}else {
			alert("Login Failed!!!");
		}
	}
}
