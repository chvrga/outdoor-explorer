package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import models.DestinationBean;
import models.ListBean.ListType;
import models.ListBean;
import models.SkiResortBean;
import models.UserBean;
import models.DestinationBean.DestinationType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.Play;
import play.libs.Crypto;

import com.google.gson.JsonElement;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.io.Format;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.pojo.PojoRepository;

/**
 * @author Ivana Frankic
 *
 */
public class DataAccess {
	private static Logger log = new Logger();
	
	private static DBType type = DBType.MARKLOGIC;
	
	/**
	 * @return an instance of DbInterface depending on the type defined 
	 */
	public static DbInterface getDbInterface() {
		DbInterface iface = null;
		switch (type) {
		case MARKLOGIC:
			iface = new MarkLogicAccess();
			break;

		default:
			break;
		}
		return iface;
	}

	
	/**
	 * @return a list of all {@link DestinationBean} objects read from the database
	 */
	public static List<DestinationBean> getAllDestinations() {
		log.debug("DataAccess.getAllDestinations()");

		List<DestinationBean> result = new ArrayList<DestinationBean>();
		result = getDbInterface().findAllDestinations();

		return result;
	}

	/**
	 * @return a list of all {@link UserBean} objects read from the database
	 */
	public static List<UserBean> getAllUserData() {
		log.debug("DataAccess.getUserData()");

		List<UserBean> result = new ArrayList<UserBean>();
		result = getDbInterface().findAllUsers();

		return result;
	}
	
	/**
	 * @param input user input in the search box
	 * @param type	user input, type filter selection
	 * @param field user input, field filter selection
	 * <br>
	 * 
	 * @return a list of all {@link DestinationBean} objects read from the database matching search parameters
	 */
	public static List<DestinationBean> findDestinations(String input, String type, String field) {
		log.debug("DataAccess.findDestinations(): input=" + input + "/" + type + "/" + field);

		if (type == null || type.isEmpty()) type = "all"; else type = StringUtils.upperCase(type);
		if (field == null || field.isEmpty()) field = "all";
		
		List<DestinationBean> result = new ArrayList<DestinationBean>();
		result = getDbInterface().findDestinations(input, type, field);

		return result;
	}
	
	/**
	 * @param id user id to search for
	 * @return	{@link UserBean} representing the user; null if not found
	 */
	public static UserBean findUserById(int id) {
		log.debug("DataAccess.findUserById(): id=" + id);

		UserBean result = getDbInterface().findUserById(id);

		return result;
	}
	
	/**
	 * @param input username to serach for 
	 * @return {@link UserBean} representing the user; null if not found
	 */
	public static UserBean findUserByUsername(String input) {
		log.debug("DataAccess.findUserByUsername(): input=" + input);

		UserBean result = getDbInterface().findUserByUsername(input);
		// TODO: null check! 
		return result;
	}

	/**
	 * @param username {@link String} user input
	 * @param password {@link String} user input
	 * @param firstname {@link String} user input
	 * @param lastname {@link String} user input
	 * @param avatar {@link File} object, user upload
	 * @return success/failure flag 
	 */
	public static boolean createUser(String username, String password, String firstname, String lastname, File avatar) {
		log.debug("DataAccess.createUser()"); 
		boolean result = false;

		SecureRandom sr = new SecureRandom();
		int id = sr.nextInt();
		
		if (getDbInterface().findUserById(id) != null) {
			id = sr.nextInt();
		}
		
		String filename = avatar.getName(); 
		File destination = new File(Play.applicationPath.getAbsolutePath() + File.separator + 
									UserBean.avatarPathPrefix + filename);
		
		String projectRoot = Play.applicationPath.getAbsolutePath();

		log.debug("DataAccess.createUser():" + "procejtRoot " + projectRoot);
		log.debug("DataAccess.createUser():" + "filename " + filename);
		log.debug("DataAccess.createUser():" + "destination " + destination.getAbsolutePath());
		
		try {
			FileUtils.copyFile(avatar, destination);
		} catch (IOException e) {
			log.error(e, "DataAccess.createUser(): Failed to upload avatar.");
		}

		UserBean user = new UserBean(username, password, firstname, lastname, filename);
		result = getDbInterface().createUser(user);
		
		return result; 
	}
	

	/**
	 * @param id represents user we are updating
	 * @param password {@link String} user input
	 * @param firstname {@link String} user input
	 * @param lastname {@link String} user input
	 * @param avatar {@link File} object, user upload
	 * @return success/failure flag 
	 */
	public static boolean updateUser(int id, String password, String firstname, String lastname, File avatar) {
		log.debug("DataAccess.updateUser()"); 
		boolean result = false;

		String filename = "";
		if (avatar != null) {
			filename = avatar.getName(); 
			log.debug("DataAccess.updateUser() new avatar filename = " + filename); 

			File destination = new File(Play.applicationPath.getAbsolutePath() + File.separator + 
										UserBean.avatarPathPrefix + filename);
			try {
				FileUtils.copyFile(avatar, destination);
			} catch (IOException e) {
				log.error(e, "DataAccess.updateUser(): Failed to upload avatar.");
			}
		} else {
			log.debug("DataAccess.updateUser() new avatar filename = " + filename); 
		}

		result = getDbInterface().updateUser(id, password, firstname, lastname, filename);
		
		return result;
	}

