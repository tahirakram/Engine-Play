package com.engineplay.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class TrimServlet extends HttpServlet {
	
    private static final Logger log = Logger.getLogger(TrimServlet.class.getName());

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			resp.setContentType("application/json");
			resp.setHeader("Access-Control-Allow-Origin", "*");

			PrintWriter out = resp.getWriter();

			String userUrl = req.getParameter("url");
			JSONObject jsonObject = new JSONObject();
			if (userUrl != null && !"".equals(userUrl)) {

				String[] removeProtocol = {"http://", "https://", "ftp://"};
				
				for (String protocol : removeProtocol) {
					if (userUrl.contains(protocol)){
						userUrl = userUrl.substring(protocol.length());
						break;
					}
				}
				
				log.info("Removed protocol URL: "+ userUrl);
				
				URL url = new URL("http://myurl.pk/?url="+userUrl);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("GET");
				
				String line = null;
				
				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

					InputStream is = connection.getInputStream();
					BufferedReader rd = new BufferedReader(
							new InputStreamReader(is));

					line = rd.readLine();
				}
				log.info("Final URL:" + line);
				jsonObject.put("url", line);
			}
			out.print(jsonObject);
			out.flush();

		} catch (Exception e) {
			log.warning("Exception: " + e.toString());
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
