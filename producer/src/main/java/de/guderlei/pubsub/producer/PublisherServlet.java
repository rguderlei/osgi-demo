package de.guderlei.pubsub.producer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

import de.guderlei.pubsub.model.Message;
import de.guderlei.pubsub.model.Producer;

/**
 * Simple Servlet. For every Request, a {@link Message} is created containing the remote host of the request
 * @author rguderlei
 *
 */
public class PublisherServlet extends HttpServlet {
	private static final long serialVersionUID = 1577027590044679300L;

	
	private Producer producer;
	
	/**Ctor. 
	 * @param producer {@link Producer} to which the message will be passed
	 */
	@Inject	
	PublisherServlet(Producer producer){
		this.producer = producer;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		producer.send(new Message("PublisherServlet", "request from " + req.getRemoteHost()));
		
		PrintWriter writer = resp.getWriter();
		writer.append("send message");
		writer.close();

		Message msg = new Message(null, null);
	}

}
