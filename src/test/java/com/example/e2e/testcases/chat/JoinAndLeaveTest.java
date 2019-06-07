package com.example.e2e.testcases.chat;

import com.example.e2e.TestBase;
import com.example.e2e.screenplay.actions.EntersChatRoom;
import com.example.e2e.screenplay.actions.LeavesChatRoom;
import com.example.e2e.screenplay.questions.EventMessages;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.GivenWhenThen.then;
import static net.serenitybdd.screenplay.GivenWhenThen.when;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;

@RunWith(SerenityRunner.class)
@Narrative( text = "" +
    "When in a chat session, the customer notices different events occurring in the chat by getting a message."
)public class JoinAndLeaveTest extends TestBase {

  @Test
  public void joinMessageWhenJoining() {
    when(dana).attemptsTo(new EntersChatRoom());
    then(dana).should(eventually(seeThat(
        new EventMessages(), hasItem("Dana joined!")
    )));
  }

  @Test
  public void joinMessageWhenSomeoneElseJoins() {
    when(dana).attemptsTo(new EntersChatRoom());
    when(bob).attemptsTo(new EntersChatRoom());
    then(dana).should(eventually(seeThat(new EventMessages(), hasItem("Bob joined!"))));
    then(bob).should(eventually(seeThat(new EventMessages(), not(hasItem("Dana joined!")))));
  }

  @Test
  public void noPreviousJoinMessage() {
    when(dana).attemptsTo(new EntersChatRoom());
    when(bob).attemptsTo(new EntersChatRoom());
    then(bob).should(eventually(seeThat(new EventMessages(), not(hasItem("Dana joined!")))));
  }

  @Test
  public void leaveMessage() {
    when(dana).attemptsTo(new EntersChatRoom());
    when(bob).attemptsTo(new EntersChatRoom());
    when(bob).attemptsTo(new LeavesChatRoom());
    then(dana).should(eventually(seeThat(
        new EventMessages(), hasItem("Bob left!")
    )));
  }
}
