package com.example.e2e.testcases.chat;

import com.example.e2e.TestBase;
import com.example.e2e.screenplay.actions.EntersChatRoom;
import com.example.e2e.screenplay.actions.PostMessage;
import com.example.e2e.screenplay.questions.ChatMessages;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.givenThat;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.GivenWhenThen.then;
import static net.serenitybdd.screenplay.GivenWhenThen.when;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(SerenityRunner.class)
@Narrative( text = "" +
    "When in a chat session, the customer send and receive text messages"
)public class ChatMessagesTest extends TestBase {


  @Test
  public void postMessage() {
    givenThat(dana).wasAbleTo(new EntersChatRoom());
    givenThat(bob).wasAbleTo(new EntersChatRoom());
    when(bob).attemptsTo(PostMessage.sayingHello());

    then(dana).should(eventually(seeThat(
        new ChatMessages(), hasItem(containsString("Bob\nHello!"))
    )));

  }
}
