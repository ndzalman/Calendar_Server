package services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import data.User;
import db.DB;

@Path("/users")
public class UserServices {
	
	private DB db = DB.getInstance();
	
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkUser")
	public String checkUser( @QueryParam("email") String email, @QueryParam("password") String password ){
		System.out.println("in checkUser method");
		User user = db.checkUser(email,password);
		Gson gson = new Gson();
		return gson.toJson(user);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/hello")
	public String getHello() {
		System.out.println("in hello method");
		return "Hello";
		
	}
	

}
