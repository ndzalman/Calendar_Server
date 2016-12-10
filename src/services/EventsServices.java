package services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.Event;
import data.User;
import db.DB;

@Path("/events")
public class EventsServices {
	private DB db = DB.getInstance();
	
	/**
	 * This service returns list of events
	 * @return the list of events
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllEvents")
	public String getAllEvents() {
		List<Event> events = new ArrayList<>();
		events = db.getEvents();
		
	    String eventsJSON = new Gson()
	    		.toJson(events, new TypeToken<Collection<Event>>() {}.getType());
		
//		Gson gson = new GsonBuilder()
//				.setExclusionStrategies(new ExclusionStrategy() {
//					
//					@Override
//					public boolean shouldSkipField(FieldAttributes f) {
//			            return false;			            
//					}
//					
//					@Override
//					public boolean shouldSkipClass(Class<?> clazz) {
//			            return (clazz == User.class);
//					}
//				})
//				.serializeNulls()
//				.create();
	
//		return gson.toJson(events.toArray());	
	    
	    return eventsJSON;
	}
	
	
	
	/**
	 * This service returns list of events
	 * @return the list of events
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllSharedEvents")
	public String getAllSharedEvents(@QueryParam("id") int id) {
		List<Event> events = new ArrayList<>();
		events = db.getSharedEvents(id);
		System.out.println("events: " + events.size());
		
	    String eventsJSON = new Gson()
	    		.toJson(events, new TypeToken<Collection<Event>>() {}.getType());
	    
	    return eventsJSON;
	}
	
	/**
	 * This service returns list of events
	 * @return the list of events
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUpcomingEvents")
	public String getUpcomingEvents(@QueryParam("id") int id) {
		List<Event> events = new ArrayList<>();
		events = db.getUpcomingEvents(id);
		System.out.println("events: " + events.size());
		
	    String eventsJSON = new Gson()
	    		.toJson(events, new TypeToken<Collection<Event>>() {}.getType());
	    
	    return eventsJSON;
	}
	
	/**
	 * This service returns list of events
	 * @return the list of events
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getEventsByDay")
	public String getEventsByDay(@QueryParam("id") int id) {
		List<Event> events = new ArrayList<>();
		events = db.getEventsByDay(id);
		System.out.println("events today: " + events.size());
		
	    String eventsJSON = new Gson()
	    		.toJson(events, new TypeToken<Collection<Event>>() {}.getType());
	    
	    return eventsJSON;
	}
	
	
		// web method
	/**
	 * This service returns events according to the user id
	 * @param id the id of the user
	 * @return list of events related to this user
	 */
		@POST
		@Path("/getEventById")
		@Produces(MediaType.APPLICATION_JSON)
		public String getEventById(@FormParam("id") int id) {
			System.out.println("in get event by id method id: " + id);
			List<Event> events = new ArrayList<>();
			events = db.getEventById(id);		
			System.out.println(events.get(0).toString());
			Gson gson = new GsonBuilder()
					.setExclusionStrategies(new ExclusionStrategy() {
						
						@Override
						public boolean shouldSkipField(FieldAttributes f) {
				            return false;			            
						}
						
						@Override
						public boolean shouldSkipClass(Class<?> clazz) {
				            return (clazz == User.class);
						}
					})
					.serializeNulls()
					.create();
			
			String jsonString = gson.toJson(events);
			System.out.println(jsonString);
			return jsonString;
		}
	

	
	/**
	 * 
	 * @param input
	 * @return
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/addEvent")
	public String addEvent( InputStream input ) {
		System.out.println("in addEvent method");
		StringBuilder eventJSON = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				eventJSON.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		
		Gson gson = new Gson();
		Event event = gson.fromJson(eventJSON.toString(), Event.class);
		System.out.println("users in this event: " + event.getUsers().size());

		String id = "-1";
		
		int eventId = db.addEvent(event);
		id = String.valueOf(eventId);
		event.setId(eventId);
		System.out.println("event id: " + eventId);
		
		Set<User> eventUsers = event.getUsers();
		System.out.println("users in this event: " + eventUsers.size());
		for(User u : eventUsers)
		{
			System.out.println(u.getId() + " != " + event.getOwnerId());
			if (u.getId() != event.getOwnerId()){ //send notification to everyone but the owner
				try {
				sendMessageToDevice(event, u);
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		}
		
		return id;

		
	}
	
	public void sendMessageToDevice(Event e, User u) throws Exception
	{
		
			String url = "https://gcm-http.googleapis.com/gcm/send";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			//con.setRequestProperty("User-Agent", USER_AGENT);
//			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Authorization", "key=AIzaSyC6pdNl8PS2jgcV-sxDwlospmXMQa44e7A");

//			e.getUsers().clear();
//			e.setUsers(null);
			String eventJSON = new Gson().toJson(e);
			
//			String urlParameters = "{\"to\":\"" + db.getUserToken(u.getId()) + 
//					"\", \"data\": {\"event-title\":\"" + e.getTitle() + "\"}}";
////			
			String urlParameters = "{\"to\":\"" + db.getUserToken(u.getId()) + 
					"\", \"data\":"+ eventJSON +"}";

			
			 byte[] sendBytes = urlParameters.getBytes("UTF-8");
			   con.setFixedLengthStreamingMode(sendBytes.length);
			
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//			wr.writeBytes(urlParameters); //orginal
			wr.write(sendBytes);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());

		}
	
	/**
	 * This service updates the given event
	 * @param input
	 * @return
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/updateEvent")
	public String updateEvent( InputStream input ) {
		System.out.println("in updateEvent method");
		StringBuilder eventJSON = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				eventJSON.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		
		Gson gson = new Gson();
		Event event = gson.fromJson(eventJSON.toString(), Event.class);
		System.out.println(eventJSON);
		boolean result = db.updateEvent(event);
		if(result) {
			return "OK";
		} else {
			return "notOk";
		}
	}
	
	/**
	 * This service removes the given event
	 * @param input
	 * @return
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/removeEvent")
	public String removeEvent( InputStream input ) {
		System.out.println("in removeEvent method");
		StringBuilder eventJSON = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				eventJSON.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		
		Gson gson = new Gson();
		Event event = gson.fromJson(eventJSON.toString(), Event.class);
		boolean result = db.removeEvent(event);
		if(result) {
			return "OK";
		} else {
			return "notOk";
		}
	}
	
	

}
