package org.plast.reg;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.core.Application;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinServletResponse;
import com.vaadin.server.VaadinServletService;


@SuppressWarnings("serial")
public class VaadinServletServiceOverride extends VaadinServlet { 

@Override
protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
	VaadinRequestHolder.setRequest(request);
	super.service(request, response);
	// We remove the request from the thread local, there's no reason to keep it once the work is done
	VaadinRequestHolder.clean();
	SecurityContextHolder.clearContext();
}

}
