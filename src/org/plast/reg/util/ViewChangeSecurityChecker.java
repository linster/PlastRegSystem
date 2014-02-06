package org.plast.reg.util;

import org.plast.reg.AuthenticationHolder;
import org.plast.reg.ui.LoginView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.*;
import com.vaadin.ui.Notification;

public class ViewChangeSecurityChecker implements ViewChangeListener {
	 @Override
     public boolean beforeViewChange(ViewChangeEvent event) {
             if (event.getNewView() instanceof LoginView) {
             	return true;
             }

             Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             if (authentication == null) {
            	 /* BUG FIX: For some reason the Authentication object we need was not passed from the LoginEvent in 
            	  * PlastRegSystemUI (probably because this ViewChangerListener was initialized before a login happened).
            	  */
             	Authentication thread_local = AuthenticationHolder.getAuthentication();
             		if (thread_local == null){
             			return false;
             		} else {
             			return thread_local.isAuthenticated();
             		}
             } else {
            	 //Notification.show("ViewChangeSecCheck bool "+authentication.isAuthenticated());
             	return authentication.isAuthenticated();
             }
          
             
     }
     @Override
     public void afterViewChange(ViewChangeEvent event) {}
}
