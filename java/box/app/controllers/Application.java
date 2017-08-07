package controllers;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import models.DestinationBean;
import models.ListBean.ListType;
import models.UserBean;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;
import utils.DataAccess;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @author Ivana Frankic
 * <br><br>
 * Main controller for this application<br>
 * Accepts HTTP requests and form submits from javascript/html<br>
 * Requests are routed based on rules in conf/routes<br>
 */
@With(Secure.class)
public class Application extends Controller {
	private static Logger log = new Logger();

	/**
	 * render index.html
	 * <br>
	 * additional html parameters are rendered: a boolean "if user is signed-in" and a string username of that user 
	 * these parameters further control which parts of html are rendered 
	 */
	public static void index() {
		renderArgs.put("connected", Security.isSignedIn());
		String currentUsername = Security.isSignedIn() ? Security.getCurrentUsername() : "";
		if (!currentUsername.isEmpty()) {
			UserBean user = DataAccess.findUserByUsername(currentUsername);
			if (!user.getFirstname().isEmpty())
				currentUsername = user.getFirstname();
			else
				currentUsername = StringUtils.capitalize(currentUsername);
		}
		renderArgs.put("currentUser", currentUsername);

		log.debug("Application.index() connected = " + Security.isSignedIn());
		render();
	}

	/**
	 * render explorer.html
	 */
	public static void explorer() {
		log.debug("Application.explorer()");
		render();
	}

	/**
	 * render signin.html
	 */
	public static void signin() {
		log.debug("Application.signin()");
		render();
	}

	/**
	 * render resortdetails.html
	 * <br>
	 * additional html parameter is rendered: a boolean "if user is signed-in" 
	 * these parameter further control which parts of html are rendered
	 */
	public static void resortdetails() {
		log.debug("Application.resortdetails()");

		renderArgs.put("connected", Security.isSignedIn());

		render();
	}

	/**
	 * render userdetails.html
	 * <br>
	 * additional html parameters are rendered: a boolean "if user is signed-in" and a string username of that user 
	 * these parameters further control which parts of html are rendered 
	 */
	public static void userdetails() {
		log.debug("Application.userdetails()");

		renderArgs.put("connected", Security.isSignedIn());
		renderArgs.put("currentUser", (Security.isSignedIn() ? Security.getCurrentUsername() : ""));

		render();
	}

	/**
	 * render users.html
	 * <br>
	 * additional html parameters are rendered: a boolean "if user is signed-in" and a string username of that user 
	 * these parameters further control which parts of html are rendered 
	 */
	public static void users() {
		log.debug("Application.users()");

		renderArgs.put("connected", Security.isSignedIn());
		renderArgs.put("currentUser", (Security.isSignedIn() ? Security.getCurrentUsername() : ""));

		render();
	}

	/**
	 * render register.html
	 */
	public static void register() {
		log.debug("Application.register()");
		render();
	}

	/**
	 * render newdestination.html
	 */
	public static void newdestination() {
		log.debug("Application.newdestination()");
		render();
	}
	
	/**
	 * Form submit from register.html 
	 * <br>
	 * @param username {@link String} user input
	 * @param password {@link String} user input
	 * @param firstname {@link String} user input
	 * @param lastname {@link String} user input
	 * @param avatar {@link File} object, user upload
	 * @return {@link String} "success" or "error"
	 */
	public static String createUser(@Required String username, @Required String password, String firstname, String lastname, File avatar) {
		log.debug("Application.createUser() " + username);

		String result = "error";
		username = username.trim();
		password = password.trim();
		firstname = firstname.trim();
		lastname = lastname.trim();

		if (DataAccess.findUserByUsername(username) != null) {
			log.debug("Application.createUser() " + username + " already exists");
		} else {
			if (DataAccess.createUser(username, password, firstname, lastname, avatar)) {
				log.debug("Application.createUser() " + username + " created!");
				result = "success";
			}
		}
		return result;
	}

