package de.guderlei.pubsub.producer;

import java.util.Hashtable;

import javax.servlet.Servlet;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.google.inject.Guice;
import com.google.inject.Injector;


public class Activator implements BundleActivator {

	private ServiceRegistration registration;
	
	@Override
	public void start(BundleContext context) throws Exception {
		/* Setup Guice */
	    Injector inj = Guice.createInjector(Peaberry.osgiModule(context), new ProducerModule());
	    /* Create bundle content */
	    
	    Hashtable<String, String> props = new Hashtable<String, String>();
	    props.put("alias", "/hello");
	    props.put("init.message", "PublisherServlet registered");
	    
	    PublisherServlet servlet = inj.getInstance(PublisherServlet.class);
	    
	    registration = context.registerService(Servlet.class.getName(), servlet,props);
	    
	    	    
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
	}

}
