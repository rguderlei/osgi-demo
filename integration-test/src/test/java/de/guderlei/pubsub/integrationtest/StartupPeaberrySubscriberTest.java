package de.guderlei.pubsub.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import de.guderlei.pubsub.model.Subscriber;

import javax.inject.Inject;


@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class StartupPeaberrySubscriberTest {
	@Inject
	BundleContext bundleContext;
	
	@Configuration
	public Option[] configure() {
		return options(
                systemPackages("org.aopalliance.intercept; version=\"1.0\"", "org.aopalliance.aop; version=\"1.0\""),
				provision(
                        mavenBundle().groupId("com.google.inject").artifactId("guice").version("3.0").start(),
                        mavenBundle().groupId("org.ops4j").artifactId("peaberry").version("1.2").start(),
                        mavenBundle().groupId("org.ops4j.peaberry.extensions").artifactId("peaberry.activation").version("1.2").start(),
                        mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.log").version("1.0.1").start(),
						mavenBundle().groupId( "de.guderlei.osgidemo" ).artifactId( "model" ).version( "1.0.0" ).start(),
						mavenBundle().groupId( "de.guderlei.osgidemo" ).artifactId( "peaberry_subscriber" ).version( "1.0.0" ).start()
		), junitBundles());
	}

    @Before
       public void before() throws BundleException{
           for(Bundle bundle: bundleContext.getBundles()){
                       bundle.start();
                       System.out.println("Bundle " + bundle.getSymbolicName() + " started");
           }
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
