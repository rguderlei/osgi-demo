package de.guderlei.pubsub.producer;

import java.util.Hashtable;

import javax.servlet.Servlet;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.google.inject.Guice;
import com.google.inject.Injector;


/**Activator ofthe  PublisherServlet Bundle
 * @author rguderlei
 *
 */
public class Activator implements BundleActivator {

	private ServiceRegistration registration;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		// Setup Guice 
	    Injector inj = Guice.createInjector(Peaberry.osgiModule(context), new ProducerModule());
	    // create Servlet instance via Guice
	    Servlet servlet = inj.getInstance(Servlet.class);
	    
	    //Configure and register service
	    Hashtable<String, String> props = new Hashtable<String, String>();
	    props.put("alias", "/hello");
	    props.put("init.message", "PublisherServlet registered");
	    registration = context.registerService(Servlet.class.getName(), servlet,props);    	    
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
	}

}
