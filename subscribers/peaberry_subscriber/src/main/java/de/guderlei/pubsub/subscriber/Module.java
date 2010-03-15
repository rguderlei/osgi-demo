package de.guderlei.pubsub.subscriber;

import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;

import de.guderlei.pubsub.model.Subscriber;
import static org.ops4j.peaberry.util.TypeLiterals.*;
import static org.ops4j.peaberry.Peaberry.*;

/**
 * Guice config for the peaberry/guice managed service bundle
 * 
 * @author rguderlei
 *
 */
public class Module extends AbstractModule {

	@Override
	protected void configure() {
		// bind LogService to an osgi service
		bind(LogService.class).toProvider(service(LogService.class).single());
		// export the PeaberrySubscriber as an osgi Subscriber service
		bind(export(Subscriber.class)).toProvider(service(PeaberrySubscriber.class).export());
	}

}
