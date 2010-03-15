package de.guderlei.pubsub.producer;

import static org.ops4j.peaberry.Peaberry.service;

import javax.servlet.Servlet;

import com.google.inject.AbstractModule;

import de.guderlei.pubsub.model.Producer;

/**Guice-Config for the PublisherServlet bundle
 * @author rguderlei
 *
 */
public class ProducerModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configure() {
		// bind Producer to osgi service
		bind(Producer.class).toProvider(service(Producer.class).single());
		// binding for the creation of the PublisherServlet instance via guice
		bind(Servlet.class).to(PublisherServlet.class);
	}

}
