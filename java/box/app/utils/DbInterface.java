package utils;

import java.io.File;
import java.util.List;

import models.DestinationBean;
import models.DestinationBean.DestinationType;
import models.ListBean.ListType;
import models.UserBean;

/**
 * @author Ivana Frankic
 *
 */
public interface DbInterface {
	/**
	 * @return Returns a list of all {@link UserBean} objects from the database
	 */
	List<UserBean> 			findAllUsers();
	
	/**
	 * @return Returns a list of all {@link DestinationBean} objects from the database
	 */
	List<DestinationBean> 	findAllDestinations();
	
	/**
	 * @param input User input string from the search box
	 * @param type	User selection for the type filter - options are defined in {@link DestinationType}
	 * @param field User selection for the field filter - options are name, country, description
	 * @return  Returns a list of matching {@link DestinationBean} objects from the database
	 */
	List<DestinationBean> 	findDestinations(String input, String type, String field);

	/**
	 * @param id	userId to search for
	 * @return		{@link UserBean} object matching given id
	 */
	UserBean 				findUserById(int id);
	
	/**
	 * @param input	{@link String} username to search for
	 * @return		{@link UserBean} object matching given username
	 */
	UserBean 				findUserByUsername(String input);

	/**
	 * @param user	{@link UserBean} object to insert into database
	 * @return		success/failure status 
	 */
	boolean 				createUser(UserBean user);
	
	/**
	 * @param id		id of the user we are modifying (this is non-modifiable by user and links to existing stored object)
	 * @param password	{@link String} user input
	 * @param firstname {@link String} user input
	 * @param lastname	{@link String} user input
	 * @param avatar	{@link String} filename extracted from user input
	 * @return			success/failure status
	 */
	boolean 				updateUser(int id, String password, String firstname, String lastname, String avatar);

	/**
	 * @param dst		{@link DestinationBean} object to insert into database
	 * @return			success/failure status
	 */
	boolean 				createDestination(DestinationBean dst);
	/**
	 * @param id			id of the destination we are modifying (this is non-modifiable by user and links to existing stored object)
	 * @param dstType		non-modifiable by user
	 * @param name			{@link String} user input
	 * @param country		{@link String} user input
	 * @param website		{@link String} user input
	 * @param description	{@link String} user input
	 * @param logo_filename	{@link String} filename extracted from user input
	 * @param bg_filename	{@link String} filename extracted from user input
	 * @param dstAltitude	{@link String} user input
	 * @param dstSlopes		{@link String} user input	
	 * @param dstDifficult	{@link String} user input
	 * @param dstMedium		{@link String} user input	
	 * @param dstEasy		{@link String} user input
	 * @param dstFree		{@link String} user input
	 * @return				success/failure status
	 */
	boolean 				updateDestination(int id, DestinationType dstType, String name, String country, String website, String description, String logo_filename,
											  String bg_filename, int dstAltitude, int dstSlopes, int dstDifficult, int dstMedium, int dstEasy, int dstFree);

	/**
	 * @param destination	{@link String} destination Id of the currently displayed {@link DestinationBean}
	 * @param type			{@link ListType} type of the list
	 * @return				a list of {@link UserBean} objects who saved the given destination in the provided {@link ListType}
	 */
	List<UserBean> 			findWhoSavedDestination(String destination, ListType type);

	/**
	 * @param destination	{@link String} destination Id of the currently displayed {@link DestinationBean}
	 * @param type			{@link ListType} type of the list
	 * @return				a list of {@link DestinationBean} objects that has shared users with the given destination in the same list type
	 */
	List<DestinationBean> 	findSimilarByListType(String destination, ListType type);
	
	/**
	 * @param userid		{@link String} user Id we are interested in
	 * @param type			{@link ListType} in which we are searching for this user
	 * @return				a list of {@link DestinationBean} objects saved by this user in the given list type
	 */
	List<DestinationBean> 	findWhatUserSaved(String userid, ListType type);

	/**
	 * @param name			{@link String} destination name
	 * @param country		{@link String} destination country
	 * @return				{@link DestinationBean} matching name/country pair
	 */	
	DestinationBean 		findDestination(String name, String country);
	
	/**
	 * @param id			destination id to search for
	 * @return				{@link DestinationBean} matching given id
	 */
	DestinationBean 		findDestinationById(int id);
	
	/**
	 * @param username		{@link String} a specific user's username
	 * @param destinationid {@link String} destination id we are interested in 
	 * @param type			{@link String} list type we are interested in 
	 * @return				true/false answer - did the given user save this destination in the specified list type
	 */
	boolean 				doesUserLikeDestination(String username, String destinationid, ListType type);

	/**
	 * @param currentUsername {@link String} username we are about to add to a list
	 * @param destination	  {@link String} destination id who's list we are about to modify
	 * @param listtype		  {@link String} represents the list type for {@link ListType} items		
	 * @param addToList		  true = add to list / false = remove from list
	 */
	void 					modifyList(String currentUsername, String destination, String listtype, boolean addToList);
	
	/**
	 * read from {@link InitialData} and write all to database
	 */
	void 					dbInit();
	
	/**
	 * remove all objects you know about from the database
	 */
	void 					dbTeardown();
}
