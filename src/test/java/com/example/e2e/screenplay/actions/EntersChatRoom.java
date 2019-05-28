package com.example.e2e.screenplay.actions;

import com.example.e2e.screenplay.page.StartPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import org.openqa.selenium.Keys;

public class EntersChatRoom implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(Open.browserOn(new StartPage()));
    actor.attemptsTo(Enter.theValue(actor.getName())
        .into(".e2e-name-input")
        .thenHit(Keys.ENTER));
  }
}
