package de.guderlei.pubsub.model;



/**Interface describing a subscriber service.
 * 
 * There can be multiple subscribers in the system.
 * 
 * @author rguderlei
 *
 */
public interface Subscriber {
	/**function called by a message hub when the hub receives a message
	 * @param message to be received by the subscriber, must not be null
	 */
	public void receive( Message message);
}