	/**
	 * @param currentUsername {@link String} user being added to a list
	 * @param destination {@link String} destination id user is working on
	 * @param listtype	{@link String} like or wishlist
	 * @param addToList {@link String} add or remove from list
	 */
	public static void modifyList(String currentUsername, String destination, String listtype, boolean addToList) {
		getDbInterface().modifyList(currentUsername, destination, StringUtils.upperCase(listtype), addToList);
	}
	
	/**
	 * @param destination {@link String} destination id to search for
	 * @return list of {@link UserBean} objects who have liked this destination
	 */
	public static List<UserBean> findWhoLikesDestination(String destination) {
		return getDbInterface().findWhoSavedDestination(destination, ListType.LIKES);
	}
	
	/**
	 * @param destination {@link String} destination id to search for
	 * @return list of {@link UserBean} objects who have saved this destination to their wishlist
	 */
	public static List<UserBean> findWhoSavedDestination(String destination) {
		return getDbInterface().findWhoSavedDestination(destination, ListType.WISHLIST);
	}
	
	/**
	 * @param destination {@link String} destination id to search for
	 * @return a list of {@link DestinationBean} objects liked by the same users as the given destination
	 */
	public static List<DestinationBean> findSimilarByLikes(String destination) {
		return getDbInterface().findSimilarByListType(destination, ListType.LIKES);
	}
	
	/**
	 * @param destination {@link String} destination id to search for
	 * @return a list of {@link DestinationBean} objects "wished" by the same users as the given destination
	 */
	public static List<DestinationBean> findSimilarByWishlist(String destination) {
		return getDbInterface().findSimilarByListType(destination, ListType.WISHLIST);
	}
	
	/**
	 * @param userid {@link String} user id 
	 * @return a list of {@link DestinationBean} objects liked by the given user id
	 */
	public static List<DestinationBean> findWhatUserLikes(String userid) {
		return getDbInterface().findWhatUserSaved(userid, ListType.LIKES);
	}
	
	/**
	 * @param userid {@link String} user id 
	 * @return a list of {@link DestinationBean} objects "wished" by the given user id
	 */
	public static List<DestinationBean> findWhatUserWhishes(String userid) {
		return getDbInterface().findWhatUserSaved(userid, ListType.WISHLIST);
	}
	
	/**
	 * @param id destination id to search for
	 * @return {@link DestinationBean} from the database with the given id 
	 */
	public static DestinationBean findDestinationById(int id) {
		return getDbInterface().findDestinationById(id);

	}
	
	/**
	 * @param name {@link String} destination name
	 * @param country {@link String} destination country
	 * @return	{@link DestinationBean} from the database with the given name and country
	 */
	public static DestinationBean findDestination(String name, String country) {
		return getDbInterface().findDestination(name, country);
	}
	
	/**
	 * @param username {@link String} username to search for
	 * @param destinationid {@link String} destination id to search for
	 * @param type {@link ListType} like or wish
	 * @return true/false - {@link ListBean} of the given type for the destination contains given username
	 */
	public static boolean doesUserLikeDestination(String username, String destinationid, ListType type) {
		return getDbInterface().doesUserLikeDestination(username, destinationid, type);
	}

