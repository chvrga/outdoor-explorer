package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import models.DestinationBean;
import models.DestinationBean.DestinationType;
import models.ListBean;
import models.ListBean.ListType;
import models.SkiResortBean;
import models.UserBean;
import play.Logger;
import play.libs.Crypto;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.pojo.PojoPage;
import com.marklogic.client.pojo.PojoQueryBuilder;
import com.marklogic.client.pojo.PojoQueryBuilder.Operator;
import com.marklogic.client.pojo.PojoQueryDefinition;
import com.marklogic.client.pojo.PojoRepository;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;

/**
 * @author Ivana Frankic
 *
 */
public class MarkLogicAccess implements DbInterface {
	private static Logger log = new Logger();
	
	/* (non-Javadoc)
	 * @see utils.DbInterface#dbInit()
	 */
	public void dbInit() {
		log.debug("PandoraSearch.dbInit():");

		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);
		
		// create a POJO repository associated with ResortBean
		PojoRepository resortBeanRepo = client.newPojoRepository(DestinationBean.class, Integer.class);

		// create a POJO repository associated with UserBean
		PojoRepository userBeanRepo = client.newPojoRepository(UserBean.class, String.class);

		// create a POJO repository associated with ListBean
		PojoRepository listBeanRepo = client.newPojoRepository(ListBean.class, Integer.class);

		for (DestinationBean bean : InitialData.destinations) {
			try {
				resortBeanRepo.write(bean);
			} catch (Exception e) {
				log.error(e, "PandoraSearch.dbInit(): exception on resort " + bean.getName());
			}
		}
		for (UserBean bean : InitialData.users) {
			try {
				userBeanRepo.write(bean);
			} catch (Exception e) {
				log.error(e, "PandoraSearch.dbInit(): exception on user " + bean.getUsername());
			}
		}
		for (ListBean bean : InitialData.lists) {
			try {
				listBeanRepo.write(bean);
			} catch (Exception e) {
				log.error(e, "PandoraSearch.dbInit(): exception on list " + bean.getId());
			}
		}

		client.release();
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#dbTeardown()
	 */
	public void dbTeardown() {
		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);
		// create a POJO repository associated with ResortBean
		PojoRepository resortBeanRepo = client.newPojoRepository(DestinationBean.class, Integer.class);

		// create a POJO repository associated with UserBean
		PojoRepository userBeanRepo = client.newPojoRepository(UserBean.class, String.class);

		// create a POJO repository associated with ListBean
		PojoRepository listBeanRepo = client.newPojoRepository(ListBean.class, Integer.class);

		log.debug("PandoraSearch.dbTeardown():");
		resortBeanRepo.deleteAll();
		userBeanRepo.deleteAll();
		listBeanRepo.deleteAll();

		client.release();
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#findAllDestinations()
	 */
	public List<DestinationBean> findAllDestinations() {
		List<DestinationBean> results = new ArrayList<DestinationBean>();

		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);
		// create a POJO repository associated with ResortBean
		PojoRepository resortBeanRepo = client.newPojoRepository(DestinationBean.class, Integer.class);

		resortBeanRepo.setPageLength(10);
		PojoPage<DestinationBean> matches;
		int start = 1;
		do {
			matches = resortBeanRepo.readAll(start);

			System.out.println("Results " + start + " thru " + (start + matches.size() - 1));

			if (matches.hasContent()) {
				while (matches.hasNext()) {
					DestinationBean bean = matches.next();
					log.debug("PandoraSearch.findAllDestinations(): found: " + bean.getId() + "/" + bean.getName());
					results.add(bean);
				}
			}

			start += matches.size();
		} while (matches.hasNextPage());

		client.release();

		return results;
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#findAllUsers()
	 */
	public List<UserBean> findAllUsers() {
		List<UserBean> results = new ArrayList<UserBean>();

		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);
		// create a POJO repository associated with UserBean
		PojoRepository userBeanRepo = client.newPojoRepository(UserBean.class, String.class);

