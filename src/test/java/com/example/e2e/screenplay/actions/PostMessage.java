package com.example.e2e.screenplay.actions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.Keys;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PostMessage implements Performable {

  private final String message;

  public PostMessage(String message) {
    this.message = message;
  }

  public static PostMessage sayingHello() {
    return instrumented(PostMessage.class, "Hello!");
  }


  @Override
  @Step("{0} posts a message saying \"#message\"")
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(Enter.theValue(message).into(".e2e-message-input").thenHit(Keys.ENTER));
  }
}
