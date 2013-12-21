package org.plast.reg;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.plast.reg.ui.LoginView;
import com.vaadin.navigator.ViewChangeListener;

public class VaadinViewChangeCheck {
	@SuppressWarnings("serial")
	public class ViewChangeSecurityChecker implements ViewChangeListener {

	        @Override
	        public boolean beforeViewChange(ViewChangeEvent event) {
	                if (event.getNewView() instanceof LoginView) {
	                	return true;
	                }

	                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	                return authentication == null ? false : authentication.isAuthenticated();
	        }
	        @Override
	        public void afterViewChange(ViewChangeEvent event) {}
	}
}