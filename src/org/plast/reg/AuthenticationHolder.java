package org.plast.reg;


import org.plast.reg.util.NoAuthenticationException;
import org.springframework.security.core.Authentication;

public class AuthenticationHolder {

	private static final ThreadLocal<Authentication> THREAD_LOCAL = new ThreadLocal<Authentication>();

	public static Authentication getAuthentication() throws NoAuthenticationException {
		if (THREAD_LOCAL.get() == null ){
			throw new NoAuthenticationException();
		} else {
			return THREAD_LOCAL.get();
		}
	}
	
	static void setAuthentication(Authentication auth) {
		
		THREAD_LOCAL.set(auth);
	}
	
	static void clean() {
		
		THREAD_LOCAL.remove();
	}

}
