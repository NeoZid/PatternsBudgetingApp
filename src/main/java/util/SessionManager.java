package util;

import java.util.Locale;

import model.User;

public class SessionManager {
	
	// singleton for session manager since we only really need 1 user logged in
    /**
     * Singleton utility class for managing the current user session.
     * Stores the logged-in user and the current application locale.
     */
	private static SessionManager instance;
	
	private User userLoggedIn;
    private Locale currentLocale = Locale.ENGLISH;
	
	private SessionManager() {}

    /**
     * Returns the singleton instance of SessionManager.
     * @return the single SessionManager instance
     */
	public static SessionManager getInstance() {
		if (instance==null) {
			instance = new SessionManager();
			return instance;
		} else {
			return instance;
		}
	}

    /**
     * Returns the currently logged-in user.
     * @return the logged-in user
     */
	public User getUserLoggedIn() {
		return userLoggedIn;
	}

    /**
     * Sets the currently logged-in user.
     * @param userLoggedIn the user to set as logged in
     */
	public void setUserLoggedIn(User userLoggedIn) {
		this.userLoggedIn = userLoggedIn;
	}

    /**
     * Returns the current application locale.
     * @return the current locale
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }

    /**
     * Sets the current application locale.
     * @param locale the new locale
     */
    public void setCurrentLocale(Locale locale) {
        this.currentLocale = locale;
    }
}
