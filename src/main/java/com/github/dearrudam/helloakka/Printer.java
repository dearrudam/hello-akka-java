package com.github.dearrudam.helloakka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.util.Optional;

/**
 * Printer
 */
public class Printer extends AbstractActor {

    private final ActorRef actorRef;

    private Printer(ActorRef actorRef) {
        this.actorRef = actorRef;
    }

    public static Props props(ActorRef actorRef) {
        return Props.create(Printer.class, () -> new Printer(actorRef));
    }

    public static Props props() {
        return props(null);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Greeting.class, this::print).build();
    }

    private void print(final Greeting greeting) {
        System.out.println(greeting.message);
        Optional.ofNullable(this.actorRef).ifPresent((actorRef1 -> actorRef1.tell(greeting, getSelf())));
    }

    public static class Greeting {

        public final String message;

        public Greeting(String message) {
            this.message = message;
        }
    }
}