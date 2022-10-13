package com.zhaizhengqing.try_json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TryJsonTest {

    @Test
    public void tryJson() throws JsonProcessingException {
        String a = "[{\"channel\":\"wechatTemplate\",\"id\":55,\"monthChannel\":\"202208_wechatTemplate\",\"code\":\"1564944496972795904\",\"clientCode\":\"ouHsu1OJis1zTsFxqDnbPX5rjhyA\",\"templateCode\":\"f9e3aa6170c14967a66857e2e4d9b66c\",\"signCode\":\"xcjilu15wlhlthkg1b9ieguguc2y0zaj\",\"templateParam\":\"\\\"{\\\\\\\"data\\\\\\\":{\\\\\\\"remark\\\\\\\":{\\\\\\\"value\\\\\\\":\\\\\\\"测试\\\\\\\"},\\\\\\\"keynote2\\\\\\\":{\\\\\\\"value\\\\\\\":\\\\\\\"08-30\\\\\\\"},\\\\\\\"first\\\\\\\":{\\\\\\\"value\\\\\\\":\\\\\\\"测试\\\\\\\"},\\\\\\\"keynote1\\\\\\\":{\\\\\\\"value\\\\\\\":\\\\\\\"1234\\\\\\\"}},\\\\\\\"miniprogram\\\\\\\":{\\\\\\\"pagepath\\\\\\\":\\\\\\\"page/Index/index?cc_mdm=operation_apps&cc_src=ma&msgid={msgid}\\\\\\\",\\\\\\\"appid\\\\\\\":\\\\\\\"wx2c6100ca98be3573\\\\\\\"},\\\\\\\"url\\\\\\\":\\\\\\\"page/Index/index?cc_mdm=operation_apps&cc_src=ma&msgid={msgid}\\\\\\\"}\\\"\",\"supplementaryParams\":\"\\\"1564944496972795904\\\"\",\"sendStatus\":100,\"sendStatusAt\":1661946831062,\"sendStatusInfo\":\"触达成功\",\"createdAt\":1661946818111,\"updatedAt\":1661946833609,\"deletedAt\":253402271999999,\"sendUser\":\"wxb86d3396affd3f79\",\"sendSubStatus\":0,\"cursorId\":64,\"callerName\":\"TgYJ//SG8UxS/4nuDiSk6w==\",\"touchStatus\":0,\"unsubscribedStatus\":0,\"unsubscribedAt\":1661946818111}]";
        ObjectMapper objectMapper = new ObjectMapper();
        List list = objectMapper.readValue(a, List.class);
        Map map = (Map) list.get(0);
        String template = (String) map.get("templateParam");
        template = objectMapper.readValue(template, String.class);
        Map templateMap = objectMapper.readValue(template, Map.class);
        System.out.println(1);
    }
}
