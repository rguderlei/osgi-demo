package de.guderlei.pubsub.subscriber;


import aQute.bnd.annotation.component.*;
import aQute.bnd.annotation.metatype.Configurable;
import org.osgi.service.log.LogService;

import de.guderlei.pubsub.model.Message;
import de.guderlei.pubsub.model.Subscriber;

import java.util.Map;

/**
 * simple {@link Subscriber} implementation. The Service will be managed by DS / SCR
 * 
 * @author rguderlei
 *
 */
@Component( name = "simple_subscriber", designateFactory = SimpleSubscriber.Config.class, configurationPolicy = ConfigurationPolicy.require)
public class SimpleSubscriber implements Subscriber {

    interface Config  {
        String prefix();
    }

	private LogService log;
    private Config config;
	
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
    public void activate(Map<?,?> props) {
        config = Configurable.createConfigurable(Config.class, props);
        log.log(LogService.LOG_INFO, "service activated");
    }

    @Modified
    public void modified(Map<?,?> props) {
        config = Configurable.createConfigurable(Config.class, props);
        log.log(LogService.LOG_INFO, "service updated");
    }

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void receive(Message message) {
		log.log(LogService.LOG_INFO, String.format("SCR Subscriber: %s", message.toString()));
	}

}
