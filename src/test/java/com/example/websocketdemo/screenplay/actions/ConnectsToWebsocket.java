package com.example.websocketdemo.screenplay.actions;

import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.screenplay.actor.Memories;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ConnectsToWebsocket implements Performable {
  private final int port;

  public ConnectsToWebsocket(int port) {
    this.port = port;
  }

  public static ConnectsToWebsocket onPort(int port) {
    return instrumented(ConnectsToWebsocket.class, port);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
    List<Transport> transports = new ArrayList<>(1);
    transports.add(new WebSocketTransport(simpleWebSocketClient));
    SockJsClient sockJsClient = new SockJsClient(transports);
    WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
    stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    String url = "ws://localhost:" + port + "/ws";
    MyStompSessionHandler sessionHandler = new MyStompSessionHandler(actor);
    try {
      stompClient.connect(url, sessionHandler).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private class MyStompSessionHandler extends StompSessionHandlerAdapter {


    private final Actor actor;
    private StompSession session;

    public <T extends Actor> MyStompSessionHandler(T actor) {
      this.actor = actor;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
      session.subscribe("/topic/public", this);
      session.send("/app/chat.addUser", ChatMessage.join(actor.getName()));
      this.session = session;
      actor.remember(Memories.WEBSOCKET_SESSION, session);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
      return ChatMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
      actor.remember(Memories.LATEST_CHAT_MESSAGE, payload);
    }

  }
}
