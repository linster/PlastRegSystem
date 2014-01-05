package org.plast.reg.ui;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.common.eventbus.EventBus;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import com.vaadin.ui.Table.Align;

public class MyAccount_OnlineInformation_View extends MainShellView
	{

	public MyAccount_OnlineInformation_View(EventBus authBus, String pageTitle)
		{
			super(authBus, pageTitle);
		
		

			Table t;
			
			VerticalLayout layout = new VerticalLayout();
			
			Label label = new Label ("This is a cool label, man");
			layout.addComponent(label);
			
			layout.addComponent(label);
			
			
	        addStyleName("myaccount_table");
	        
	        //Make a table to put stuff in
	        //TODO: content provider for the table
	        
	        /* Create the table with a caption. */
	        Table table = new Table("This is my Table");

	        
	        table.setWidth("99%");
	        /* Define the names and data types of columns.
	         * The "default value" parameter is meaningless here. */
	        table.addContainerProperty("First Name", String.class,  null);
	        table.addContainerProperty("Last Name",  String.class,  null);
	        table.addContainerProperty("Year",       Integer.class, null);

	        /* Add a few items in the table. */
	        table.addItem(new Object[] {
	            "Nicolaus","Copernicus",new Integer(1473)}, new Integer(1));
	        table.addItem(new Object[] {
	            "Tycho",   "Brahe",     new Integer(1546)}, new Integer(2));
	        table.addItem(new Object[] {
	            "Giordano","Bruno",     new Integer(1548)}, new Integer(3));
	        table.addItem(new Object[] {
	            "Galileo", "Galilei",   new Integer(1564)}, new Integer(4));
	        table.addItem(new Object[] {
	            "Johannes","Kepler",    new Integer(1571)}, new Integer(5));
	        table.addItem(new Object[] {
	            "Isaac",   "Newton",    new Integer(1643)}, new Integer(6));
	        
	        
	        
	        // Allow selecting items from the table.
	        table.setSelectable(true);

	        // Send changes in selection immediately to server.
	        table.setImmediate(true);

	        // Shows feedback from selection.
	        final Label current = new Label("Selected: -");

	        // Handle selection change.
	        table.addValueChangeListener(new Property.ValueChangeListener() {
	            public void valueChange(ValueChangeEvent event) {
	                current.setValue("Selected: " + table.getValue());
	            }
	        });
	        
	        
	        table.addColumnResizeListener(new Table.ColumnResizeListener(){
	            public void columnResize(ColumnResizeEvent event) {
	                // Get the new width of the resized column
	                int width = event.getCurrentWidth();
	                
	                // Get the property ID of the resized column
	                String column = (String) event.getPropertyId();

	                // Do something with the information
	                table.setColumnFooter(column, String.valueOf(width) + "px");
	            }
	        });
	                
	        // Must be immediate to send the resize events immediately
	        table.setImmediate(true);
	        
	        
		    layout.addComponent(table);
			
			//Set the main application component to be a label.
			//In the future, we'll probably define a FormLayout, pack a bunch of fields into it, and then add the layout into the line below.
			//super.setApplicationComponent(new Label("Online Account Manager"));
			
			this.apppanel.setContent(layout);
		}
	

}
