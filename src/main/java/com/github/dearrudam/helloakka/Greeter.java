package com.github.dearrudam.helloakka;

import com.github.dearrudam.helloakka.Printer.Greeting;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * Greeter
 */
public class Greeter extends AbstractActor {

    public static Props props(String message, ActorRef printerActor) {
        return Props.create(Greeter.class, () -> new Greeter(message, printerActor));
    }

    private final String message;
    private final ActorRef printerActor;
    private String greeting = "";

    public Greeter(String message, ActorRef printerActor) {
        this.message = message;
        this.printerActor = printerActor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(WhoToGreet.class, (wtg) -> {
            this.greeting = message + ", " + wtg.who;
        }).match(Greet.class, (g) -> {
            this.printerActor.tell(new Greeting(this.greeting), getSelf());
        }).build();
    }

    // Message Types
    public static class WhoToGreet {

        public final String who;

        public WhoToGreet(String who) {
            this.who = who;
        }

    }

    public static class Greet {
        public Greet() {}
    }

}