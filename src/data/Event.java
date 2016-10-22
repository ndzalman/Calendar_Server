package data;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name = "events")
public class Event {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column( name = "Id", nullable=false)
	private int id;
	
	@Column( name = "Title", nullable=false, length=30)
    private String title;
	
	@Column( name = "Date_Start")
    private Calendar dateStart;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column( name = "Date_End")
    private Calendar dateEnd;
    
	@Column( name = "Description", nullable=false, length=200)
    private String description;
	
    @ManyToOne
    @JoinColumn(name="USER_ID", nullable=false)
    private User user;
	
    public Event(){

    }

    public Event(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Event(String title, Calendar dateStart, Calendar dateEnd, String description) {
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDateStart() {
        return dateStart;
    }

    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }

    public Calendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
