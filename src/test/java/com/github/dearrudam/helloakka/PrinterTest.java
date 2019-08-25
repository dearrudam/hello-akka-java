package com.github.dearrudam.helloakka;

import static org.junit.Assert.assertEquals;

import com.github.dearrudam.helloakka.Printer.Greeting;

import org.junit.Test;

import akka.actor.ActorRef;
import akka.testkit.javadsl.TestKit;

/**
 * PrinterTest
 */
public class PrinterTest extends BaseTest {

    @Test
    public void testGreeterActorSendingOfGreeting() {
        final TestKit testProbe = new TestKit(system);
        final ActorRef printer = system.actorOf(Printer.props(testProbe.getRef()));
        printer.tell(new Greeting("Hello, Akka"), ActorRef.noSender());
        Greeting greeting = testProbe.expectMsgClass(Greeting.class);
        assertEquals("Hello, Akka", greeting.message);
    }
}