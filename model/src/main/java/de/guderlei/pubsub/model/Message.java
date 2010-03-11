package de.guderlei.pubsub.model;

import javax.annotation.Nonnull;

/**The class representing a message to be processed by the system.
 * 
 * The message is produced by a Producer and the distributed to multiple Subscribers by
 * a central message hub.
 * 
 * @author rguderlei
 *
 */
public class Message {
	private final String message;
	private final String producer;
	
	/**Ctor.
	 * @param producer identifier for the producer of the message, must not be null
	 * @param message the message, must not be null
	 */
	public Message (@Nonnull String producer, @Nonnull String message){
		if(producer == null || "".equals(producer)){
			throw new IllegalArgumentException("producer must not be null or empty");
		}
		
		if(message == null || "".equals(message)){
			throw new IllegalArgumentException("message must not be null or empty");
		}
		
		this.producer = producer;
		this.message = message;
	}
	
	public @Nonnull String toString(){
		return producer + ": " + message;
	}
	
}
