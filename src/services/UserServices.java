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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.Event;
import data.User;
import db.DB;

@Path("/users")
public class UserServices {
	
	private DB db = DB.getInstance();
	
	/**
	 * The service inserts the given user
	 * @param input
	 * @return
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/insertUser")
	public String insertUser( InputStream input ) {
		System.out.println("in insertUser method");
		StringBuilder jsonUser = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonUser.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		
		Gson gson = new Gson();
		User user = gson.fromJson(jsonUser.toString(), User.class);
		boolean result = db.insertUser(user);
		if(result) {
			return "OK";
		} else {
			return "notOk";
		}
	}
	
	/**
	 * This service check if the user with the given email and password exist
	 * @param email the email of the user
	 * @param password the password of the user
	 * @return a json represent the user if found, else null
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkUser")
	public String checkUser( @QueryParam("email") String email, @QueryParam("password") String password ){
		System.out.println("in checkUser method " + email + " " + password);
		User user = db.checkUser(email,password);		
		Gson gson = new GsonBuilder()
				.setExclusionStrategies(new ExclusionStrategy() {
					
					@Override
					public boolean shouldSkipField(FieldAttributes f) {
			            return false;			            
					}
					
					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
			            return (clazz == Event.class);
					}
				})
				.serializeNulls()
				.create();
		
		String jsonString = gson.toJson(user);
		System.out.println(jsonString);
		return jsonString;
	}
	
	// web method
	/**
	 * This service check if the user with the given email and password exist
	 * @param email the email of the user
	 * @param password the password of the user
	 * @return a json represent the user if found, else null
	 */
	@POST
	@Path("/validateUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String validateUser(@FormParam("email") String email,@FormParam("password")String password) {
		System.out.println("in checkUser method " + email + " " + password);
		User user = db.checkUser(email,password);		
		Gson gson = new GsonBuilder()
				.setExclusionStrategies(new ExclusionStrategy() {
					
					@Override
					public boolean shouldSkipField(FieldAttributes f) {
			            return false;			            
					}
					
					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
			            return (clazz == Event.class);
					}
				})
				.serializeNulls()
				.create();
		
		String jsonString = gson.toJson(user);
		System.out.println(jsonString);
		return jsonString;
	}
	
}
