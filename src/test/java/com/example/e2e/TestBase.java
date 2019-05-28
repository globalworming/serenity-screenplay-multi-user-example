package com.example.e2e;


import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.ClearCookiesPolicy;
import net.thucydides.core.annotations.Managed;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.givenThat;

public class TestBase {

  @Managed(uniqueSession = true, clearCookies = ClearCookiesPolicy.BeforeEachTest)
  private WebDriver aBrowser;

  @Managed(uniqueSession = true, clearCookies = ClearCookiesPolicy.BeforeEachTest)
  private WebDriver aSecondBrowser;

  public Actor dana = new Actor("Dana");
  public Actor bob = new Actor("Bob");


  @Before
  public void setUp() {
    givenThat(dana).can(BrowseTheWeb.with(aBrowser));
    givenThat(bob).can(BrowseTheWeb.with(aSecondBrowser));
  }
}
