package org.plast.reg.ui;

import com.google.common.eventbus.EventBus;
import com.vaadin.ui.*;

public class MyAccount_OnlineInformation_View extends MainShellView
	{

	public MyAccount_OnlineInformation_View(EventBus authBus, String pageTitle)
		{
			super(authBus, pageTitle);
		
		

			
			VerticalLayout layout = new VerticalLayout();
			
			Label label = new Label ("Thisis a cool label");
			layout.addComponent(label);
			
			
			//Set the main application component to be a label.
			//In the future, we'll probably define a FormLayout, pack a bunch of fields into it, and then add the layout into the line below.
			//super.setApplicationComponent(new Label("Online Account Manager"));
			
			this.apppanel.setContent(layout);
		}
	

}
