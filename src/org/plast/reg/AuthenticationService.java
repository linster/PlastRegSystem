package org.plast.reg;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.security.core.authority.*;

import com.vaadin.ui.Notification;
/**
 * This class is responsible for handling Login/Logout. The Authentication EventBus' LoginEvent/LogoutEvents (defined in 
 * the PlastressystemUI file) are bound to these methods to do the dirty work.
 * @author Stefan
 *
 */
public class AuthenticationService {
	
	public void handleAuthentication(String login, String password, HttpServletRequest httpRequest) {
	       
	        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
	        token.setDetails(new WebAuthenticationDetails(httpRequest));
	        ServletContext servletContext = httpRequest.getSession().getServletContext();
	        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	        AuthenticationManager authManager = wac.getBean(AuthenticationManager.class);
	        Authentication authentication = authManager.authenticate(token);
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public void handleLogout(HttpServletRequest httpRequest) {
	
	        ServletContext servletContext = httpRequest.getSession().getServletContext();
	        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	        LogoutHandler logoutHandler = wac.getBean(LogoutHandler.class);
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        // Response should not be used?
	        logoutHandler.logout(httpRequest, null, authentication);
	}
	
	public static Collection<SimpleGrantedAuthority> GetAuthorities( Collection<? extends GrantedAuthority> authorities) {
		/**
		 * This method takes in an authority and returns a collection containing the "sub"/child authorities that 
		 * the given "super-authority" is also capable of.
		 */
		
		
		/* The following authorities are defined in the system (Read > as "includes")
  			ROLE_ADMIN > ROLE_REGISTRAR
   	     	ROLE_REGISTRAR > ROLE_PARENT
    	    ROLE_ZVIASKOVY > ROLE_PARENT
		 */
		
		if (authorities == null)
			return null;
		
		if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
			Collection<SimpleGrantedAuthority> rc = new HashSet<SimpleGrantedAuthority>();
			rc.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			rc.add(new SimpleGrantedAuthority("ROLE_REGISTRAR"));
			rc.add(new SimpleGrantedAuthority("ROLE_ZVIASKOVY"));
			rc.add(new SimpleGrantedAuthority("ROLE_PARENT"));
			return rc;
		}
		
		if (authorities.contains(new SimpleGrantedAuthority("ROLE_REGISTRAR"))){
			Collection<SimpleGrantedAuthority> rc = new HashSet<SimpleGrantedAuthority>();
			rc.add(new SimpleGrantedAuthority("ROLE_REGISTRAR"));
			rc.add(new SimpleGrantedAuthority("ROLE_ZVIASKOVY"));
			rc.add(new SimpleGrantedAuthority("ROLE_PARENT"));
			return rc;
		}
		
		if (authorities.contains(new SimpleGrantedAuthority("ROLE_ZVIASKOVY"))){
			Collection<SimpleGrantedAuthority> rc = new HashSet<SimpleGrantedAuthority>();
			rc.add(new SimpleGrantedAuthority("ROLE_ZVIASKOVY"));
			rc.add(new SimpleGrantedAuthority("ROLE_PARENT"));
			return rc;
		}
		
		if (authorities.contains(new SimpleGrantedAuthority("ROLE_PARENT"))){
			Collection<SimpleGrantedAuthority> rc = new HashSet<SimpleGrantedAuthority>();
			rc.add(new SimpleGrantedAuthority("ROLE_PARENT"));
			return rc;
		}
		
		return null;
		/* This is the ideal code that uses the beans defined. However it fails with the following exception:
		 * 
		 * 	at org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl.getRolesReachableInOneOrMoreSteps(RoleHierarchyImpl.java:148)
			at org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl.getReachableGrantedAuthorities(RoleHierarchyImpl.java:110)
			at org.plast.reg.ui.MainShellView.NavTreeMethodDispatch(MainShellView.java:232)
		 * 
		 * 
		 * 	RoleHierarchy rhi = new RoleHierarchyImpl(); //This class doesn't work.
			Collection<? extends GrantedAuthority> ca = currentAuth.getAuthorities();
			Notification.show("CA " + ca.iterator().next().getAuthority());
			Collection<? extends GrantedAuthority> ga = rhi.getReachableGrantedAuthorities(ca); //NPE here.
			return ga;
		 */
		
		// When remaking this function, go through the GA list, and build a new list by converting each ga<? extends GrantedAuthority>
		// using a for-loop. Call the .toString() method on each, build a new list.
	}

	
	
}
