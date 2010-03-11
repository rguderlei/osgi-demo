package de.guderlei.pubsub.subscriber;

import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;

import de.guderlei.pubsub.model.Subscriber;
import static org.ops4j.peaberry.util.TypeLiterals.*;
import static org.ops4j.peaberry.Peaberry.*;

public class Module extends AbstractModule {

	@Override
	protected void configure() {
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(export(Subscriber.class)).toProvider(service(PeaberrySubscriber.class).export());
	}

}
