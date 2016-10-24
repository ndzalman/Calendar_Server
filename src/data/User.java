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

@Entity
@Table (name = "users")
public class User{ 


	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column( name = "USER_ID", nullable=false)
    private int id;
	
	@Column( name = "user_name", nullable=false, length=15)
    private String userName;
	
	@Column( name = "Email", nullable=false, length=40,unique=true)
    private String email;
	
	@Column( name = "Password", nullable=false, length=20)
    private String password;
	
	@Temporal(TemporalType.DATE) // for saving without time
    private Calendar dateOfBirth;
    
	@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
    private List<Event> events = new ArrayList<>();

    public User(){

    }

    public User(String userName, String email, String password, Calendar dateOfBirth, List<Event> events) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event){
        this.events.add(event);
    }

    public void removeEvent(Event event){
        this.events.remove(event);
    }

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
