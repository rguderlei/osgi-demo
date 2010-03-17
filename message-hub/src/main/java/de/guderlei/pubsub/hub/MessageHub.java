package de.guderlei.pubsub.hub;

import org.osgi.service.log.LogService;

import com.google.inject.Inject;

import de.guderlei.pubsub.model.Message;
import de.guderlei.pubsub.model.Producer;
import de.guderlei.pubsub.model.Subscriber;

/**
 * The {@link MessageHub} takes a {@link Message} and distributes the message to
 * a list of {@link Subscriber}s. 
 * 
 * @author rguderlei
 *
 */
public class MessageHub implements Producer{
	
	private final Iterable<Subscriber> subscribers;
	private final LogService log;
	
	/**Ctor.
	 * @param subscribers
	 * @param log
	 */
	@Inject
	MessageHub(Iterable<Subscriber> subscribers, LogService log){
		this.subscribers = subscribers;
		this.log = log;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void send(Message message) {
		if(!subscribers.iterator().hasNext()){
			log.log(LogService.LOG_WARNING, "no subscriber");
		}
				
		for(Subscriber subscriber: subscribers){
			log.log(LogService.LOG_INFO, "Distributing message ...");
			subscriber.receive(message);
		}		
	} 
}