	/**
	 * Form submit from edit user flow
	 * <br>
	 * @param userid {@link String} hidden item on html, represents user
	 * @param password {@link String} user input
	 * @param firstname {@link String} user input
	 * @param lastname {@link String} user input
	 * @param avatar {@link File} object, user upload
	 * @return {@link String} "success" or "error"
	 */
	public static String updateUser(String userid, @Required String password, String firstname, String lastname, File avatar) {
		log.debug("Application.updateUser() " + userid);

		String result = "error";

		int id = new Integer(userid).intValue();

		password = password.trim();
		firstname = firstname.trim();
		lastname = lastname.trim();

		UserBean user = DataAccess.findUserById(id);
		if (user == null) {
			log.debug("Application.updateUser() " + userid + " does not exist");
		} else if (!user.getUsername().equalsIgnoreCase(Security.getCurrentUsername())) {
			log.debug("Application.updateUser() - you can only edit your own account");
		} else if (DataAccess.updateUser(id, password, firstname, lastname, avatar)) {
			log.debug("Application.updateUser() " + userid + " created!");
			result = "success";
		}

		return result;
	}

	/** Form submit from newdstination.html
	 * <br>
	 * @param name {@link String} user input
	 * @param country {@link String} user input
	 * @param website {@link String} user input
	 * @param logo {@link File} object, user upload
	 * @param background {@link File} object, user upload
	 * @param description {@link String} user input
	 * @param type {@link String} user input
	 * @param altitude {@link String} user input
	 * @param slopes {@link String} user input
	 * @param difficult {@link String} user input
	 * @param medium {@link String} user input
	 * @param easy {@link String} user input
	 * @param freeride {@link String} user input
	 * @return {@link String} "success" or "error"
	 */
	public static String createDestination(@Required String name, String country, String website, File logo, File background, String description, String type,
			String altitude, String slopes, String difficult, String medium, String easy, String freeride) {
		log.debug("Application.createDestination() " + name);

		String result = "error";

		name = name.trim();
		country = country.trim();
		website = website.trim();
		description = description.trim();

		altitude = altitude.trim();
		slopes = slopes.trim();
		difficult = difficult.trim();
		medium = medium.trim();
		easy = easy.trim();
		freeride = freeride.trim();

		// TODO - really check for duplicates; similar names for example
		if (DataAccess.findDestination(name, country) != null) {
			log.debug("Application.createDestination() " + name + "/" + country + " already exists");
		} else {
			if (DataAccess.createDestination(name, country, website, logo, background, description, type, altitude, slopes, difficult, medium, easy, freeride)) {
				log.debug("Application.createDestination() " + name + "/" + country + " created!");
				result = "success";
			}
		}

		return result;
	}

	/** Form submit from edit destination
	 * <br>
	 * @param destinationid {@link String} hidden item on html, represents destination
	 * @param type {@link String} user input
	 * @param background {@link File} object, user upload
	 * @param name {@link String} user input
	 * @param country {@link String} user input
	 * @param altitude {@link String} user input
	 * @param slopes {@link String} user input
	 * @param difficult {@link String} user input
	 * @param medium {@link String} user input
	 * @param easy {@link String} user input
	 * @param freeride {@link String} user input
	 * @param website {@link String} user input
	 * @param description {@link String} user input
	 * @param logo {@link File} object, user upload
	 * @return {@link String} "success" or "error"
	 */
	public static String updateDestination( String destinationid, String type, File background, @Required String name, String country, String altitude,
											String slopes, String difficult, String medium, String easy, String freeride, String website, String description, File logo) {
		
		log.debug("Application.updateDestination() " + name);

		String result = "error";

		int id = new Integer(destinationid).intValue();
		
		name = name.trim();
		country = country.trim();
		website = website.trim();
		description = description.trim();

		altitude = altitude.trim();
		slopes = slopes.trim();
		difficult = difficult.trim();
		medium = medium.trim();
		easy = easy.trim();
		freeride = freeride.trim();

		// FIXME - findById
		if (DataAccess.findDestinationById(id) == null) {
			log.error("Application.updateDestination() " + id + " does not exist!");
		} else {
			if (DataAccess.updateDestination(id, type, name, country, website, description, logo, background, altitude, slopes, difficult, medium, easy, freeride)) {
				log.debug("Application.updateDestination() " + id + "/" + name + "/" + country + " updated!");
				result = "success";
			}
		}

		return result;
	}

	/**
	 * Currently signed-in user clicks on "like/unlike" or "save/usave from wishlist" 
	 * <br>
	 * @param destination {@link String} destination id user is working on
	 * @param listtype	{@link String} like or wishlist
	 * @param add		{@link String} add or remove from list
	 */
	public static void addToList(String destination, String listtype, String add) {
		log.debug("Application.addToList(): " + destination + "/" + listtype + "/" + add);

		destination = destination.trim();
		listtype = listtype.trim();

		DataAccess.modifyList(Security.getCurrentUsername(), destination, listtype, add.equalsIgnoreCase("true"));

		ok();
	}

