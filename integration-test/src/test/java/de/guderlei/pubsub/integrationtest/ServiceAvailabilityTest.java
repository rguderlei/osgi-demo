package de.guderlei.pubsub.integrationtest;

import java.io.File;

import javax.inject.Inject;
import javax.servlet.Servlet;

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
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;


import de.guderlei.pubsub.model.Producer;
import de.guderlei.pubsub.model.Subscriber;
import de.guderlei.pubsub.producer.PublisherServlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.*;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class ServiceAvailabilityTest {
    @Inject
    BundleContext bundleContext;

    @Configuration
    public Option[] configure() {
        return options( dsProfile(), configProfile(),
                rawPaxRunnerOption("http.proxyHost", "proxy"),
                rawPaxRunnerOption("http.proxyPort", "3128"),
                systemPackages("javax.inject", "org.aopalliance.intercept; version=\"1.0\"", "org.aopalliance.aop; version=\"1.0\""),
                provision(
                        mavenBundle().groupId("com.google.inject").artifactId("guice").version("3.0").start(),
                        mavenBundle().groupId("org.ops4j").artifactId("peaberry").version("1.2").start(),
                        mavenBundle().groupId("org.ops4j.peaberry.extensions").artifactId("peaberry.activation").version("1.2").start(),
                        mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.log").version("1.0.1").start(),
                        mavenBundle( "org.apache.felix", "org.apache.felix.scr", "1.6.0" ).start(),
                        mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.http.bundle").version("2.2.0").start(),
                        mavenBundle().groupId("de.guderlei.osgidemo").artifactId("model").version("1.0.0").start(),
                        mavenBundle().groupId("de.guderlei.osgidemo").artifactId("ds_subscriber").version("1.0.0").start(),
                        mavenBundle().groupId("de.guderlei.osgidemo").artifactId("peaberry_subscriber").version("1.0.0").start(),
                        mavenBundle().groupId("de.guderlei.osgidemo").artifactId("message-hub").version("1.0.0").start(),
                        mavenBundle().groupId("de.guderlei.osgidemo").artifactId("producer").version("1.0.0").start()
                        //bundle(new File("./../lib/runtime/aopalliance-1.0.jar").toURI().toString())
                ), junitBundles());
    }

    @Test
    public void bundle_context_available() {
        assertNotNull(bundleContext);
    }

    @Test
    public void subscriber_bundles_are_started() {
        boolean ds = false;
        boolean peaberry = false;
        for (Bundle bundle : bundleContext.getBundles()) {
            if (bundle.getSymbolicName().equals("de.guderlei.osgidemo.ds_subscriber")) {
                ds = true;
                assertEquals(bundle.getState(), Bundle.ACTIVE);
            }
            if (bundle.getSymbolicName().equals("de.guderlei.osgidemo.peaberry_subscriber")) {
                peaberry = true;
                assertEquals(bundle.getState(), Bundle.ACTIVE);
            }
        }
        assertTrue(peaberry);
        assertTrue(ds);
    }

    @Test
    public void peaberry_activation_is_started() throws BundleException {
        for (Bundle bundle : bundleContext.getBundles()) {
            if (bundle.getSymbolicName().equals("org.ops4j.peaberry.activation")) {
                // equinox seems to start the bundle on demand ...
                bundle.start();
                assertEquals("peaberry bundle not active", Bundle.ACTIVE, bundle.getState());
            }
        }
    }

//	@Test
//	public void two_subscriber_services_are_available() throws BundleException, InvalidSyntaxException {
//			//equinox seems to start some bundles on demand ...
//			for(Bundle bundle: bundleContext.getBundles()){
//				try{
//					bundle.start();
//				} catch (Exception e) {
//					System.out.println(bundle.getSymbolicName() + " could not be started");
//				}
//			}
//			
//			ServiceReference[] subscribers = bundleContext.getServiceReferences(Subscriber.class.getName(), null);
//			assertNotNull("no subscribers found", subscribers);			
//			assertEquals(2, subscribers.length);		
//	}

    @Test
    public void one_producer_service_is_available() throws InterruptedException, BundleException {
        //equinox seems to start some bundles on demand ...
        for (Bundle bundle : bundleContext.getBundles()) {
            try {
                bundle.start();
            } catch (Exception e) {
                System.out.println(bundle.getSymbolicName() + " could not be started");
            }
        }

        ServiceReference ref = bundleContext.getServiceReference(Producer.class.getName());
        assertNotNull(ref);
        Producer prod = (Producer) bundleContext.getService(ref);
        assertNotNull(prod);
        assertEquals("de.guderlei.pubsub.hub.MessageHub", prod.getClass().getName());

        bundleContext.ungetService(ref);
    }

    /**
     * Checks whether one {@link PublisherServlet} is registered
     *
     * @throws BundleException
     */
    @Test
    public void one_servlet_is_registered() throws InterruptedException, BundleException {
        //equinox seems to start some bundles on demand ...
        for (Bundle bundle : bundleContext.getBundles()) {
            try {
                bundle.start();
            } catch (Exception e) {
                System.out.println(bundle.getSymbolicName() + " could not be started");
            }
        }

        ServiceReference ref = bundleContext.getServiceReference(Servlet.class.getName());
        assertNotNull(ref);
        Servlet srvlt = (Servlet) bundleContext.getService(ref);
        assertNotNull(srvlt);
        assertEquals("PublisherServlet", srvlt.getClass().getSimpleName());

        bundleContext.ungetService(ref);
    }
}
