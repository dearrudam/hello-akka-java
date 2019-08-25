package com.github.dearrudam.helloakka;

import static org.junit.Assert.assertEquals;

import com.github.dearrudam.helloakka.Greeter.Greet;
import com.github.dearrudam.helloakka.Greeter.WhoToGreet;
import com.github.dearrudam.helloakka.Printer.Greeting;

import org.junit.Test;

import akka.actor.ActorRef;
import akka.testkit.javadsl.TestKit;

/**
 * GreeterTest
 */
public class GreeterTest extends BaseTest{

    @Test
    public void testGreeterActorSendingOfGreeting() {
        final TestKit testProbe = new TestKit(system);
        final ActorRef helloGreeter = system.actorOf(Greeter.props("Hello", testProbe.getRef()));
        helloGreeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());
        helloGreeter.tell(new Greet(), ActorRef.noSender());
        Greeting greeting = testProbe.expectMsgClass(Greeting.class);
        assertEquals("Hello, Akka", greeting.message);
    }

}