package id.salt.zkoss.zulController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;

import id.salt.zkoss.models.User;
import id.salt.zkoss.services.RegisterService;
import id.salt.zkoss.services.UserDetailService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RegisterComponent extends SelectorComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	Textbox userName;
	@Wire
	Textbox userEmail;
	@Wire
	Textbox userPassword;
	@Wire
	Textbox roles;
	
	@WireVariable("registerService")
	private RegisterService service;
	
	
	@Listen("onClick = #btnRegister; onOk = window")
	public void Register() {
		var user = new User();
		user.setName(userName.getValue());
		user.setEmail(userEmail.getValue());
		user.setPassword(userPassword.getValue());
		user.setRoles(roles.getValue());
		
		var response = service.Register(user);
		if(!response) {
			alert("Something went wrong");
		}else {
			alert("User has ben added");
		}
		
		Executions.sendRedirect("/login");
	}

	
}
