package de.guderlei.pubsub.producer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

import de.guderlei.pubsub.model.Message;
import de.guderlei.pubsub.model.Producer;

public class PublisherServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1577027590044679300L;

	@Inject
	private Producer producer;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		producer.send(new Message("PublisherServlet", "GET"));
		
		resp.getWriter().append("send message");
		resp.getWriter().close();
	}

}
