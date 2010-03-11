package de.guderlei.pubsub.hub;

import com.google.inject.Inject;

import de.guderlei.pubsub.model.Message;
import de.guderlei.pubsub.model.Producer;
import de.guderlei.pubsub.model.Subscriber;

public class MessageHub implements Producer{
	@Inject
	private Iterable<Subscriber> subscribers;
	

	@Override
	public void send(Message message) {
		if(!subscribers.iterator().hasNext()){
			System.out.println("no subscriber");
		}
		
		for(Subscriber subscriber: subscribers){
			subscriber.receive(message);
		}		
	} 
}
