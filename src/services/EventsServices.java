package services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.Event;
import data.User;
import db.DB;

@Path("/events")
public class EventsServices {
	private DB db = DB.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllEvents")
	public String getAllEvents() {
		Gson json = new Gson();
		List<Event> events = new ArrayList<>();
		events = db.getEvents();
		
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
		
		
		return gson.toJson(events.toArray());	
	}
	
	
	// web method
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
		String id = "-1";
		id = String.valueOf(db.addEvent(event));
		return id;

		
	}
	
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
