package id.salt.zkoss.zulController;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

import id.salt.zkoss.models.Book;
import id.salt.zkoss.models.User;
import id.salt.zkoss.services.BookService;
import id.salt.zkoss.services.UserDetailService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BookListComponent extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@WireVariable("bookService")
	private BookService service;
	
	@WireVariable("userInfoService")
	private UserDetailService userService;
	
	@Wire
	private Grid gridData;
	
	@Wire
	private Label labelUser;
	
	private ListModel<Book> modelData = new ListModelList<Book>();
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		gridData.setModel(modelData);
		MapData();
		GetUser();
	}
	
	private void MapData()
	{
		var data = service.getList();
		
		ListModelList<Book> m = (ListModelList<Book>) modelData;
		m.clear();
		
		data.forEach(d -> m.add(d));
	}
	
	private void GetUser() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User user = (User)auth.getPrincipal();
//		labelUser.setValue(null);
	}
}
