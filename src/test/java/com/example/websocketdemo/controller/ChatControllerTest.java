package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.screenplay.abilities.ConnectToWebsockets;
import com.example.websocketdemo.screenplay.actions.ConnectsToWebsocket;
import com.example.websocketdemo.screenplay.actions.SendsChatMessage;
import com.example.websocketdemo.screenplay.questions.TheLatestMessageReceived;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Consequence;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.actors.OnStage.withCurrentActor;
import static org.hamcrest.core.IsEqual.equalTo;

// Todo, remove redundancy in another actor joined 
// Add package.info and @narrative 
// Maybe try markdown resources 

@RunWith(SerenityRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerTest {

  @Rule
  public SpringIntegrationMethodRule springIntegrationMethodRule = new SpringIntegrationMethodRule();

  @LocalServerPort
  private int port;

  @Autowired
  private ChatController chatController;

  @Before
  public void prepareTests() {
    Cast cast = new Cast();
    cast.actorNamed("Dana", new ConnectToWebsockets());
    cast.actorNamed("Bob", new ConnectToWebsockets());
    Stage stage = setTheStage(cast);
    stage.shineSpotlightOn("Dana");
    withCurrentActor(ConnectsToWebsocket.onPort(port));
    theActorInTheSpotlight().should(eventually(joinWebsocketSession()));
  }

  @Test
  public void anotherActorJoins() {
    Actor dana = theActorCalled("Dana");
    dana.should(eventually(joinWebsocketSession()));

    Actor bob = theActorCalled("Bob");
    bob.attemptsTo(ConnectsToWebsocket.onPort(port));
    bob.should(eventually(joinWebsocketSession()));

    dana.should(eventually(seeThatBobJoined()));
  }


  @Test
  public void sendMessage() {
    ChatMessage expectedMessage = ChatMessage.chat(theActorInTheSpotlight().getName(), "hello");

    theActorInTheSpotlight().attemptsTo(SendsChatMessage.saying("hello"));
    theActorInTheSpotlight().should(eventually(seeThat(
        new TheLatestMessageReceived(), equalTo(expectedMessage))));
  }

  @Test
  public void sendExplicitMessage() {
    ChatMessage expectedMessage = ChatMessage.chat("Dana", "fuck");
    theActorInTheSpotlight().attemptsTo(SendsChatMessage.saying("fuck"));

    theActorInTheSpotlight().should(eventually(seeThat(
        new TheLatestMessageReceived(), equalTo(expectedMessage))));
  }

  @Test
  @DirtiesContext
  public void explicitMessageIsFiltered() {
    ChatMessage expectedMessage = ChatMessage.chat("Dana", "***");
    chatController.enableSwearWordFilter();
    theActorInTheSpotlight().attemptsTo(SendsChatMessage.saying("fuck"));
    theActorInTheSpotlight().should(eventually(seeThat(
        new TheLatestMessageReceived(), equalTo(expectedMessage))));
  }

  private Consequence<ChatMessage> joinWebsocketSession() {
    return seeThat(new TheLatestMessageReceived(),
        equalTo(ChatMessage.join(theActorInTheSpotlight().getName())));
  }

  private Consequence<ChatMessage> seeThatBobJoined() {
    return seeThat(new TheLatestMessageReceived(),
        equalTo(ChatMessage.join("Bob")));
  }

}
