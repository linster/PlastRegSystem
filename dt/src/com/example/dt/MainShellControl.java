package com.example.dt;

import java.util.Vector;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Tree;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
public class MainShellControl extends MainShellView {

	final static String[][] menuitems = new String[][] {
			new String[]{ "Home"},
			new String[]{ "DummyTable", "Add", "Edit"},
			new String[]{ "Item 2", "Child 1", "Child 2"}
	};
	
	
	public static void initNavTree(Tree t) {
		/** Populates the navigation tree of the main "Shell" with entries
		 * 
		 */
		for (String[] parent: menuitems){
			t.addItem(parent[0]);
			
			if (parent.length == 1) {
				t.setChildrenAllowed(parent, false);
			} else {
				for (String child : parent) {
					if (child.equals(parent[0])) //So I can use nice for notation
						continue; 				 //without reprinting the parent
					t.addItem(child);					
					t.setParent(child, parent[0]);
					t.setChildrenAllowed(child, false);
				}
				t.expandItemsRecursively(parent);
			}
		}
		t.setImmediate(true);
		
	}
	
	public static Component NavTreeMethodDispatch(String parent, String child) {
		/** Given a parent, child strings, return a component to set the app to. If no dispatch effective, set to null
		 * @return Component. For use in the SetContent.
		 */
		
		if (parent.equals("null") && child.equals("DummyTable")){ //String.valueOf(Object) returns the string "null", not the null NULL.																
			return new com.example.dt.DummyTable();				  //This is the way to access a root element of the tree.	
		}
		
		if (parent.equals("DummyTable") && child.equals("Add")){
			return new com.example.dt.DummyTable();
		}
		
		if (parent.equals("null") && child.equals("Item 2")) {
			return new Label("Item 2 root");
		}
		
		if (parent.equals("Item 2") && child.equals("Child 1")){
			return new Label("Item 2 Child 1");
		}
		
		
		return new Label("Nothing selected");
		
		
	}
	
	

	

}
