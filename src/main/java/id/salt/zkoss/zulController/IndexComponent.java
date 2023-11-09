package id.salt.zkoss.zulController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

public class IndexComponent extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire
	public Textbox txtBox;
	
	@Listen("onClick = #btnAlert; onOK = window")
	public void OnAlert()
	{
		alert(txtBox.getValue());
	}
	
}
