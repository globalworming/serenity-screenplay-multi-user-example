package com.example.websocketdemo.screenplay.questions;

import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.screenplay.actor.Memories;
import net.serenitybdd.screenplay.Actor;

public class TheLatestMessageReceived extends QuestionWithDefaultSubject<ChatMessage> {
  @Override
  public ChatMessage answeredBy(Actor actor) {
    return actor.recall(Memories.LATEST_CHAT_MESSAGE);
  }

}
