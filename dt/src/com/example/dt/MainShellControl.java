package com.example.dt;

import com.vaadin.ui.Tree;

public class MainShellControl extends MainShellView {

	public static void initNavTree(Tree t) {
		/** Populates the navigation tree of the main "Shell" with entries
		 * 
		 */
		
		final String[][] menuitems = new String[][] {
				new String[]{ "Home"},
				new String[]{ "DummyTable", "Add", "Edit"},
				new String[]{ "Item 2", "Child 1", "Child 2"}
		};
		
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
	
	public static void initNavTreeListeners(Tree t){
		/** Creates the ValueChangeListener so that when the user clicks on
		 *  a navigation item in the nav tree, it sets the content 
		 */
	}

	

}
