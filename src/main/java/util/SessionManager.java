package util;

import java.util.Locale;

import model.User;

public class SessionManager {
	
	// singleton for session manager since we only really need 1 user logged in
	private static SessionManager instance;
	
	private User userLoggedIn;
    private Locale currentLocale = Locale.ENGLISH;
	
	private SessionManager() {}
	
	public static SessionManager getInstance() {
		if (instance==null) {
			instance = new SessionManager();
			return instance;
		} else {
			return instance;
		}
	}

	public User getUserLoggedIn() {
		return userLoggedIn;
	}

	public void setUserLoggedIn(User userLoggedIn) {
		this.userLoggedIn = userLoggedIn;
	}

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(Locale locale) {
        this.currentLocale = locale;
    }
}
