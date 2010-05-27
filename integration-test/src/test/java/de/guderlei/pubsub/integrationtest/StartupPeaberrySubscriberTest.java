package de.guderlei.pubsub.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.configProfile;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.dsProfile;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.rawPaxRunnerOption;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import de.guderlei.pubsub.model.Subscriber;


@RunWith(JUnit4TestRunner.class)
public class StartupPeaberrySubscriberTest {
	@Inject
	BundleContext bundleContext;
	
	@Configuration
	public Option[] configure() {
		return options(mavenConfiguration(),equinox(), dsProfile(), configProfile(), 
				rawPaxRunnerOption("http.proxyHost", "proxy"),
				rawPaxRunnerOption("http.proxyPort", "3128"),
				provision(
						mavenBundle().groupId( "de.guderlei.osgidemo" ).artifactId( "model" ).version( "1.0.0" ),
						mavenBundle().groupId( "de.guderlei.osgidemo" ).artifactId( "peaberry_subscriber" ).version( "1.0.0" ),
				bundle(new File("./../lib/compile/jsr305-1.3.9.jar").toURI().toString()),
				bundle(new File("./../lib/compile/org.apache.felix.log-1.0.0.jar").toURI().toString()),
				bundle(new File("./../lib/runtime/aopalliance-1.0.jar").toURI().toString()),
				bundle(new File("./../lib/compile/guice-2.0.jar").toURI().toString()),
				bundle(new File("./../lib/compile/peaberry-1.1.1.jar").toURI().toString()),
				bundle(new File("./../lib/runtime/peaberry.activation-1.2-SNAPSHOT.jar").toURI().toString())
		));
	}
	
	/**
	 * Checks whether one {@link Subscriber} is registered
	 * @throws BundleException 
	 */
	@Test
	public void one_subscriber_is_registered()throws InterruptedException, BundleException{
		//equinox seems to start some bundles on demand ...
		for(Bundle bundle: bundleContext.getBundles()){
			try{
				bundle.start();
			} catch (Exception e) {
				System.out.println(bundle.getSymbolicName() + " could not be started");
			}
		}
		
		ServiceReference ref = bundleContext.getServiceReference(Subscriber.class.getName());
		assertNotNull(ref);
		Subscriber srvc = (Subscriber) bundleContext.getService(ref);
		assertNotNull(srvc);
		assertEquals("de.guderlei.pubsub.subscriber.PeaberrySubscriber", srvc.getClass().getName());
		bundleContext.ungetService(ref);
	}
}
