package de.guderlei.pubsub.subscriber;


import aQute.bnd.annotation.component.Component;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import de.guderlei.pubsub.model.Message;
import de.guderlei.pubsub.model.Subscriber;

/**
 * simple {@link Subscriber} implementation. The Service will be managed by DS / SCR
 * 
 * @author rguderlei
 *
 */
@Component(servicefactory = true)
public class SimpleSubscriber implements Subscriber {
	
	private LogService log;
	
	/**sets the log service reference
	 * @param log
	 */
    @Reference
	public void setLog(LogService log){
		if(log != null){
			this.log = log;
		}
	}

    @Activate
    protected void activate(ComponentContext context) {

    }

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void receive(Message message) {
		log.log(LogService.LOG_INFO, "SCR Subscriber: " + message.toString());
	}

}
