package com.github.dearrudam.helloakka;

import com.github.dearrudam.helloakka.Printer.Greeting;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * Greeter
 */
class Greeter extends AbstractActor {

    public static Props props(String message, ActorRef printerActor) {
        return Props.create(Greeter.class, () -> new Greeter(message, printerActor));
    }

    private final String message;
    private final ActorRef printerActor;
    private String greeting = "";

    private Greeter(String message, ActorRef printerActor) {
        this.message = message;
        this.printerActor = printerActor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WhoToGreet.class, this::prepareGreetingMessage)
                .match(Greet.class, this::sendToPrinter)
                .build();
    }

    private void prepareGreetingMessage(WhoToGreet wtg) {
        this.greeting = message + ", " + wtg.who;
    }

    private void sendToPrinter(Greet g) {
        this.printerActor.tell(new Greeting(this.greeting), getSelf());
    }

    // Message Types
    public static class WhoToGreet {

        final String who;

        public WhoToGreet(String who) {
            this.who = who;
        }

    }

    public static class Greet {
        public Greet() {
        }
    }

}