package com.github.dearrudam.helloakka;

import akka.actor.ActorRef;
import akka.testkit.javadsl.TestKit;
import com.github.dearrudam.helloakka.Greeter.Greet;
import com.github.dearrudam.helloakka.Greeter.WhoToGreet;
import com.github.dearrudam.helloakka.Printer.Greeting;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * GreeterTest
 */
public class GreeterTest extends BaseTest{

    @Test
    public void testGreeterActorSendingOfGreeting() {
        final TestKit testProbe = new TestKit(system);
        final ActorRef greeter = system.actorOf(Greeter.props("Hello", testProbe.getRef()));
        greeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());
        greeter.tell(new Greet(), ActorRef.noSender());
        Greeting greeting = testProbe.expectMsgClass(Greeting.class);
        assertEquals("Hello, Akka", greeting.message);
    }

}