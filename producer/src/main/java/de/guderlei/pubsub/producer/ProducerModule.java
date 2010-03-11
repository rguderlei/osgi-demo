package de.guderlei.pubsub.producer;

import static org.ops4j.peaberry.Peaberry.service;
import com.google.inject.AbstractModule;

import de.guderlei.pubsub.model.Producer;

public class ProducerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Producer.class).toProvider(service(Producer.class).single());
	}

}
