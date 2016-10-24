package db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import data.Event;
import data.User;

public class DB {

	private EntityManagerFactory emf;
	private EntityManager em;

	private static DB db;

	public static DB getInstance() {
		if (db == null) {
			return new DB();
		} else {
			return db;
		}
	}

	private DB() {
		emf = Persistence.createEntityManagerFactory("CalendarServer");
		em = emf.createEntityManager();
	}

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

	public int addEvent(Event event) {
		em.getTransaction().begin();

		em.persist(event);
		em.flush();
		int id = event.getId();
		em.getTransaction().commit();
		return id;
		
	}

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

	public boolean removeEvent(Event event) {
		Event e = em.find(Event.class, event.getId());

		em.getTransaction().begin();
		em.remove(e);
		em.getTransaction().commit();
		return true;
	}

	public List<Event> getEvents() {
		Query query = em.createQuery("select e from Event e");
		Vector<Event> categories = (Vector<Event>)query.getResultList();
		return categories;	
	}

}
