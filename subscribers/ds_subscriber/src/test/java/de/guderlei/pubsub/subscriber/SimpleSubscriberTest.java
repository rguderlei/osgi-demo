package de.guderlei.pubsub.subscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.osgi.service.log.LogService;

import de.guderlei.pubsub.model.Message;

public class SimpleSubscriberTest {
	@Test
	public void send_message(){
		LogService log = mock(LogService.class);
		
		Message message = new Message("me", "message");
		SimpleSubscriber sub = new SimpleSubscriber();
		sub.setLog(log);
		
		sub.receive(message);
		verify(log, only()).log(LogService.LOG_INFO, "SCR Subscriber: me: message");
	}
}
