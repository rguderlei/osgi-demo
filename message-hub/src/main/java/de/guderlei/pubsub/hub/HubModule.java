package de.guderlei.pubsub.hub;

import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;

import de.guderlei.pubsub.hub.MessageHub;
import de.guderlei.pubsub.model.Producer;
import de.guderlei.pubsub.model.Subscriber;
import static org.ops4j.peaberry.util.TypeLiterals.*;
import static org.ops4j.peaberry.Peaberry.*;


/**Guice/Peaberry config for the MessageHub bundle.
 * @author rguderlei
 *
 */
public class HubModule extends AbstractModule {
	
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	protected void configure() {
		// export the MessageHub as Producer service
		bind(export(Producer.class)).toProvider(service(MessageHub.class).export());
		// import the LogService via osgi
		bind(LogService.class).toProvider(service(LogService.class).single());
		// import all available Subscriber services
		bind(iterable(Subscriber.class)).toProvider(service(Subscriber.class).multiple());
	}
}
