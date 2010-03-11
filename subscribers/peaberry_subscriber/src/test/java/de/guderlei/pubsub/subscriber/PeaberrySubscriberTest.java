package de.guderlei.pubsub.subscriber;

import org.junit.Test;
import org.osgi.service.log.LogService;

import de.guderlei.pubsub.model.Message;

import static org.mockito.Mockito.*;

public class PeaberrySubscriberTest {
	@Test
	public void send_message(){
		LogService log = mock(LogService.class);
		
		Message message = new Message("me", "message");
		PeaberrySubscriber sub = new PeaberrySubscriber(log);
		
		sub.receive(message);
		verify(log, only()).log(LogService.LOG_INFO, "PeaberrySubscriber: " + message.toString());
	}
}
