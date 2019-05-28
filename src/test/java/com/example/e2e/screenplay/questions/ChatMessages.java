package com.example.e2e.screenplay.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class ChatMessages implements Question<List<String>> {

  @Override
  public List<String> answeredBy(Actor actor) {
    return Target.the("chat messages")
        .located(By.cssSelector(".e2e-chat-message"))
        .resolveAllFor(actor).stream()
        .map(WebElementFacade::getText)
        .collect(Collectors.toList());
  }
}
