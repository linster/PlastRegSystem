package org.plast.reg;


import org.plast.reg.util.NoAuthenticationException;
import org.springframework.security.core.Authentication;

public class AuthenticationHolder {

	public static final ThreadLocal<Authentication> THREAD_GLOBAL = new ThreadLocal<Authentication>();

	public static Authentication getAuthentication() throws NoAuthenticationException {
		if (THREAD_GLOBAL.get() == null ){
			throw new NoAuthenticationException();
		} else {
			return THREAD_GLOBAL.get();
		}
	}
	
	static void setAuthentication(Authentication auth) {
		
		THREAD_GLOBAL.set(auth);
	}
	
	static void clean() {
		
		THREAD_GLOBAL.remove();
	}

}
