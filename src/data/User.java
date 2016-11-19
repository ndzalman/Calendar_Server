package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class represent a user object.
 * @author Nadav
 *
 */
@Entity
@Table (name = "users")
public class User{ 

	/**
	 * Id of the user
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column( name = "USER_ID", nullable=false)
    private int id;
	
	/**
	 * User name of the user
	 */
	@Column( name = "user_name", nullable=false, length=15)
    private String userName;
	
	/**
	 * Email of the user, this field is unique
	 */
	@Column( name = "Email", nullable=false, length=40,unique=true)
    private String email;
	
	/**
	 * Password of the user
	 */
	@Column( name = "Password", nullable=false, length=20)
    private String password;
	
	/**
	 * Date of birth of the user
	 */
	@Temporal(TemporalType.DATE) // for saving without time
    private Calendar dateOfBirth;
    
	/**
	 * Events of the user, one user can have many events
	 */
	@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
    private List<Event> events = new ArrayList<>();

	/**
	 * Default constructor
	 */
    public User(){

    }

    /**
     * Constructor of the user
     * @param userName the userName of the user
     * @param email the email of the user
     * @param password the password of the user
     * @param dateOfBirth the date of birth of the user
     * @param events the list of events of the user
     */
    public User(String userName, String email, String password, Calendar dateOfBirth, List<Event> events) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }
    
    /**
     * Returns the userName of the user
     * @return the userName of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the userName of the user
     * @param userName the user name of the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the email of the user
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the date of birth of the user
     * @return the date of birth of the user
     */
    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }
    
    /**
     * Sets the date of birth of the user
     * @param dateOfBirth the date of birth of the user
     */
    public void setDateOfBirth(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns the events of the user
     * @return the events of the user
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets the events of the user
     * @param events the events of the user
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * Add event to the list events of the user
     * @param event the event to be added
     */
    public void addEvent(Event event){
        this.events.add(event);
    }

    /**
     * Remove event from the list events of the user
     * @param event the event to be removed
     */
    public void removeEvent(Event event){
        this.events.remove(event);
    }

    /**
     * Returns the id of the user
     * @return the id of the user
     */
    public int getId() {
		return id;
	}

	@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", events=" + events +
                '}';
    }
}
