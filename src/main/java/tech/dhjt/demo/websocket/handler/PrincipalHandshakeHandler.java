package tech.dhjt.demo.websocket.handler;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * <设置认证用户信息的握手拦截器>
 **/
public class PrincipalHandshakeHandler extends DefaultHandshakeHandler {

    private static final Logger logger = LoggerFactory.getLogger(PrincipalHandshakeHandler.class);



    // @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        //这里就是简单
        HttpSession session = getSession(request);
        if (session != null) {
            //            if (isCopyHttpSessionId()) {
            //                attributes.put(HTTP_SESSION_ID_ATTR_NAME, session.getId());
            //            }
            //            Enumeration<String> names = session.getAttributeNames();
            //            while (names.hasMoreElements()) {
            //                String name = names.nextElement();
            //                if (isCopyAllAttributes() || getAttributeNames().contains(name)) {
            //                    attributes.put(name, session.getAttribute(name));
            //                }
            //            }
        }
        return true;
    }

    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }
}
