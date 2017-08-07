package controllers;

import play.*;
import play.libs.Crypto;
import play.mvc.*;
import utils.DataAccess;
import models.*;

public class Security extends Secure.Security {
	private static Logger log = new Logger();

	/**
	 * @param username
	 * @param password
	 * @return
	 */
	static boolean authenticate(String username, String password) {
		log.debug("Security.authenticate() " + username);
		
		username = username.trim();
		
		boolean result = false;
		UserBean user = DataAccess.findUserByUsername(username);
		if (user == null) {
			flash.error("secure.error");
		} else {
			String encrypted = Crypto.encryptAES(password);
			if (encrypted.equals(user.getPassword())) {
				log.debug("Security.authenticate() " + username + " authenticated");
				result = true;
			} 
		}

		return result;
	}

	/**
	 * @param profile
	 * @return
	 */
	static boolean check(String profile) {
		log.debug("Security.check() " + profile);

		return true;
	}

	/**
	 * @param profile
	 */
	static void onCheckFailed(String profile) {
		log.debug("Security.onCheckFailed() " + profile);

		forbidden("Unauthorized."); 
	}

	/**
	 * @throws Throwable
	 */
	@Before(unless = { "login", "authenticate", "logout", "explorer" })
	static void checkAccess() throws Throwable {
		log.debug("Security.checkAccess() for " + request.url);

		// Authentication
		if (!session.contains("username")) {
			flash.put("url", "GET".equals(request.method) ? request.url : Play.ctxPath + "/"); // seems a good default
			// login();
			ok();
		}
		// Checks
		Check check = getActionAnnotation(Check.class);
		if (check != null) {
			check(check);
		}
		check = getControllerInheritedAnnotation(Check.class);
		if (check != null) {
			check(check);
		}
	}

	/**
	 * @param check
	 * @throws Throwable
	 */
	private static void check(Check check) throws Throwable {
		for (String profile : check.value()) {
			boolean hasProfile = (Boolean) Security.invoke("check", profile);
			if (!hasProfile) {
				Security.invoke("onCheckFailed", profile);
			}
		}
	}

	/**
	 * @return
	 */
	protected static boolean isSignedIn() {
		log.debug("Security.isSignedIn(): " + session.contains("username"));
		return session.contains("username");
	}
	
	/**
	 * @return
	 */
	protected static String getCurrentUsername() {
		log.debug("Security.getCurrentUsername(): " + session.get("username"));
		if (isSignedIn()) {
			return session.get("username");
		} else {
			return null;
		}
	}
}