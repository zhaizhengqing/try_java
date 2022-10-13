package com.zhaizhengqing.try_graalvm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

public class TryPythonTest {
    @Test
    public void tryPython() throws IOException {
        long time = new Date().getTime();
        Context graalContext = Context.newBuilder().allowAllAccess(true).build();
        InputStream in = ClassLoader.getSystemClassLoader()
                .getResourceAsStream("PythonProcessor.py");
        String python = IOUtils.toString(in, Charset.forName("utf8"));
        Value pythonProcessorInPython = graalContext.eval("python", python);
        PythonProcessor as = pythonProcessorInPython.as(PythonProcessor.class);
        List l = new ArrayList<>();
        l.add("c");
        l.add("cc");
        Map m = new HashMap();
        m.put("d", "dd");
        Set s = new HashSet();
        s.add("a");
        s.add("b");

        String str = "asdfasdfasdf";
        Value sign = as.sign(l, m, s, str, 1, 2l, 3f);
        System.out.println(sign.getClass().getName());
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(sign);
        System.out.println(objectMapper.writeValueAsString(sign.as(Set.class)));
        long time1 = new Date().getTime();
        System.out.println(time1);
        System.out.println(time1 - time);
    }

    interface PythonProcessor {
        public abstract Value sign(List l, Map m, Set s, String str, int i, long lon, float f);
    }
}
