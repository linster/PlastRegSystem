package org.plast.reg.ui;
import java.io.File;

import javax.servlet.annotation.WebServlet;

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
		new String[]{ "DummyTable", "Add", "Edit"},
		new String[]{ "Item 2", "Child 1", "Child 2"}
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
	apppanel.setHeight("800px");
	apppanel.setWidth("800px");
	//apppanel.setHeight("800px");
	//apppanel.setWidth(grid.getWidth(), grid.getWidthUnits());
	}
	
	private void initNavTree() {		
		/** Populates the navigation tree of the main "Shell" with entries
		 * 
		 */
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
		 */
		
		if (parent.equals("null") && child.equals("DummyTable")){ //String.valueOf(Object) returns the string "null", not the null NULL.																
			return new org.plast.reg.ui.DummyTable();				  //This is the way to access a root element of the tree.	
		}
		
		if (parent.equals("DummyTable") && child.equals("Add")){
			return new org.plast.reg.ui.DummyTable();
		}
		
		if (parent.equals("null") && child.equals("Item 2")) {
			return new Label("Item 2 root");
		}
		
		if (parent.equals("Item 2") && child.equals("Child 1")){
			return new Label("Item 2 Child 1");
		}
		
		
		return new Label("Nothing selected");
		
		
	}
	
	
	private void initMainMenuLayout() {
		/** Initialize the System menubar. In here, there will be the Office-365-style switcher
		 * 
		 */
		mainMenuLayout.addComponent(new Label("Welcome User!"));
		mainMenuLayout.addComponent(new Button("Sign out"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		/** This is the main hook that Vaadin uses when a view is changed.
		 * 	The MasterNavigator generates a ViewChangeEvent which is passed into this method.
		 * 
		 *  This is probably a good place to put some SpringSecurity auth checking
		 */
		
		Notification.show("Welcome to the main form");
		
	}
	
	public MainShellView() {
		initLayout();
		initMainMenuLayout();
		initNavTree();
		initNavTreeListeners();
		apppanel.setContent(apppanelComponent);
	}

	

}
