package de.guderlei.pubsub.subscriber;

import org.osgi.service.log.LogService;

import com.google.inject.Inject;

import de.guderlei.pubsub.model.Message;
import de.guderlei.pubsub.model.Subscriber;

/**
 * simple {@link Subscriber} service implementation which will be
 * managed by peaberry/guice 
 * 
 * @author rguderlei
 *
 */
public class PeaberrySubscriber implements Subscriber {
	private final LogService logService;
	
	/**Ctor.
	 * @param logService
	 */
	@Inject
	PeaberrySubscriber(LogService logService){
		this.logService = logService;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void receive(Message message) {
		logService.log(LogService.LOG_INFO, "PeaberrySubscriber: " + message.toString());
	}

}
