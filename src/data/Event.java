package data;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * This class represent an event object.
 * @author Nadav
 *
 */
@Entity
@Table (name = "events")
public class Event {

	/**
	 * Id of this event. auto generate.
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column( name = "ID", nullable=false)
	private int id;
	
	/**
	 * Title of this event.
	 */
	@Column( name = "Title", nullable=false, length=30)
    private String title;
	
	/**
	 * The starting date of this event.
	 */
	@Column( name = "Date_Start")
    private Calendar dateStart;
	
	/**
	 * The ending date of this event.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column( name = "Date_End")
    private Calendar dateEnd;
	
	/**
	 * Location of the event
	 */
	@Column( name = "Location")
    private String location;
	
	/**
	 * Description of this event.
	 */
	@Column( name = "Description", length=200)
    private String description;
	
	/**
	 * User of this event. many events can be related to one user.
	 */
	@ManyToMany(targetEntity=User.class)
    private Set<User> users = new HashSet<>();
	
	/**
	 * User id of the owner of this event
	 */
	@Column(name="Owner_Id", nullable=false)
	private int ownerId;
	
    /**
     * Default constructor.initialize an empty event object.
     */
    public Event(){

    }

    /**
     * Constructor of event. initialize an event object with title and description.
     * @param title the title of this event
     * @param description description of this event
     */
    public Event(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /**
     * Constructor of event. initialize an event object with title,starting date, ending date and description.
     * @param title the title of this event
     * @param dateStart the starting date of this event
     * @param dateEnd the ending date of this event
     * @param description description of this event
     */
    public Event(String title, Calendar dateStart, Calendar dateEnd, String description) {
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
    }

    /**
     * Returns the id of this event.
     * @return the id of this event
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of this event
     * @param id the id of this event
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Returns the title of this event.
     * @return the title of this event
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this event
     * @param title the title of this event
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the starting date of this event
     * @return the starting date of this event
     */
    public Calendar getDateStart() {
        return dateStart;
    }

    /**
     * Sets the starting date  of this event
     * @param dateStart the starting date  of this event
     */
    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }
    
    /**
     * Returns the ending date of this event
     * @return the ending date of this event
     */
    public Calendar getDateEnd() {
        return dateEnd;
    }

    /**
     * Sets the ending date of this event
     * @param dateEnd the ending date of this event
     */
    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }
    
    /**
     * Returns the description of this event
     * @return the description of this event
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this event
     * @param description the description of this event
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Returns the users of this event
     * @return the users of this event
     */
	public Set<User> getUsers() {
		return this.users;
	}


	/**
	 * Add user to list of users
	 * @param user the user to be added
	 */
	public void addUser(User user) {
		this.users.add(user);
	}
	
	/**
	 * Remove user from list of users
	 * @param user the user to be removed
	 */
	public void removeUser(User user) {
		this.users.remove(user);
	}
	
    /**
     * Sets the users of this event
     * @param users the users of this event
     */
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return id == event.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", description='" + description + '\'' +
                '}';
    }
	
}
