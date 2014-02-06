package org.plast.reg;


import org.springframework.security.core.Authentication;

public class AuthenticationHolder {

	private static final ThreadLocal<Authentication> THREAD_LOCAL = new ThreadLocal<Authentication>();

	public static Authentication getAuthentication() {
		
		return THREAD_LOCAL.get();
	}
	
	static void setAuthentication(Authentication auth) {
		
		THREAD_LOCAL.set(auth);
	}
	
	static void clean() {
		
		THREAD_LOCAL.remove();
	}

}
