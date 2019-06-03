package com.example.websocketdemo.screenplay.abilities;

import net.serenitybdd.screenplay.Ability;

public abstract class AbilityWithDefaultDescription implements Ability {

  @Override
  public String toString() {
    return this.getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1 $2");
  }
}
