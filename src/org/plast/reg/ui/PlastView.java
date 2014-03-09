package org.plast.reg.ui;

import org.plast.reg.controller.Controller;

import com.vaadin.ui.Panel;



public class PlastView extends Panel{

	Controller myController; 
	void RegisterController(Controller c) {
		myController = c;
	}
	
	Controller getController() {
		return this.myController;
	}
	
}
