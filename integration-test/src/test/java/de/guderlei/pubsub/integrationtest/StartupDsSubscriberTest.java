package de.guderlei.pubsub.integrationtest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.ops4j.pax.exam.CoreOptions.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.framework.*;

import de.guderlei.pubsub.model.Subscriber;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;


import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class StartupDsSubscriberTest {
	@Inject
	BundleContext bundleContext;

    @Inject
    ConfigurationAdmin configurationAdmin;
	
	@Configuration
	public Option[] configure() {
		return options(
                provision(
                //mavenBundle().groupId("org.apache.servicemix.bundles").artifactId("org.apache.servicemix.bundles.junit").version("3.8.2_4").start(),
                mavenBundle().groupId("biz.aQute").artifactId("bndlib").version("1.43.0").start(),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.log").version("1.0.1").start(),
                mavenBundle( "org.apache.felix", "org.apache.felix.scr", "1.4.0" ).start(),
                mavenBundle( "org.apache.felix", "org.apache.felix.configadmin", "1.2.8" ).start(),
                mavenBundle( "org.apache.felix", "org.apache.felix.metatype", "1.0.4" ).start(),
                mavenBundle().groupId( "de.guderlei.osgidemo" ).artifactId( "model" ).version( "1.0.0" ).start(),
				mavenBundle().groupId( "de.guderlei.osgidemo" ).artifactId( "ds_subscriber" ).version( "1.0.0" ).start()

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
	 */
	@Test
	public void one_subscriber_is_registered() throws InterruptedException, IOException, InvalidSyntaxException {
        assertThat(configurationAdmin, is(notNullValue()));
        org.osgi.service.cm.Configuration config =  configurationAdmin.createFactoryConfiguration("simple_subscriber", null);
        Properties props = new Properties();
        props.put("prefix", "foo");
        config.update(props);

      /*  System.out.println("config created");
        System.out.println(config.getFactoryPid());
        System.out.println(config.getPid());
        System.out.println(config.getBundleLocation());
        System.out.println(config.getProperties());    */


        ServiceTracker serviceTracker = new ServiceTracker(bundleContext, Subscriber.class.getName(), null);
        serviceTracker.open();
        Subscriber subscriber = (Subscriber) serviceTracker.waitForService(1000);
        assertThat(subscriber, is(notNullValue()));

		assertEquals("de.guderlei.pubsub.subscriber.SimpleSubscriber", subscriber.getClass().getName());
	}



    /**
     * Checks whether one {@link Subscriber} is registered
     */
    @Test
    public void requesting_services_with_different_configurations_leads_to_different_objects() throws InterruptedException, IOException {
        org.osgi.service.cm.Configuration config =  configurationAdmin.createFactoryConfiguration("simple_subscriber", null);
        Properties props = new Properties();
        props.put("prefix", "foo");
        config.update(props);

        config =  configurationAdmin.createFactoryConfiguration("simple_subscriber", null);
        props = new Properties();
        props.put("prefix", "bar");
        config.update(props);

        ServiceTracker serviceTracker = new ServiceTracker(bundleContext, Subscriber.class.getName(), null);
        serviceTracker.open();

        Object[] services = serviceTracker.getServices();
        assertEquals(services.length, 2);
        assertNotNull(services[0]);
        assertNotNull(services[1]);
        assertFalse(services[0] == services[1]);
    }



}
