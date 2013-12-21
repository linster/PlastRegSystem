package org.plast.reg.events;

public class LoginEvent {
	/**This LoginEvent is one of the two events possible on the Authentication EventBus.
	 * This one is adapted from:
	 * https://github.com/nfrankel/More-Vaadin/blob/master/springsecurity-integration/src/main/java/com/morevaadin/vaadin7/springsecurity/event/LoginEvent.java
	 * 
	 * 
	 */
	
	
	private final String login;

    private final String password;

    public LoginEvent(String login, String password) {

            this.login = login;
            this.password = password;
    }

    public String getLogin() {
            return login;
    }

    public String getPassword() {
            return password;
    }
}
