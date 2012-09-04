package de.guderlei.pubsub.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import de.guderlei.pubsub.model.Subscriber;

import javax.inject.Inject;

@RunWith(JUnit4TestRunner.class)
public class StartupDsSubscriberTest {
	@Inject
	BundleContext bundleContext;
	
	@Configuration
	public Option[] configure() {
		return options(dsProfile(), configProfile(),
				rawPaxRunnerOption("http.proxyHost", "proxy"),
				rawPaxRunnerOption("http.proxyPort", "3128"),
                provision(
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.log").version("1.0.1").start(),
                mavenBundle( "org.apache.felix", "org.apache.felix.scr", "1.6.0" ).start(),
				mavenBundle().groupId( "de.guderlei.osgidemo" ).artifactId( "model" ).version( "1.0.0" ).start(),
				mavenBundle().groupId( "de.guderlei.osgidemo" ).artifactId( "ds_subscriber" ).version( "1.0.0" ).start()

		), junitBundles());
	}
	
	/**
	 * Checks whether one {@link Subscriber} is registered
	 */
	@Test
	public void one_subscriber_is_registered()throws InterruptedException{
		ServiceReference ref = bundleContext.getServiceReference(Subscriber.class.getName());
		assertNotNull(ref);
		Subscriber srvc = (Subscriber) bundleContext.getService(ref);
		assertNotNull(srvc);
		assertEquals("de.guderlei.pubsub.subscriber.SimpleSubscriber", srvc.getClass().getName());
		bundleContext.ungetService(ref);
	}

    /**
     * Checks whether one {@link Subscriber} is registered
     */
    @Test
    public void requesting_services_leads_to_different_objects()throws InterruptedException{
        ServiceReference ref = bundleContext.getServiceReference(Subscriber.class.getName());
        assertNotNull(ref);
        Subscriber srvc1 = (Subscriber) bundleContext.getService(ref);

        ServiceReference ref2 = bundleContext.getServiceReference(Subscriber.class.getName());
        assertNotNull(ref2);

        Subscriber srvc2 = (Subscriber) bundleContext.getService(ref2);

        assertFalse(srvc1 == srvc2);

        bundleContext.ungetService(ref);
        bundleContext.ungetService(ref2);

    }


	
}