		userBeanRepo.setPageLength(10);
		PojoPage<UserBean> matches;
		int start = 1;
		do {
			matches = userBeanRepo.readAll(start);

			System.out.println("Results " + start + " thru " + (start + matches.size() - 1));

			if (matches.hasContent()) {
				while (matches.hasNext()) {
					UserBean bean = matches.next();
					log.debug("PandoraSearch.findAllUsers(): found: " + bean.toString());
					results.add(bean);
				}
			}

			start += matches.size();
		} while (matches.hasNextPage());

		client.release();

		return results;
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#findDestinations(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<DestinationBean> findDestinations(String input, String type, String field) {
		List<DestinationBean> results = new ArrayList<DestinationBean>();

		log.debug("PandoraSearch.findDestinations(): Searching for " + input + "/" + type + "/" + field);
		
		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);
		// create a POJO repository associated with ResortBean
		PojoRepository resortBeanRepo = client.newPojoRepository(DestinationBean.class, Integer.class);

		QueryManager qm = client.newQueryManager();

		// FIXME!

		PojoQueryBuilder<DestinationBean> qb = resortBeanRepo.getQueryBuilder();
		PojoPage<DestinationBean> matches;
		resortBeanRepo.setPageLength(10);
		int start = 1;
		do {
			if (type.equalsIgnoreCase("all") && field.equalsIgnoreCase("all")) {
				// No filters - wild-card string query - any type of destination, input in any part of the document
				StringQueryDefinition query = qm.newStringDefinition().withCriteria("*"+input+"*");
				matches = resortBeanRepo.search(query, start);
			} else {
				if (!type.equalsIgnoreCase("all") && !field.equalsIgnoreCase("all")) {
					// Two filters - set specific type of destination and match input in specified filed 
					matches = resortBeanRepo.search(qb.and(qb.value("type",	type), qb.value(field, "*" + input + "*")), start);
				}
				else {
					if (!type.equalsIgnoreCase("all")) {
						// Type filter only 
						// FIXME - this query does not feel natural - match type then OR all other fields :/
						matches = resortBeanRepo.search(qb.and(	qb.value("type", type), 
																qb.or(	qb.value("name", "*"+input+"*"),
																		qb.value("country", "*"+input+"*"),
																		qb.value("description", "*"+input+"*"),
																		qb.value("website", "*"+input+"*"))), start);
					}
					else // Field filter only
						matches = resortBeanRepo.search(qb.value(field, "*" + input + "*"), start);
				}
			}

			log.debug("PandoraSearch.findDestinations(): Results " + start + " thru " + (start + matches.size() - 1));

			if (matches.hasContent()) {
				while (matches.hasNext()) {
					DestinationBean bean = matches.next();
					log.debug("PandoraSearch.findDestinations(): found: " + bean.getId() + "/" + bean.getName());
					results.add(bean);
				}
			}

			start += matches.size();
		} while (matches.hasNextPage());

		client.release();

		return results;
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#findDestinationById(int)
	 */
	public DestinationBean findDestinationById(int input) {
		DestinationBean result = null;

		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);
		// create a POJO repository associated with ResortBean
		PojoRepository resortBeanRepo = client.newPojoRepository(DestinationBean.class, Integer.class);

		PojoQueryBuilder<DestinationBean> qb = resortBeanRepo.getQueryBuilder();

		PojoPage<DestinationBean> matches;
		int start = 1;
		do {
			matches = resortBeanRepo.search(qb.value("id", input), start);

			log.debug("PandoraSearch.findDestinationById(): Results " + start + " thru " + (start + matches.size() - 1));

			if (matches.size() > 1) {
				log.error("PandoraSearch.findDestinationById(): Multiple destinations with same id!");
				return result;
			}

			if (matches.hasContent()) {
				while (matches.hasNext()) {
					DestinationBean bean = matches.next();
					log.debug("PandoraSearch.findDestinationById(): found: " + bean.getId() + "/" + bean.getName());
					result = bean;
					break;
				}
			}

			start += matches.size();
		} while (matches.hasNextPage());

		client.release();

		return result;
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#findDestination(java.lang.String, java.lang.String)
	 */
	public DestinationBean findDestination(String name, String country) {
		DestinationBean result = null;

		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);
		// create a POJO repository associated with ResortBean
		PojoRepository resortBeanRepo = client.newPojoRepository(DestinationBean.class, Integer.class);

		PojoQueryBuilder<DestinationBean> qb = resortBeanRepo.getQueryBuilder();

		PojoPage<DestinationBean> matches;
		int start = 1;
		do {
			matches = resortBeanRepo.search(qb.and(qb.value("name", name), qb.value("country", country)), start);

			log.debug("PandoraSearch.findDestinationById(): Results " + start + " thru " + (start + matches.size() - 1));

			if (matches.size() > 1) {
				log.error("PandoraSearch.findDestinationById(): Multiple destinations with same id!");
				return result;
			}

			if (matches.hasContent()) {
				while (matches.hasNext()) {
					DestinationBean bean = matches.next();
					log.debug("PandoraSearch.findDestinationById(): found: " + bean.getId() + "/" + bean.getName());
					result = bean;
					break;
				}
			}

			start += matches.size();
		} while (matches.hasNextPage());

		client.release();

		return result;
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#findUserByUsername(java.lang.String)
	 */
	public UserBean findUserByUsername(String input) {
		UserBean result = null;

		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);

		// create a POJO repository associated with UserBean
		PojoRepository userBeanRepo = client.newPojoRepository(UserBean.class, String.class);

		PojoQueryBuilder<UserBean> qb = userBeanRepo.getQueryBuilder();

		PojoPage<UserBean> matches;
		int start = 1;
		do {
			matches = userBeanRepo.search(qb.value("username", input), start);

			log.debug("PandoraSearch.findUserByUsername(): Results " + start + " thru " + (start + matches.size() - 1));

			if (matches.size() > 1) {
				log.error("PandoraSearch.findUserByUsername(): Multiple users with same username!");
				return result;
			}

			if (matches.hasContent()) {
				while (matches.hasNext()) {
					UserBean bean = matches.next();
					log.debug("PandoraSearch.findUserByUsername(): found: " + bean.getId() + "/" + bean.getUsername());
					result = bean;
				}
			}

			start += matches.size();
		} while (matches.hasNextPage());

		client.release();

		return result;
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#findUserById(int)
	 */
	public UserBean findUserById(int input) {
		UserBean result = null;
		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);

		// create a POJO repository associated with UserBean
		PojoRepository userBeanRepo = client.newPojoRepository(UserBean.class, String.class);

		PojoQueryBuilder<UserBean> qb = userBeanRepo.getQueryBuilder();

		PojoPage<UserBean> matches;
		int start = 1;
		do {
			matches = userBeanRepo.search(qb.value("id", input), start);

			log.debug("PandoraSearch.findUserById(): Results " + start + " thru " + (start + matches.size() - 1));

			if (matches.size() > 1) {
				log.error("PandoraSearch.findUserById(): Multiple users with same username!");
				return result;
			}

			if (matches.hasContent()) {
				while (matches.hasNext()) {
					UserBean bean = matches.next();
					log.debug("PandoraSearch.findUserById(): found: " + bean.getId() + "/" + bean.getUsername());
					result = bean;
				}
			}

			start += matches.size();
		} while (matches.hasNextPage());

		client.release();

		return result;
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#createUser(models.UserBean)
	 */
	public boolean createUser(UserBean user) {
		boolean result = true;
		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);
		// create a POJO repository associated with UserBean
		PojoRepository userBeanRepo = client.newPojoRepository(UserBean.class, String.class);

		try {
			userBeanRepo.write(user);
		} catch (Exception e) {
			result = false;
			log.error(e, "PandoraSearch.createUser(): exception on user " + user.getUsername());
		}
		client.release();

		return result;
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#updateUser(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean updateUser(int id, String password, String firstname, String lastname, String avatar) {
		boolean result = false;
		
		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);

		// create a POJO repository associated with UserBean
		PojoRepository userBeanRepo = client.newPojoRepository(UserBean.class, String.class);

		PojoQueryBuilder<UserBean> qb = userBeanRepo.getQueryBuilder();

		PojoPage<UserBean> matches;
		UserBean user = null;
		int start = 1;
		do {
			matches = userBeanRepo.search(qb.value("id", id), start);

			log.debug("PandoraSearch.updateUser(): Results " + start + " thru " + (start + matches.size() - 1));

			if (matches.size() > 1) {
				log.error("PandoraSearch.updateUser(): Multiple users with same username!");
				return result;
			}

			if (matches.hasContent()) {
				while (matches.hasNext()) {
					UserBean bean = matches.next();
					log.debug("PandoraSearch.updateUser(): found: " + bean.toString());
					user = bean;
					break;
				}
			}

			start += matches.size();
		} while (matches.hasNextPage());

		if (user != null) {
			if (!user.getFirstname().equalsIgnoreCase(firstname))
				user.setFirstname(firstname);
			if (!user.getLastname().equalsIgnoreCase(lastname))
				user.setLastname(lastname);
			if (!user.getPassword().equalsIgnoreCase(Crypto.encryptAES(password)))
				user.setPassword(Crypto.encryptAES(password));
			if (!avatar.isEmpty() && !user.getAvatar().equalsIgnoreCase(avatar)) 
				user.setAvatar(UserBean.avatarUrlPrefix + avatar);
			
			log.debug("PandoraSearch.updateUser(): updated: " + user.toString());

			userBeanRepo.write(user);
			
			result = true;
		}
		
		client.release();

		return result;
	}
	
	/* (non-Javadoc)
	 * @see utils.DbInterface#createDestination(models.DestinationBean)
	 */
	public boolean createDestination(DestinationBean dst) {
		boolean result = true;

		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);
		// create a POJO repository associated with ResortBean
		PojoRepository resortBeanRepo = client.newPojoRepository(DestinationBean.class, Integer.class);

		try {
			resortBeanRepo.write(dst);
		} catch (Exception e) {
			result = false;
			log.error(e, "PandoraSearch.createDestination(): exception on destination " + dst.getName());
		}

		client.release();

		return result;
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#updateDestination(int, models.DestinationBean.DestinationType, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, int, int, int, int)
	 */
	public boolean updateDestination(int id, DestinationType dstType, String name, String country, String website, String description, String logo,
			  						 String background, int dstAltitude, int dstSlopes, int dstDifficult, int dstMedium, int dstEasy, int dstFree) {
		boolean result = false;
		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);

		
		// create a POJO repository associated with ResortBean
		PojoRepository resortBeanRepo = client.newPojoRepository(DestinationBean.class, Integer.class);
		
		PojoQueryBuilder<DestinationBean> qb = resortBeanRepo.getQueryBuilder();

		PojoPage<DestinationBean> matches;
		DestinationBean destination = null;
		int start = 1;
		do {
			matches = resortBeanRepo.search(qb.value("id", id), start);

			log.debug("PandoraSearch.updateDestination(): Results " + start + " thru " + (start + matches.size() - 1));

			if (matches.size() > 1) {
				log.error("PandoraSearch.updateDestination(): Multiple destinations with same id!");
				return result;
			}

			if (matches.hasContent()) {
				while (matches.hasNext()) {
					DestinationBean bean = matches.next();
					log.debug("PandoraSearch.updateDestination(): found: " + bean.toString());
					destination = bean;
					break;
				}
			}

			start += matches.size();
		} while (matches.hasNextPage());

		if (destination != null) {
			if (!destination.getName().equalsIgnoreCase(name))
				destination.setName(name);
			if (!destination.getCountry().equalsIgnoreCase(country))
				destination.setCountry(country);
			if (!destination.getWebsite().equalsIgnoreCase(website))
				destination.setWebsite(website);
			if (!destination.getDescription().equalsIgnoreCase(description))
				destination.setDescription(description);
			
			if (!logo.isEmpty() && !destination.getLogo().equalsIgnoreCase(logo)) 
				destination.setLogo(DestinationBean.logoUrlPrefix + logo);
			if (!background.isEmpty() && !destination.getBackground().equalsIgnoreCase(background)) 
				destination.setBackground(DestinationBean.backgroundUrlPrefix + background);			
			
			if (dstType == DestinationType.SKI) {
				if (dstAltitude != -1 && ((SkiResortBean) destination).getAltitude() != dstAltitude) {
					((SkiResortBean) destination).setAltitude(dstAltitude);
				}
				if (dstSlopes != -1 && ((SkiResortBean) destination).getSlopeLength() != dstSlopes) {
					((SkiResortBean) destination).setSlopeLength(dstSlopes);
				}
				if (dstDifficult != -1 && ((SkiResortBean) destination).getSlopeCounts().getDifficult() != dstDifficult) {
					((SkiResortBean) destination).getSlopeCounts().setDifficult(dstDifficult);
				}
				if (dstMedium != -1 && ((SkiResortBean) destination).getSlopeCounts().getMedium() != dstMedium) {
					((SkiResortBean) destination).getSlopeCounts().setMedium(dstMedium);
				}
				if (dstEasy != -1 && ((SkiResortBean) destination).getSlopeCounts().getEasy() != dstEasy) {
					((SkiResortBean) destination).getSlopeCounts().setEasy(dstEasy);
				}
				if (dstFree != -1 && ((SkiResortBean) destination).getSlopeCounts().getFreeride() != dstFree) {
					((SkiResortBean) destination).getSlopeCounts().setFreeride(dstFree);
				}
				
			}
			
			log.debug("PandoraSearch.updateDestination(): updated: " + destination.toString());

			resortBeanRepo.write(destination);
			
			result = true;
		}
		
		
		client.release();
		
		return result;
	}
	/* (non-Javadoc)
	 * @see utils.DbInterface#modifyList(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public void modifyList(String username, String destination, String listtype, boolean addToList) {
		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);

		// create a POJO repository associated with ListBean
		PojoRepository listBeanRepo = client.newPojoRepository(ListBean.class, Integer.class);
		try {
			// Check if username exists; exit if null user is trying ot create a list
			UserBean user = findUserByUsername(username);
			if (user == null)
				throw new IllegalArgumentException(username + " does not exist!");

			// Find matching listtype for destination
			PojoQueryBuilder<ListBean> qb = listBeanRepo.getQueryBuilder();
			ListBean list = null;
			Integer destinationId = new Integer(destination);
			PojoPage<ListBean> matches;
			int start = 1;
			do {
				matches = listBeanRepo.search(qb.and(qb.value("destinationId", destinationId), qb.value("type", listtype)), start);

				log.debug("PandoraSearch.addToList(): Results " + start + " thru " + (start + matches.size() - 1));

				if (matches.hasContent()) {
					while (matches.hasNext()) {
						list = matches.next();
						log.debug("PandoraSearch.addToList(): found: " + list.toString());
						break;
					}
				}

				start += matches.size();
			} while (matches.hasNextPage());

			// If found ..
			if (list != null) {
				log.debug("PandoraSearch.addToList(): found a list");
				if(addToList) 
					list.addUser(username);
				else 
					list.removeUser(username);
			} else {
				if (addToList) {
					log.debug("PandoraSearch.addToList(): creating list with user " + username);
					list = ListBean.createList(destination, listtype, Arrays.asList(username));
				} else {
					log.debug("PandoraSearch.addToList(): cannot remove from non-existant list");
				}
			}

			log.debug("PandoraSearch.addToList(): modifying list = " + list.toString());

			// Write ListBean POJO
			listBeanRepo.write(list);

		} catch (Exception e) {
			log.error(e, "PandoraSearch.addToList(): exception adding " + username + " to " + listtype + " for " + destination);
		}

		client.release();

	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#findWhoSavedDestination(java.lang.String, models.ListBean.ListType)
	 */
	public List<UserBean> findWhoSavedDestination(String destination, ListType type) {
		log.debug("PandoraSearch.findWhoLikesDestination(): BEGIN");
		List<UserBean> results = new ArrayList<UserBean>();

		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);

		// create a POJO repository associated with ListBean
		PojoRepository listBeanRepo = client.newPojoRepository(ListBean.class, Integer.class);

		try {
			PojoQueryBuilder<ListBean> qb = listBeanRepo.getQueryBuilder();
			Integer destinationId = new Integer(destination);
			PojoPage<ListBean> matches;
			int start = 1;

			do {
				matches = listBeanRepo.search(qb.value("destinationId", destinationId), start);

				log.debug("PandoraSearch.findWhoLikesDestination(): Results " + start + " thru " + (start + matches.size() - 1));

				if (matches.hasContent()) {
					while (matches.hasNext()) {
						ListBean tmp = matches.next();
						log.debug("PandoraSearch.findWhoLikesDestination(): found list: " + tmp.toString());
						if (tmp.getType().equals(type)) {
							// Found a list of likes for this destination; need to find all userBeans who liked it
							log.debug("PandoraSearch.findWhoLikesDestination(): found list of usernames: " + String.join(", ", tmp.getUsernames()));
							for (String username : tmp.getUsernames()) {
								UserBean user = findUserByUsername(username);
								if (user != null) {
									user.setPassword("");
									results.add(user);
								}
							}
							break;
						}
					}
				}

				start += matches.size();
			} while (matches.hasNextPage());
		} catch (Exception e) {
			log.error(e, "PandoraSearch.findWhoLikesDestination(): exception in search for likers of " + destination.toString());
		}

		client.release();

		log.debug("PandoraSearch.findWhoLikesDestination(): END");
		return results;
	}

	// Similar - "users also like"
	/* (non-Javadoc)
	 * @see utils.DbInterface#findSimilarByListType(java.lang.String, models.ListBean.ListType)
	 */
	public List<DestinationBean> findSimilarByListType(String destination, ListType type) {
		log.debug("PandoraSearch.findSimilarByListType(): BEGIN");
		List<DestinationBean> results = new ArrayList<DestinationBean>();
		
		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);

		// create a POJO repository associated with ListBean
		PojoRepository listBeanRepo = client.newPojoRepository(ListBean.class, Integer.class);

		// Find users who like this
		List<UserBean> likers = findWhoSavedDestination(destination, type);

		// Now find what else they like
		try {
			PojoQueryBuilder<ListBean> qb = listBeanRepo.getQueryBuilder();
			int destinationId = new Integer(destination).intValue();

			// For every user who liked this - find what else he liked
			for (UserBean user : likers) {
				log.debug("PandoraSearch.findSimilarByListType(): current loop user: " + user.getUsername());
				PojoPage<ListBean> matches;
				int start = 1;
				do {
					
					matches = listBeanRepo.search(qb.and(
							qb.range("destinationId", Operator.NE, destinationId),
							qb.and(
									qb.value("type", type.name()), 
									qb.containerQuery("usernames", qb.term(user.getUsername())
											)
									)
							), start);
					
					log.debug("PandoraSearch.findSimilarByListType(): Found lists for other destinations " + start + " thru " + (start + matches.size() - 1));

					// Check each list to see if likers are listed in them
					if (matches.hasContent()) {
						while (matches.hasNext()) {
							ListBean tmp = matches.next();
							log.debug("PandoraSearch.findSimilarByListType(): found list: " + tmp.toString());
							log.debug("PandoraSearch.findSimilarByListType(): found list of usernames: " + String.join(", ", tmp.getUsernames()));
							// We found another list for one of our likers; get a DestinationBean connected to this list
							DestinationBean dst = findDestinationById(tmp.getDestinationId());
							if (dst != null) {
								if(!results.stream().anyMatch(t -> t.getId()==(dst.getId()))) {
									results.add(dst);
									log.debug("PandoraSearch.findSimilarByListType(): added destination to results: " + dst.toString());
								}
							} else {
								log.error("PandoraSearch.findSimilarByListType(): destination lookup failed for " + tmp.getDestinationId());
							}

						}
					}
					start += matches.size();
				} while (matches.hasNextPage());
			}
		} catch (Exception e) {
			log.error(e, "PandoraSearch.findSimilarByListType(): exception in search for likers of " + destination);
		}

		client.release();

		log.debug("PandoraSearch.findSimilarByListType(): END");
		return results;
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#findWhatUserSaved(java.lang.String, models.ListBean.ListType)
	 */
	public List<DestinationBean> findWhatUserSaved(String userid, ListType type) {
		log.debug("PandoraSearch.findWhatUserSaved(): BEGIN");
		List<DestinationBean> results = new ArrayList<DestinationBean>();

		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);

		// create a POJO repository associated with ListBean
		PojoRepository listBeanRepo = client.newPojoRepository(ListBean.class, Integer.class);

		// Now find what else they like
		try {
			PojoQueryBuilder<ListBean> qb = listBeanRepo.getQueryBuilder();
			int userId = new Integer(userid).intValue();
			UserBean user = findUserById(userId);

			PojoPage<ListBean> matches;
			int start = 1;

			do {
				// Find all lists where userId is in the userlist
				PojoQueryDefinition query = qb.and(qb.value("type", type.name()), qb.containerQuery("usernames", qb.term(user.getUsername())));
				matches = listBeanRepo.search(query, start);

				log.debug("PandoraSearch.findWhatUserSaved(): Found results " + start + " thru " + (start + matches.size() - 1));

				// Check each list to see if likers are listed in them
				if (matches.hasContent()) {
					while (matches.hasNext()) {
						ListBean tmp = matches.next();
						log.debug("PandoraSearch.findWhatUserSaved(): found list: " + tmp.toString());
						DestinationBean dst = findDestinationById(tmp.getDestinationId());
						if (dst != null) {
							results.add(dst);
							log.debug("PandoraSearch.findWhatUserSaved(): found destination: " + dst.toString());
						} else {
							log.error("PandoraSearch.findWhatUserSaved(): destination lookup failed for " + tmp.getDestinationId());
						}
					}
				}
				start += matches.size();
			} while (matches.hasNextPage());
		} catch (Exception e) {
			log.error(e, "PandoraSearch.findWhatUserSaved(): exception in search for likes from user " + userid);
		}

		client.release();

		log.debug("PandoraSearch.findWhatUserSaved(): END");
		return results;
	}

	/* (non-Javadoc)
	 * @see utils.DbInterface#doesUserLikeDestination(java.lang.String, java.lang.String, models.ListBean.ListType)
	 */
	public boolean doesUserLikeDestination(String username, String destinationid, ListType type) {
		log.debug("PandoraSearch.doesUserLikeDestination(): BEGIN");

		boolean result = false; 
		DatabaseClient client = DatabaseClientFactory.newClient(MarkLogicConfig.host, MarkLogicConfig.port, MarkLogicConfig.database, 
																MarkLogicConfig.user, MarkLogicConfig.password, MarkLogicConfig.authType);

		// create a POJO repository associated with ListBean
		PojoRepository listBeanRepo = client.newPojoRepository(ListBean.class, Integer.class);

		// Now find what else they like
		try {
			PojoQueryBuilder<ListBean> qb = listBeanRepo.getQueryBuilder();
			int destinationId = new Integer(destinationid).intValue();

			PojoPage<ListBean> matches;
			int start = 1;

			do {
				// Find all lists where userId is in the userlist
				PojoQueryDefinition query = qb.and(qb.value("destinationId", destinationId), 
											qb.and(qb.value("type", type.name()), 
												   qb.containerQuery("usernames", qb.term(username))));
				
				matches = listBeanRepo.search(query, start);

				log.debug("PandoraSearch.doesUserLikeDestination(): Found results " + start + " thru " + (start + matches.size() - 1));

				// Check if we have content and return true if so
				if (matches.hasContent()) {
					log.debug("PandoraSearch.doesUserLikeDestination(): Found it!");
					result = true;
					break;
				}
				start += matches.size();
			} while (matches.hasNextPage());
		} catch (Exception e) {
			log.error(e, "PandoraSearch.doesUserLikeDestination(): exception in search for likes from user " + username);
		}

		client.release();

		log.debug("PandoraSearch.doesUserLikeDestination(): END; result = " + result);
		return result;
	}
}
