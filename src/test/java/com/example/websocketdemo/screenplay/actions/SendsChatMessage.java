package com.example.websocketdemo.screenplay.actions;

import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.screenplay.actor.Memories;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.springframework.messaging.simp.stomp.StompSession;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SendsChatMessage implements Performable {
  private final String message;

  public SendsChatMessage(String message) {
    this.message = message;
  }

  public static SendsChatMessage saying(String s) {
    return instrumented(SendsChatMessage.class, s);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    StompSession session = actor.recall(Memories.WEBSOCKET_SESSION);
    session.send("/app/chat.sendMessage", ChatMessage.chat(actor.getName(), message));

  }
}