	/**
	 * return a json representation of all {@link DestinationBean} objects read from database
	 */
	public static void getAllDestinations() {
		log.debug("Application.getAllDestinations()");

		Gson gson = new Gson();
		String result = gson.toJson(DataAccess.getAllDestinations());

		renderText(result);
	}

	/**
	 * renders a json representation of all {@link UserBean} objects read from the database
	 */
	public static void getAllUserData() {
		log.debug("Application.getAllUserData()");

		Gson gson = new Gson();
		String result = gson.toJson(DataAccess.getAllUserData());

		renderText(result);
	}

	/** 
	 * @param input user input in the search box
	 * @param type	user input, type filter selection
	 * @param field user input, field filter selection
	 * <br>
	 * renders a json representation of all {@link DestinationBean} objects read from the database
	 */
	public static void searchDestinations(String input, String type, String field) {
		log.debug("Application.searchDestinations(): input=" + input + "/" + type + "/" + field);

		Gson gson = new Gson();
		String result = ""; 
		if (input.trim().isEmpty() && type.equalsIgnoreCase("all") && field.equalsIgnoreCase("all"))
			result = gson.toJson(DataAccess.getAllDestinations());
		else 
			result = gson.toJson(DataAccess.findDestinations(input, type, field));

		renderText(result);
	}

	/**
	 * @param destination currently displayed destination
	 * <br>
	 * renders a json representation of all {@link UserBean} objects read from the database
	 */
	public static void findWhoLikesDestination(String destination) {
		log.debug("Application.findWhoLikesDestination()");

		Gson gson = new Gson();
		List<UserBean> likers = DataAccess.findWhoLikesDestination(destination);
		String result = gson.toJson(likers);

		renderText(result);
	}
	
	/**
	 * @param destination currently displayed destination
	 * <br>
	 * renders a json representation of all {@link UserBean} objects read from the database
	 */
	public static void findWhoSavedDestination(String destination) {
		log.debug("Application.findWhoSavedDestination()");

		Gson gson = new Gson();
		List<UserBean> likers = DataAccess.findWhoSavedDestination(destination);
		String result = gson.toJson(likers);

		renderText(result);
	}

	/**
	 * @param destination currently displayed destination
	 * <br>
	 * renders a json representation of all {@link DestinationBean} objects read from the database
	 */
	public static void findSimilarByLikes(String destination) {
		log.debug("Application.findSimilarByLikes() for " + destination);

		Gson gson = new Gson();
		String result = gson.toJson(DataAccess.findSimilarByLikes(destination));

		renderText(result);
	}

	/**
	 * @param destination currently displayed destination
	 * <br>
	 * renders a json representation of all {@link DestinationBean} objects read from the database
	 */
	public static void findSimilarByWishlist(String destination) {
		log.debug("Application.findSimilarByWishlist() for " + destination);

		Gson gson = new Gson();
		String result = gson.toJson(DataAccess.findSimilarByWishlist(destination));

		renderText(result);
	}

	
	/**
	 * @param userid currently displayed user
	 * 
	 * renders a json representation of all {@link DestinationBean} objects read from the database
	 */
	public static void findWhatUserLikes(String userid) {
		log.debug("Application.findWhatUserLikes() for " + userid);

		Gson gson = new Gson();
		String result = gson.toJson(DataAccess.findWhatUserLikes(userid));

		renderText(result);
	}

	/**
	 * @param userid currently displayed user
	 * 
	 * renders a json representation of all {@link DestinationBean} objects read from the database
	 */
	public static void findWhatUserWishes(String userid) {
		log.debug("Application.findWhatUserWishes() for " + userid);

		Gson gson = new Gson();
		String result = gson.toJson(DataAccess.findWhatUserWhishes(userid));

		renderText(result);
	}
	
	/**
	 * @param destinationId currently displayed destination
	 * 
	 * renders a json object true/false on whether the currently signed-in user liked and saved a destination
	 */
	public static void doILikeIt(String destinationId) {
		log.debug("Application.doILikeIt() for " + destinationId);

		JsonObject result = new JsonObject();
		String currentUsername = Security.getCurrentUsername();
		if (currentUsername != null) {
			result.addProperty("liked", DataAccess.doesUserLikeDestination(currentUsername, destinationId, ListType.LIKES));
			result.addProperty("saved", DataAccess.doesUserLikeDestination(currentUsername, destinationId, ListType.WISHLIST));
		}
		renderText(result);
	}
}