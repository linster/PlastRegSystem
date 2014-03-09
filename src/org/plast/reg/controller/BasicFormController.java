package org.plast.reg.controller;

import java.util.*;

import org.plast.reg.model.Model;

import com.vaadin.navigator.View;

public class BasicFormController extends Controller {
/**
 * BasicFormController
 * --Takes in a hashtable of fields and values (created by the view).
 * --One of the fields in the hashtable is the class name of the model that should be tied to the view
 * --Instantiates this model, passes hashtable to it.
 * 
 * Model
 * --Takes in the hashtable of [FieldName, Value] pairs, and runs the queries neccessary to the DB.
 * 
 * 
 */
	
	//Contains a HashTable of <String,String> Key, Value pairs for the form contents.
	Map<String, String> formData = new HashMap<String, String>();
	
	BasicFormController(Model Model) {
		//PlastView needs an interface for RegisterController.
		//Each PlastView needs a RegisterController method. Adds a reference to the 
		//Controller
		
		this.model = Model;
		
	}
	
	Model model;
	
	Model getModel() { return this.model; }
	
	void passFormData(Map<String,String> formdata) {
		this.getModel().acquireData(formdata);
	}
	
}
