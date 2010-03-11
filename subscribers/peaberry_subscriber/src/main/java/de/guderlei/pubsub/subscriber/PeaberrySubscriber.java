package de.guderlei.pubsub.subscriber;

import org.osgi.service.log.LogService;

import com.google.inject.Inject;

import de.guderlei.pubsub.model.Message;
import de.guderlei.pubsub.model.Subscriber;

public class PeaberrySubscriber implements Subscriber {
	private LogService logService;
	
	@Inject
	public PeaberrySubscriber(LogService logService){
		this.logService = logService;
	}
	
	@Override
	public void receive(Message message) {
		logService.log(LogService.LOG_INFO, "PeaberrySubscriber: " + message.toString());
	}

}
