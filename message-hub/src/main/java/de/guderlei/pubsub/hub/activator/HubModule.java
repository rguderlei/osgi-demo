package de.guderlei.pubsub.hub.activator;

import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;

import de.guderlei.pubsub.hub.MessageHub;
import de.guderlei.pubsub.model.Producer;
import de.guderlei.pubsub.model.Subscriber;
import static org.ops4j.peaberry.util.TypeLiterals.*;
import static org.ops4j.peaberry.Peaberry.*;


public class HubModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(export(Producer.class)).toProvider(service(MessageHub.class).export());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(iterable(Subscriber.class)).toProvider(service(Subscriber.class).multiple());
	}
}
