package org.plast.reg.ui;
import java.io.File;
import java.util.Collection;

import javax.servlet.annotation.WebServlet;

import com.google.common.eventbus.EventBus;

import org.plast.reg.AuthenticationService;
import org.plast.reg.events.*;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.security.access.hierarchicalroles.*;
import org.springframework.security.core.*;

import com.google.gwt.layout.client.Layout;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

@SuppressWarnings("serial")
@Theme("dt")

public class MainShellView extends Panel implements View{
	
	
	
	/* Diagram of layouts
	 * (all these in a 2 column grid)
	 * -----------------
	 * LOGO (Horiz)    |
	 * ----------------|
	 * mainMenu (Horiz)|
	 * ----------------|
	 * Tree | Panel    |
	 * 
	 */
	

	Tree navtree = new Tree("Navigation");
	Panel apppanel = new Panel("Application View");
	Component apppanelComponent = new Label("Test");
	HorizontalLayout mainMenuLayout = new HorizontalLayout(); //Holds signin/switch user buttons, and welcome doo-dads.
	final static String[][] navmenuitems = new String[][] {
		new String[]{ "Home"},
		new String[]{ "My Account", "Online Information", "Personal Information"},
		new String[]{ "Registrations", "Ulad Registrations"},
		new String[]{ "Finances", "Baly Balance"},
		new String[]{ "Administrative", "Send Bug Report"}
		};
	
	final static String[][] registrarOnlyMenuItems = new String[][] {
		new String[] {"Registrations", "Register Person to Plast", "Create Family Unit", "Map Users to People", "Bulk Register into Ulady"},
		new String[] {"Finances", "Adjust Baly Balance"}
	};
	
	final static String[][] adminOnlyMenuItems = new String[][] {
		new String[] {"Administrative", "Debug"}
	};
	
	
	private void initLayout() {
		/** Draws a layout something similar to 
		 *   LOGO
		 *   ---------
		 *   Menu
		 *   --------------
		 *   Treeview | Panel
		 *            |
		 */
	GridLayout grid = new GridLayout(2,3);
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
	Image logoimage = new Image("", rlogoimage);
	logoimage.setHeight("80px");
	logolayout.addComponent(logoimage);
	Label main = new Label("<h1>Main Screen</h1>",Label.CONTENT_XHTML);
	logolayout.addComponent(main);
	logolayout.setComponentAlignment(main, Alignment.BOTTOM_CENTER );
	grid.addComponent(logolayout, 0, 0, 1, 0); 
	
	//Make a new Horizontal Layout for signout/switch user buttons.
	grid.addComponent(mainMenuLayout, 1, 1, 1, 1);
	grid.setComponentAlignment(mainMenuLayout, Alignment.MIDDLE_RIGHT);
	grid.addComponent(this.navtree, 0, 2);
	grid.addComponent(apppanel, 1, 2);
	
	//TODO Make this app panel actually expand to the full window size. 
	//apppanel.setHeight("800px");
	//apppanel.setWidth("800px");
	apppanel.setSizeFull();
	//apppanel.setHeight("800px");
	//apppanel.setWidth(grid.getWidth(), grid.getWidthUnits());
	}
	
	private void populateNavTree() {		
		/** Populates the navigation tree of the main "Shell" with entries
		 * 
		 */
		//Populate the tree with navmenuitems. This string[][] array contains
		//all the items seen by non privileged users. 
		for (String[] parent: navmenuitems){
			navtree.addItem(parent[0]);
			
			if (parent.length == 1) {
				navtree.setChildrenAllowed(parent, false);
			} else {
				for (String child : parent) {
					if (child.equals(parent[0])) //So I can use nice for notation
						continue; 				 //without reprinting the parent
					navtree.addItem(child);					
					navtree.setParent(child, parent[0]);
					navtree.setChildrenAllowed(child, false);
				}
				navtree.expandItemsRecursively(parent);
			}
		}
		navtree.setImmediate(true);
		
		//Populate the tree with the administrative functions, but only if the user has the correct authorization level
		
		try{
			//Create a list of all authorities reachable by my current authority.
			Collection<SimpleGrantedAuthority> ga = AuthenticationService.GetAuthorities(this.currentAuth.getAuthorities());
			if ( ga.contains(new SimpleGrantedAuthority("ROLE_REGISTRAR")) ){
				populatePrivNavTree(registrarOnlyMenuItems);
			}
			if ( ga.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
				populatePrivNavTree(adminOnlyMenuItems);
			}
		} catch(NullPointerException e){
			//A NPE happens here if the mainView is initialzed before a login event. (There is no SecurityContextHolder.getContext()). 
			//As a result, need to catch this.
			Notification.show("NPE in Tree Populate", Notification.Type.ERROR_MESSAGE);
		}
		
	}
	/**Helper function to populate the navTree with privaledged menu entries.
	 * 
	 * @param itemArray
	 * This is the String[][] array to pass in. The first element must be the name of the parent element to extend.
	 */
	private void populatePrivNavTree(String[][] itemArray){
		for (String[] parent: itemArray){
			if (parent.length == 1) {
				continue; //If there is nothing to add, then don't add it.
			} else {
				for (String child : parent) {
					if (child.equals(parent[0])) //So I can use nice for notation
						continue; 				 //without reprinting the parent
					navtree.addItem(child);					
					navtree.setParent(child, parent[0]);
					navtree.setChildrenAllowed(child, false);
				}
				navtree.expandItemsRecursively(parent);
			}
		}
		navtree.setImmediate(true);
		
		
		
	}
	
