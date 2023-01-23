package org.dorkmaster.mandlebrot;

import org.dorkmaster.mandlebrot.common.math.ComplexNumber;
import org.dorkmaster.mandlebrot.common.render.Pixel;
import org.dorkmaster.mandlebrot.common.render.RenderContext;
import org.dorkmaster.mandlebrot.common.util.Timer;
import org.dorkmaster.mandlebrot.renderer.Starter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

// /render/640/480/256/4.0/-2/-2/2/2
@ServerEndpoint("/render/{width}/{height}/{iterations}/{escape}/{lowerR}/{lowerI}/{upperR}/{upperI}/image.png")
@ApplicationScoped
public class RenderSocket {
    protected Logger logger= LoggerFactory.getLogger(this.getClass());

    @OnOpen
    public void onOpen(Session session,
                       @PathParam("width") int width, @PathParam("height") int height,
                       @PathParam("iterations") int iterations, @PathParam("escape") double escape,
                       @PathParam("lowerR") double lowerR, @PathParam("lowerI") double lowerI,
                       @PathParam("upperR") double upperR, @PathParam("upperI") double upperI) {

        RenderContext context = new RenderContext(width, height, iterations, escape, new ComplexNumber(lowerR, lowerI), new ComplexNumber(upperR, upperI));

        new Thread(() -> {
            try {
                startRender(session, context);
            } catch (IOException|ExecutionException|InterruptedException|TimeoutException e) {
                e.printStackTrace();
            }
        }).start();
    }

    protected void startRender(Session session, RenderContext context) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        Timer timer = new Timer();
        Starter starter = new Starter();
        Set<Future<Collection<Pixel>>> futures = starter.submitTasks(context.getWidth() * context.getHeight() / 10, context);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (!starter.render(out, context, futures)) {
            session.getBasicRemote().sendBinary(ByteBuffer.wrap(out.toByteArray()));
            out = new ByteArrayOutputStream();
            Thread.sleep(100);
        }
        session.getBasicRemote().sendBinary(ByteBuffer.wrap(out.toByteArray()));
        logger.info("Completed ${session} in {} ms", session.getId(), timer.time());
        session.close();
    }

//    @OnClose
//    public void onClose(Session session, @PathParam("name") String name) {
//        System.out.println("onClose> " + name);
//    }
//
//    @OnError
//    public void onError(Session session, @PathParam("name") String name, Throwable throwable) {
//        System.out.println("onError> " + name + ": " + throwable);
//    }
//
//    @OnMessage
//    public void onMessage(String message, @PathParam("name") String name) {
//        System.out.println("onMessage> " + name + ": " + message);
//    }
}
