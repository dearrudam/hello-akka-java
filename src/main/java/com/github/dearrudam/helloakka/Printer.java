package com.github.dearrudam.helloakka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * Printer
 */
public class Printer extends AbstractActor {

    public static Props props(ActorRef actorRef) {
        return Props.create(Printer.class, () -> new Printer(actorRef));
    }

    private final ActorRef requestorRef;

    public Printer(ActorRef requestorRef) {
        this.requestorRef = requestorRef;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Greeting.class, (greeting) -> {
            System.out.println(greeting.message);
            if(this.requestorRef!=null)
                this.requestorRef.tell(greeting, getSelf());
        }).build();
    }

    public static class Greeting {

        public final String message;

        public Greeting(String message) {
            this.message = message;
        }
    }
}