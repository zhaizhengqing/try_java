package com.zhaizhengqing.try_graalvm;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TryJs {

    public void tryJs() {
        long time = new Date().getTime();
        Context graalContext = Context.newBuilder().allowAllAccess(true).build();
        String js = "(function a(p1,p2){let jsProcessor=Object(); jsProcessor.sign=function(p1,p2){return p1.c+p2.d;}; return jsProcessor;})()";
        Value jsProcessorInJs = graalContext.eval("js", js);
        JsProcessor as = jsProcessorInJs.as(JsProcessor.class);
        Map p1 = new HashMap();
        p1.put("c", "cc");
        Map p2 = new HashMap();
        p2.put("d", "dd");
        String sign = as.sign(p1, p2);
        System.out.println(sign);
        long time1 = new Date().getTime();
        System.out.println(time1);
        System.out.println(time1 - time);
    }

    interface JsProcessor {
        public abstract String sign(Map p1, Map p2);
    }
}
