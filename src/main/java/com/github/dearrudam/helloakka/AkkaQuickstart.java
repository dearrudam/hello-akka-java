package com.github.dearrudam.helloakka;

import java.io.IOException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * AkkaQuickstart
 */
public class AkkaQuickstart {

    public static void main(String[] args) {

        final ActorSystem system = ActorSystem.create("helloakkajava");
        try {
            final ActorRef printerActor = system.actorOf(Printer.props(), "printerActor");
            final ActorRef portugueseGreeter = system.actorOf(Greeter.props("Olá", printerActor), "portugueseGreeter");
            final ActorRef englishGreeter = system.actorOf(Greeter.props("Hello", printerActor), "englishGreeter");

            portugueseGreeter.tell(new Greeter.WhoToGreet("Max"), ActorRef.noSender());
            portugueseGreeter.tell(new Greeter.Greet(), ActorRef.noSender());

            englishGreeter.tell(new Greeter.WhoToGreet("Max"), ActorRef.noSender());
            englishGreeter.tell(new Greeter.Greet(), ActorRef.noSender());

            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
            system.terminate();
        }

    }

}