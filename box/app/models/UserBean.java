package models;

import java.io.File;
import java.security.SecureRandom;

import play.Play;
import play.libs.Crypto;

import com.marklogic.client.pojo.annotation.Id;
import com.marklogic.client.pojo.annotation.PathIndexProperty;

/**
 * @author Ivana Frankic
 * <br><br>
 * Object representing user. Holds simple information about the user
 * <br>
 * Users and destination interact through {@link ListBean}
 */
public class UserBean {
	/**
	 * path prefix for the file upload to the filesystem 
	 */
	public static final String avatarPathPrefix = 	"public" + File.separator + 
													"images" + File.separator + 
													"avatars" + File.separator;
	/**
	 * url prefix for displaying the file
	 */
	public static final String avatarUrlPrefix 	= 	"public/images/avatars/";
	
	private int id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String avatar;
	
	/**
	 * @param username username, one item for login 
	 * @param password password, the other item for login
	 * @param firstname first name
	 * @param lastname last name
	 * @param avatar filename of the chosen and  uploaded avatar
	 */
	public UserBean(String username, String password, String firstname, String lastname, String avatar) {
		setId(new SecureRandom().nextInt());
		setUsername(username);
		setPassword(Crypto.encryptAES(password));
		setFirstname(firstname);
		setLastname(lastname);
		setAvatar(avatarUrlPrefix + avatar);
	}

	@PathIndexProperty(scalarType=PathIndexProperty.ScalarType.INT)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Id
	@PathIndexProperty(scalarType=PathIndexProperty.ScalarType.STRING)
	public String getUsername() {
		return username;
	}

	public void setUsername(String nickname) {
		this.username = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return " {"
				+"id:"+this.getId()
				+", username:" + this.getUsername()
				+", avatar:"+this.getAvatar()
				+"} ";
	}
}