	/**
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
	 * @return success/failure result
	 */
	public static boolean createDestination(String name, String country, String website, 
											File logo, File background, String description, String type,
											String altitude, String slopes, String difficult, String medium, String easy, String freeride) {
		
		log.debug("DataAccess.createDestination()"); 
		boolean result = false;
		
		
		DestinationType dstType = DestinationType.fromString(type);
		if (dstType == DestinationType.UNKNOWN) {
			log.error("DataAccess.createDestination() unknown destination type"); 
			return false;
		}

		int dstAltitude = 0, dstSlopes = 0, dstDifficult = 0, dstMedium = 0, dstEasy = 0, dstFree = 0;
		if (dstType == DestinationType.SKI) {
			try {
				dstAltitude = new Integer(altitude.trim()).intValue();
				dstSlopes = new Integer(slopes.trim()).intValue();
				dstDifficult = new Integer(difficult.trim()).intValue();
				dstMedium = new Integer(medium.trim()).intValue();
				dstEasy = new Integer(easy.trim()).intValue();
				dstFree = new Integer(freeride.trim()).intValue();
			} catch (Exception e) {
				log.error(e, "DataAccess.createDestination() exception parsing integers"); 
				return false;
			}
		}

		SecureRandom sr = new SecureRandom();
		int id = sr.nextInt();
		
		if (getDbInterface().findDestinationById(id) != null) {
			id = sr.nextInt();
		}
		
		String projectRoot = Play.applicationPath.getAbsolutePath();
		log.debug("DataAccess.createDestination():" + "procejtRoot " + projectRoot);
		
		String logo_filename = "default.png";
		String bgr_filename = "default.png";
		
		if (logo != null) {
			logo_filename = logo.getName(); 
			File logo_destination = new File(Play.applicationPath.getAbsolutePath() + 
											 File.separator +
											 DestinationBean.logoPathPrefix + logo_filename);
			
			log.debug("DataAccess.createDestination():" + "logo_filename " + logo_filename);
			log.debug("DataAccess.createDestination():" + "logo_destination " + logo_destination.getAbsolutePath());
			try {
				FileUtils.copyFile(logo, logo_destination);
			} catch (IOException e) {
				log.error(e, "DataAccess.createDestination(): Failed to upload image.");
			}


		} else {
			log.warn("DataAccess.createDestination(): logo file is null; using default.png");
		}
		if (background != null) {
			bgr_filename = background.getName(); 
			File bgr_destination = new File(Play.applicationPath.getAbsolutePath() + 
											File.separator +
											DestinationBean.backgroundPathPrefix + bgr_filename);
			
			log.debug("DataAccess.createDestination():" + "bgr_filename " + bgr_filename);
			log.debug("DataAccess.createDestination():" + "bgr_destination " + bgr_destination.getAbsolutePath());
			try {
				FileUtils.copyFile(background, bgr_destination);
			} catch (IOException e) {
				log.error(e, "DataAccess.createDestination(): Failed to upload image.");
			}

		} else {
			log.warn("DataAccess.createDestination(): background file is null; using default.png");
		}
		
		if (dstType == DestinationType.SKI) {
			DestinationBean dst = new SkiResortBean(name, country, description, website, logo_filename, bgr_filename, dstAltitude, dstSlopes, dstDifficult, dstMedium, dstEasy, dstFree);
			result = getDbInterface().createDestination(dst);
			
		} else {
			DestinationBean dst = new DestinationBean(dstType, name, country, description, website, logo_filename, bgr_filename);
			result = getDbInterface().createDestination(dst);
		}
		
		return result; 
	}
	
	/**
	 * @param id represents destination we are updating
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
	 * @return success/failure result
	 */
	public static boolean updateDestination(int id, String type, String name, String country, String website, String description, File logo, File background,
											String altitude, String slopes, String difficult, String medium, String easy, String freeride) {

		boolean result = false;
		
		DestinationType dstType = DestinationType.fromString(type);
		if (dstType == DestinationType.UNKNOWN) {
			log.error("DataAccess.createDestination() unknown destination type"); 
			return false;
		}
		
		int dstAltitude = 0, dstSlopes = 0, dstDifficult = 0, dstMedium = 0, dstEasy = 0, dstFree = 0;
		if (dstType == DestinationType.SKI) {
			try {
				dstAltitude	 	= altitude.trim().isEmpty() ? -1 : new Integer(altitude.trim()).intValue();
				dstSlopes 		= slopes.trim().isEmpty() ? -1 : new Integer(slopes.trim()).intValue();
				dstDifficult 	= difficult.trim().isEmpty() ? -1 : new Integer(difficult.trim()).intValue();
				dstMedium 		= medium.trim().isEmpty() ? -1 : new Integer(medium.trim()).intValue();
				dstEasy 		= easy.trim().isEmpty() ? -1 : new Integer(easy.trim()).intValue();
				dstFree 		= freeride.trim().isEmpty() ? -1 : new Integer(freeride.trim()).intValue();
			} catch (Exception e) {
				log.error(e, "DataAccess.createDestination() exception parsing integers"); 
				return false;
			}
		}
		
		String logo_filename = "";
		if (logo != null) {
			logo_filename = logo.getName(); 
			log.debug("DataAccess.updateUser() new avatar filename = " + logo_filename); 

			File destination = new File(Play.applicationPath.getAbsolutePath() + File.separator + 
										DestinationBean.logoPathPrefix + logo_filename);
			try {
				FileUtils.copyFile(logo, destination);
			} catch (IOException e) {
				log.error(e, "DataAccess.updateUser(): Failed to upload logo.");
			}
		} else {
			log.debug("DataAccess.updateUser() new logo filename = " + logo_filename); 
		}
		
		String bg_filename = "";
		if (background != null) {
			bg_filename = background.getName(); 
			log.debug("DataAccess.updateUser() new avatar filename = " + bg_filename); 

			File destination = new File(Play.applicationPath.getAbsolutePath() + File.separator + 
										DestinationBean.backgroundPathPrefix + bg_filename);
			try {
				FileUtils.copyFile(background, destination);
			} catch (IOException e) {
				log.error(e, "DataAccess.updateUser(): Failed to upload background.");
			}
		} else {
			log.debug("DataAccess.updateUser() new logo filename = " + bg_filename); 
		}

		result = getDbInterface().updateDestination(id, dstType, name, country, website, description, logo_filename, bg_filename, 
													dstAltitude, dstSlopes, dstDifficult, dstMedium, dstEasy, dstFree);

		return result;
	}
}