	private void initNavTreeListeners() {
		this.navtree.addValueChangeListener(new ValueChangeListener() {
			//Add the listener to the tree component. 
			@Override
			public void valueChange(final ValueChangeEvent event) {
				//Vaadin handles events as these magic objects
				//which correspond to what type of thing was clicked on.
				if (event.getProperty().getValue() != null) {
					//event.getProperty().getValue() is the name of the thing selected
					final String selectedItem = String.valueOf(event.getProperty().getValue());
					final String parent = String.valueOf(navtree.getParent(navtree.getValue()));
					//Ok, so we can grab the selected item and it's parent. 
					apppanelComponent = NavTreeMethodDispatch(parent, selectedItem);
					apppanel.setContent(apppanelComponent);
				}
				
			}
		});
	}
	
	public Component NavTreeMethodDispatch(String parent, String child) {
		/** Given a parent, child strings, return a component to set the app to. If no dispatch effective, set to null
		 * @return Component. For use in the SetContent.
		 * 
		 * If there is no parent or child, match with the string "null"
		 * For example: parent.equals("null") && child.equals("DummyTable")
		 */
		
		if (parent.equals("null") && child.equals("DummyTable")){ //String.valueOf(Object) returns the string "null", not the null NULL.																
			return new org.plast.reg.ui.DummyTable();				  //This is the way to access a root element of the tree.	
		}
		
		if (parent.equals("My Account") && child.equals("Online Information")){
			return new org.plast.reg.ui.DummyTable();
		}
		
		if (parent.equals("Administrative") && child.equals("Send Bug Report")) {
			
			Notification.show("Auths " + AuthenticationService.GetAuthorities(this.currentAuth.getAuthorities()).toString()  );
			
			/*
			//RoleHierarchyImpl rhi = new RoleHierarchyImpl();
			RoleHierarchy rhi = new RoleHierarchyImpl();
			Collection<? extends GrantedAuthority> ca = currentAuth.getAuthorities();
			Notification.show("CA " + ca.iterator().next().getAuthority());
			Collection<? extends GrantedAuthority> ga = rhi.getReachableGrantedAuthorities(ca);
			Notification.show(ga.toString());
			return new Label("Debugging");
			*/
			return new Label("Debugging");
		}
		
		if (parent.equals("Item 2") && child.equals("Child 1")){
			return new Label("Item 2 Child 1");
		}
		
		
		return new Label("Nothing selected");
		
		
	}
	
	Button bsignout = new Button("Sign out");
	
	private void initMainMenuLayout(final EventBus authBus) {
		/** Initialize the System menubar. In here, there will be the Office-365-style switcher
		 * 
		 */
		mainMenuLayout.addComponent(new Label("Welcome User!"));
		mainMenuLayout.addComponent(bsignout);
		bsignout.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				authBus.post(new LogoutEvent());
			}
		});
	}

	
	Authentication currentAuth;
	@Override
	public void enter(ViewChangeEvent event) {
		/** This is the main hook that Vaadin uses when a view is changed.
		 * 	The MasterNavigator generates a ViewChangeEvent which is passed into this method.
		 * 
		 *  
		 */
		
		//Notification.show("Welcome to the main form");
		this.currentAuth = SecurityContextHolder.getContext().getAuthentication();
		populateNavTree(); //Need to repopulate the nav tree after authentication
	}
	
	
	public MainShellView(final EventBus authBus) {
		initLayout();
		initMainMenuLayout(authBus);
		populateNavTree();
		initNavTreeListeners();
		apppanel.setContent(apppanelComponent);
	}

	

}
