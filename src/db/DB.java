package db;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import data.Event;
import data.User;

/**
 * This class responsible for the interaction with the date base. this class is a singleton.
 * @author Nadav
 *
 */
public class DB {

	/**
	 * Entity manger Factory
	 */
	private EntityManagerFactory emf;
	
	/**
	 * Entity Manger
	 */
	private EntityManager em;

	/**
	 * The instance of this class
	 */
	private static DB db;

	/**
	 * Returns the instance of this class.
	 * @return the instance of this class
	 */
	public static DB getInstance() {
		if (db == null) {
			return new DB();
		} else {
			return db;
		}
	}

	/**
	 * Constructor of this class initialize one instance of this class.
	 */
	private DB() {
		emf = Persistence.createEntityManagerFactory("CalendarServer");
		em = emf.createEntityManager();
	}

	/**
	 * This method inserts user to the users table
	 * @param user the user to insert
	 * @return a boolean represent if the user inserted successfully.
	 */
	public boolean insertUser(User user) {
		em.getTransaction().begin();

		em.persist(user);
		em.getTransaction().commit();

		if (em.contains(user)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method add event the events table
	 * @param event the event to insert
	 * @return the if of the new event
	 */
	public int addEvent(Event event) {
		em.getTransaction().begin();
		em.persist(event);
		em.flush();
		int id = event.getId();
		System.out.println("event id: " + id);
		em.getTransaction().commit();
		System.out.println("event id: " + id);
		System.out.println("event: " + event.toString());
		return id;
	}

	/**
	 * This method check if the user with this email and password exist in the data base
	 * @param email the email of the user
	 * @param password the password of the user
	 * @return the user if found, else null
	 */
	public User checkUser(String email, String password) {
		Query query = em.createQuery("select u from User u where u.email = :email and u.password = :password");
		query.setParameter("email", email);
		query.setParameter("password", password);

		User user = null;
		try {
			user = (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return user;

	}
	
	/**
	 * Returns the user according to the given email
	 * @param email the email of the user
	 * @return the user if found, else null
	 */
	public User getUser(String email) {
		Query query = em.createQuery("select u from User u where u.email = :email");
		query.setParameter("email", email);

		User user = null;
		try {
			user = (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return user;
	}
	
	/**
	 * Returns list of events according to the given user id
	 * @param id the id of the user
	 * @return the list of events related to the user with the given id
	 */
	public List<Event> getEventById(int id) {
		Query query = em.createQuery("select e from Event e where e.user.id = :id");
		query.setParameter("id", id);

		List<Event> events = null;
		try {
			events = (List<Event>)query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return events;
	}
	
	/**
	 * This method update the given event
	 * @param event the event to be updated
	 * @return 
	 */
	public boolean updateEvent(Event event) {
		Event e = em.find(Event.class, event.getId());

		em.getTransaction().begin();
		e.setTitle(event.getTitle());
		e.setDescription(event.getDescription());
		e.setDateStart(event.getDateStart());
		e.setDateEnd(event.getDateEnd());
		em.getTransaction().commit();
		return true;
	}

	/**
	 * This method remove the given event from the database
	 * @param event the event to be removed
	 * @return
	 */
	public boolean removeEvent(Event event) {
		Event e = em.find(Event.class, event.getId());

		em.getTransaction().begin();
		em.remove(e);
		em.getTransaction().commit();
		return true;
	}

	/**
	 * This method returns all events 
	 * @return list of events
	 */
	public List<Event> getEvents() {
		Query query = em.createQuery("select e from Event e");
		Vector<Event> events = (Vector<Event>)query.getResultList();
		return events;	
	}
	

	public List<Event> getSharedEvents(int id) {
		Query query = em.createQuery("select e from Event e inner join e.users user where user.id in :id");
		List<Integer> ids = new LinkedList<>();
        ids.add(id);
		query.setParameter("id", ids);
		Vector<Event> events = (Vector<Event>)query.getResultList();
		return events;	
		
	}

	public List<Event> getUpcomingEvents(int id) {
		Query query = em.createQuery("select e from Event e inner join e.users user where user.id in :id AND e.dateStart > :date");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		List<Integer> ids = new LinkedList<>();
        ids.add(id);
		query.setParameter("id", ids);
		query.setParameter("date", calendar);
		query.setMaxResults(10); //limit result to 10
		Vector<Event> events = (Vector<Event>)query.getResultList();
		return events;	
	}
	
	/**
	 * This method returns all users 
	 * @return list of users
	 */
	public List<User> getUsers() {
		Query query = em.createQuery("select u from User u");
		Vector<User> users = (Vector<User>)query.getResultList();
		return users;	
	}
	
	/**
	 * Inserts the given token to the user with given id
	 * @param user
	 * @param pushToken
	 */
	public void insertTokenToUser( int id, String token ) {
		User user = em.find(User.class, id);
		  em.getTransaction().begin();
		  user.setToken(token);
		  em.getTransaction().commit();	
	}
	
	/**
	 * Returns the token of the user with the given id
	 * @param id the user id
	 */
	public String getUserToken( int id ) {
		User user = em.find(User.class, id);
		String token = user.getToken();
		return token;
	}

	public static void main(String[] args) {
		DB db = DB.getInstance();
//		User user = new User();
//		user.setUserName("anael");
//		user.setEmail("anaelshomrai@gmail.com");
//		user.setPassword("123456");
//		Calendar dateOfBirth = Calendar.getInstance();
//		dateOfBirth.set(Calendar.YEAR, 1992);
//		dateOfBirth.set(Calendar.MONTH, 8); // 9 -1 month starts from 0
//		dateOfBirth.set(Calendar.DAY_OF_MONTH, 9);
//		user.setDateOfBirth(dateOfBirth);
		
//		Event event = new Event();
//		event.setUser(db.getUser("anaelshomrai@gmail.com"));
//		event.setDescription("Important");
//		event.setTitle("Do");
//		Calendar dateStart = Calendar.getInstance();
//		event.setDateStart(dateStart);
//		Calendar dateEnd = Calendar.getInstance();
//		dateEnd.set(Calendar.HOUR_OF_DAY, 14);
//		dateEnd.set(Calendar.MINUTE, 22);
//		dateEnd.set(Calendar.MILLISECOND, 0);
//		dateEnd.set(Calendar.SECOND, 0);
//		event.setDateEnd(dateEnd);

		
//		List<Event> events = new ArrayList<>();
//		Event event = new Event();
//		event.setDescription("Important");
//		event.setTitle("Do Homework");
//		Calendar dateStart = Calendar.getInstance();
//		event.setDateStart(dateStart);
//		Calendar dateEnd = Calendar.getInstance();
//		dateEnd.set(Calendar.HOUR_OF_DAY, 14);
//		dateEnd.set(Calendar.MINUTE, 22);
//		dateEnd.set(Calendar.MILLISECOND, 0);
//		dateEnd.set(Calendar.SECOND, 0);
//		event.setDateEnd(dateEnd);
//		events.add(event);
//		event = new Event();
//		event.setTitle("Go to the doctor");
//		event.setDescription("important");
//		events.add(event);
//
//		user.setEvents(events);
//		db.insertUser(user);
		
//		db.addEvent(event);
//		db.updateEvent(event);

	}



}
