package org.plast.reg.ui;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.common.eventbus.EventBus;
import com.vaadin.data.Property;
import com.vaadin.ui.*;

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
	        
	        t = new Table() {
/*	            @Override
	            protected String formatPropertyValue(Object rowId, Object colId,
	                    Property<?> property) {
	                if (colId.equals("Time")) {
	                    SimpleDateFormat df = new SimpleDateFormat();
	                    df.applyPattern("MM/dd/yyyy hh:mm:ss a");
	                    return df
	                            .format(((Calendar) property.getValue()).getTime());
	                } else if (colId.equals("Price")) {
	                    if (property != null && property.getValue() != null) {
	                        String ret = new DecimalFormat("#.##").format(property
	                                .getValue());
	                        return "$" + ret;
	                    } else {
	                        return "";
	                    }
	                }
	                return super.formatPropertyValue(rowId, colId, property);
	            }*/
	        };
	        t.setSizeFull();
	        t.addStyleName("borderless");
	        t.setSelectable(true);
	        t.setColumnCollapsingAllowed(true);
	        t.setColumnReorderingAllowed(true);
			
	        
		    layout.addComponent(t);
			
			//Set the main application component to be a label.
			//In the future, we'll probably define a FormLayout, pack a bunch of fields into it, and then add the layout into the line below.
			//super.setApplicationComponent(new Label("Online Account Manager"));
			
			this.apppanel.setContent(layout);
		}
	

}
