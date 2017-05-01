package utils;

import play.Logger;
import play.Play;

import com.marklogic.client.DatabaseClientFactory.Authentication;

/**
 * Basic configuration parameters for establishing a database connection
 * <ul>
 * <li>host</li>
 * <li>port</li>
 * <li>user</li>
 * <li>password</li>
 * <li>authentication type</li>
 * </ul>
 * All parameters are read from Play.configuration properties
 * */


/**
 * @author Ivana Frankic
 *
 */
public class MarkLogicConfig {
	public static String host = Play.configuration.getProperty("marklogic.host");
	public static int port = Integer.parseInt(Play.configuration.getProperty("marklogic.port"));

	public static String database = Play.configuration.getProperty("marklogic.database");
	
	public static String user = Play.configuration.getProperty("marklogic.user");
	public static String password = Play.configuration.getProperty("marklogic.password");

	public static Authentication authType = Authentication.valueOf(Play.configuration.getProperty("marklogic.authentication_type").toUpperCase());
}
