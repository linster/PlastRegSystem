package com.example.dt;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.*;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;
import org.springframework.web.filter.DelegatingFilterProxy;

public class ContextInitializer implements WebApplicationInitializer {
	/** This class is responsible for programmatically intializing the SpringContextListener, among other 
	 *  spring listeners. (This is because I was unable to insert the lines required for web.xml, since that 
	 *  file is not in my project.)
	 *  
	 *  Required Lines:
	 *  http://docs.spring.io/spring/docs/1.2.8/reference/webintegration.html
	 *  
	 *  Manual page for this initializer:
	 *  http://docs.spring.io/spring/docs/3.1.4.RELEASE/javadoc-api/org/springframework/web/WebApplicationInitializer.html
	 */
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		// TODO Auto-generated method stub
		// Create the 'root' Spring application context
	      AnnotationConfigWebApplicationContext rootContext =
	        new AnnotationConfigWebApplicationContext();
	      rootContext.setConfigLocation("classpath:/WEB-INF/applicationContext.xml");
	      //rootContext.register(AppConfig.class); /* May need to actually do this properly */
	      
	      
	      // Manage the lifecycle of the root application context
	      container.addListener(new ContextLoaderListener(rootContext));
	      
	      

	      
	      /*
	      // Create the dispatcher servlet's Spring application context
	      AnnotationConfigWebApplicationContext dispatcherContext =
	        new AnnotationConfigWebApplicationContext();
	      dispatcherContext.register(DispatcherConfig.class);

	      // Register and map the dispatcher servlet
	      ServletRegistration.Dynamic dispatcher =
	        container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
	      dispatcher.setLoadOnStartup(1);
	      dispatcher.addMapping("/");
	      */
	}

}
