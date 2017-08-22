package com.engineplay.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.engineplay.datastore.pojos.UserPrefs;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String navBar, form = null;
		int donuts = 0;
		if (user == null) {
			navBar = "<h3>Welcome! <a href=\"" + userService.createLoginURL("/") +
			"\">Sign in or register</a> to customize.</h3>";
			form = "";
		} else {
			UserPrefs userPrefs = UserPrefs.getPrefsForUser(user);
			
			if (userPrefs != null) {
				donuts = userPrefs.getDonuts();
			}
			navBar = "<h3>Welcome, " + user.getEmail() + "! You can <a href=\"" +
			userService.createLogoutURL("/") +
			"\">sign out</a>.</h3>";
			form = "<form action=\"/donuts\" method=\"post\">" +
			"<label for=\"donuts\">" +
			"Need more donuts?:" +
			"</label>" +
			"<input name=\"donuts\" id=\"donuts\" type=\"text\" size=\"4\" />" +
			" <input name=\"olddonuts\"  type=\"hidden\" value=\"" + donuts + "\" /> " +
			" <input name=\"userId\"  type=\"hidden\" value=\"" +user.getEmail()+ "\" /> " +
			"   <input type=\"submit\" value=\"  Add  \" /><br><br><input type=\"submit\"  name =\"deleteBtn\" value=\"Delete my Record\" />" +
			"</form>";
			
		}
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.print("<html><head><title>Engine Play - Donuts</title></head><body><center>");
		out.println("<img src = 'images/donuts_logo.gif'>");
		out.println(navBar);
		if (donuts != 0)
			out.println("<p>Donuts you have: " + donuts + "</p>");
		else
			out.println("<p> No donuts you have :( </p>");
		out.println(form);
		out.print("<br><b>Note:</b> Your Email ID will be saved, if you add donuts. You can delete your record by pressing <b>Delete my Record</b>.<br><br>");
		out.print("<img src='http://code.google.com/appengine/images/appengine-noborder-120x30.gif' alt='Powered by Google App Engine' /></center></body></html>");
	}
}