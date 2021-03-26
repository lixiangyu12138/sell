package com.liwenwen.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {
    private Session session;
    private static CopyOnWriteArraySet<WebSocket> wsSet= new CopyOnWriteArraySet<>();
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        wsSet.add(this);
        log.info("【websocket消息】有新的连接，总数:{}",wsSet.size());

    }
    @OnClose
    public void onClose(){
        wsSet.remove(this);
        log.info("【websocket消息】连接断开，总数{}",wsSet.size());
    }
    @OnMessage
    public void onMessage(String msg){
        log.info("【websocket消息】广播消息 message={}",msg);
    }
    public void sendMessage(String msg){
        for(WebSocket webSocket: wsSet){
            log.info("【websocket消息】广播消息 message={}",msg);
            try{
                webSocket.session.getBasicRemote().sendText(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
