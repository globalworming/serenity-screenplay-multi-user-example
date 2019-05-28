package com.example.e2e.screenplay.actions;

import com.example.e2e.screenplay.page.StartPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Open;

public class LeavesChatRoom implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(Open.browserOn(new StartPage()));
  }
}
