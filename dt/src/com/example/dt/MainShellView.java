package com.example.dt;
import java.io.File;

import javax.servlet.annotation.WebServlet;

import com.google.gwt.layout.client.Layout;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.example.dt.*;

@SuppressWarnings("serial")
@Theme("dt")

public class MainShellView extends UI {
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MainShellView.class)
	public static class Servlet extends VaadinServlet {
	}

	Tree navtree = new Tree("Navigation");
	Panel apppanel = new Panel("Application View");
	@Override
	protected void init(VaadinRequest request) {
		initLayout();
		initNavTree();
		
	}
	
	private void initLayout() {
		/** Draws a layout something similar to 
		 *   LOGO
		 *   ---------
		 *   Treeview | Panel
		 *            |
		 */
	GridLayout grid = new GridLayout(2,2);
	setContent(grid);
	grid.setColumnExpandRatio(0, 1);
	grid.setColumnExpandRatio(1, 4);
	grid.setRowExpandRatio(0, 1);
	grid.setRowExpandRatio(1, 8);
	
	//Need to create a layout for our Logo to live in.
	HorizontalLayout logolayout = new HorizontalLayout();
	logolayout.setWidth("100%");
	logolayout.setHeight("100px");
	
	FileResource rlogoimage = new FileResource( new File((VaadinService.getCurrent().getBaseDirectory().getAbsolutePath())+"/WEB-INF/images/logo.png" ));
	Image logoimage = new Image("Logo", rlogoimage);
	logoimage.setHeight("80px");
	logolayout.addComponent(logoimage);
	logolayout.addComponent(new Label("Main Screen"));

	grid.addComponent(logolayout, 0, 0, 1, 0); //Add a label
	grid.addComponent(this.navtree, 0, 1);
	grid.addComponent(apppanel, 1, 1);
	
	}
	
	private void initNavTree() {
		MainShellControl.initNavTree(this.navtree);
	}
	
	
	

}
