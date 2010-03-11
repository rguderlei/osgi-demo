package de.guderlei.pubsub.model;

import javax.annotation.Nonnull;

/**Defining an endpoint to be called by a Producer of a message. 
 * This Interface is typically implemented by a message hub in order to be 
 * distributed to the subscribers.
 * 
 * The interface is implemented once and can be called by multiple message producers
 * 
 * @author rguderlei
 *
 */
public interface Producer {
	/**
	 * @param message the message to be sent, must not be null
	 */
	public void send(@Nonnull Message message);
}
