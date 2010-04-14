package de.guderlei.pubsub.producer;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import de.guderlei.pubsub.model.Message;
import de.guderlei.pubsub.model.Producer;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class PublisherServletTest {

	@Test
	public void create_and_handle_message(){
		
		Producer producer = mock(Producer.class);
		
		PublisherServlet servlet = new PublisherServlet(producer);
		
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		try {
			File tmp = File.createTempFile("msg", ".txt");
			tmp.deleteOnExit();
			
			when(response.getWriter()).thenReturn(new PrintWriter(tmp));
			
			servlet.doGet(request, response);
			verify(producer, only()).send(any(Message.class));
			
			tmp.delete();
			
		} catch (Exception e) {			
			e.printStackTrace();
			fail();
		} 
	}

}
