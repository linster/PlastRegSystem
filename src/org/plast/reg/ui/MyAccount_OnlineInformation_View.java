package org.plast.reg.ui;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;

public class MyAccount_OnlineInformation_View extends Panel implements View {

@Override
public void enter(ViewChangeEvent event) {
	// TODO Auto-generated method stub
	
}

public MyAccount_OnlineInformation_View() {
	buildLayout();
}

public void buildLayout(){
	VerticalLayout layout = new VerticalLayout();
	Label label = new Label ("Online Info");
	layout.addComponent(label);
	setContent(layout);
}

}

