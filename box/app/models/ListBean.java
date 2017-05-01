package models;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.type.ListType;

import play.Logger;

import com.marklogic.client.pojo.annotation.Id;
import com.marklogic.client.pojo.annotation.PathIndexProperty;

/**
 * @author Ivana Frankic
 * <br><br>
 * Users and destinations interact through {@link ListBean} <br>
 * Users interact directly using "like" and "wish". This further lets them see interconnected destinations. <br>
 * Destinations are connected by users ("likers" or "wishers") in common. 
 */
public class ListBean {
	private static Logger log = new Logger();

	private int id;
	private int destinationId;
	private ListType type;
	private List<String> usernames;
	
	/**
	 * @param destinationId represents the destination this list belongs to
	 * @param type is this a "like" or a "wish" list
	 * @param usernames list of usernames belonging to this list
	 */
	public ListBean(int destinationId, ListType type, List<String> usernames) {
		this.id = new SecureRandom().nextInt();
		this.destinationId = destinationId;
		this.type = type;
		this.usernames = new ArrayList<String>(usernames);
	}
	
	/**
	 * @param destination represents the destination this list belongs to
	 * @param type is this a "like" or a "wish" list
	 * @param usernames list of usernames belonging to this list
	 */
	public ListBean(DestinationBean destination, ListType type, List<String> usernames) {
		this(destination.getId(), type, usernames);
	}
	
	/**
	 * @param destination represents the destination this list belongs to
	 * @param type is this a "like" or a "wish" list
	 * @param usernames list of usernames belonging to this list
	 */
	public ListBean(String destination, ListType type, List<String> usernames) {
		this(new Integer(destination), type, usernames);
	}
	
	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@PathIndexProperty(scalarType = PathIndexProperty.ScalarType.INT)
	public int getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}

	public ListType getType() {
		return type;
	}

	public void setType(ListType type) {
		this.type = type;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	public void addUser(String username) {
		if (usernames.contains(username)) 
			log.debug("ListBean.addUser(): user already in this list!");
		else
			usernames.add(username);
	}
	
	public void removeUser(String username) {
		usernames.remove(username);
	}
	
	public List<String> allOtherUsers(String username) {
		List<String> others = new ArrayList<String>(this.usernames);
		others.remove(username);
		
		return others;
	}
	
	public enum ListType {
		LIKES(0),
		WISHLIST(1);
		
		int type ;
		
		private ListType(int type) {
			this.type = type;
		}
		
		public boolean sameType(String type) {
			log.debug("ListBean.sameType(): comparing " + this.type + "/" + type);
			
			boolean result = false;
			
			switch (type) {
			case "likes":
				result = (this.type == 0);
				break;
			case "wishlist":
				result = (this.type == 1);
				break;
			default:
				break;
			}

			return result;
		}
	}

	public static ListBean createList(String destination, String listtype, List<String> users) {
		ListBean result = null;
		switch (listtype) {
		case "likes":
		case "LIKES":
			result = new ListBean(destination, ListType.LIKES, users);
			break;
		case "wishlist":
		case "WISHLIST":
			result = new ListBean(destination, ListType.WISHLIST, users);
			break;
		default:
			break;
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return " {"
				+"id:"+this.getId()
				+", type:" + this.getType()
				+", destinationId:"+this.getDestinationId()
				+", users:"+ String.join(", ", this.getUsernames())
				+"} ";
	}
}
