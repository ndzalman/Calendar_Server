package data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "meetings")
public class Meeting {
	
	/**
	 * Id of the meeting
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column( name = "MEETING_ID", nullable=false)
    private int id;
	
	@OneToMany
	List<User> user;
	
	Event event;
	
	// attachment 

}
