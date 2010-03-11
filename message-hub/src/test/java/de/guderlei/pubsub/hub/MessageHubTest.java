package de.guderlei.pubsub.hub;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.osgi.service.log.LogService;

import de.guderlei.pubsub.model.Message;
import de.guderlei.pubsub.model.Subscriber;
import static org.mockito.Mockito.*;

public class MessageHubTest {
	@Test
	public void message_processing_with_two_subscribers(){
		Subscriber sub1 = mock(Subscriber.class);
		Subscriber sub2 = mock(Subscriber.class);
		List<Subscriber> subs = Arrays.asList(sub1, sub2);
		
		LogService log = mock(LogService.class);
		
		Message msg = new Message("me", "message");
		
		MessageHub hub = new MessageHub(subs, log);
		
		hub.send(msg);
		
		verify(sub1, only()).receive(msg);
		verify(sub2, only()).receive(msg);
		
		verify(log, times(2)).log(LogService.LOG_INFO, "Distributing message ...");
	}
	
	@Test
	public void message_processing_without_subscribers(){
		LogService log = mock(LogService.class);
		
		Message msg = new Message("me", "message");
		
		MessageHub hub = new MessageHub(Collections.<Subscriber>emptyList(), log);
		
		hub.send(msg);
		
		verify(log, only()).log(LogService.LOG_WARNING, "no subscriber");
	}
}
