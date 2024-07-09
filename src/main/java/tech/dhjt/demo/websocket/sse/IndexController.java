package tech.dhjt.demo.websocket.sse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@CrossOrigin
public class IndexController {

    private Map<String, SseEmitter> pushSseEmitterMap = new HashMap<>();

    @GetMapping(path = "/push/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter pushSse(@PathVariable("id") String uid) {
        System.out.println("pushSse is run");
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onCompletion(() -> {
            pushSseEmitterMap.remove(uid);
        });
        sseEmitter.onTimeout(() -> {
            pushSseEmitterMap.remove(uid);
        });
        sseEmitter.onError(throwable -> {
            System.out.println("onError is run");
            throwable.printStackTrace();
            pushSseEmitterMap.remove(uid);
        });
        if (!pushSseEmitterMap.containsKey(uid)) {
            pushSseEmitterMap.remove(uid);
        }
        pushSseEmitterMap.put(uid, sseEmitter);
        return sseEmitter;
    }

    // @Scheduled(fixedDelay = 2*1000)
    // public void scheduledMsgEmitter() throws IOException
    // {
    // pushSseEmitterMap.keySet().forEach(key -> {
    // SseEmitter emitter = pushSseEmitterMap.get(key);
    // if (null != emitter){
    // try {
    // System.out.println("Timeout : "+ emitter.getTimeout());
    // emitter.send(": " + Calendar.getInstance().getTime());
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    //
    // });
    // }

    @RequestMapping("/push/send")
    @ResponseBody
    public String push(@RequestParam String uid, @RequestParam String value) {
        System.out.println("push is run");
        SseEmitter sseEmitter = pushSseEmitterMap.get(uid);
        if (sseEmitter != null) {
            try {
                sseEmitter.send(value, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                e.printStackTrace();
                return "fail";
            }
        }
        return "ok";
    }

    @RequestMapping("/push/finish")
    @ResponseBody
    public String finish(@RequestParam String uid, @RequestParam String value) {
        System.out.println("finish is run");
        SseEmitter sseEmitter = pushSseEmitterMap.get(uid);
        if (sseEmitter != null) {
            try {
                sseEmitter.send(value, MediaType.APPLICATION_JSON);
                sseEmitter.complete();
            } catch (IOException e) {
                e.printStackTrace();
                return "fail";
            }
        }
        return "ok";
    }
}
