package de.guderlei.pubsub.hub.activator;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import de.guderlei.pubsub.model.Producer;

public class Activator implements BundleActivator {

	@Inject
	private Export<Producer> hub;
	
	@Override
	public void start(BundleContext context) throws Exception {
		/* Setup Guice */
	    Injector inj = Guice.createInjector(Peaberry.osgiModule(context), new HubModule());

	    /* Create bundle content */
	    inj.injectMembers(this);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		hub.unput();
	}

}
