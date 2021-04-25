package com.wang.bilibili.danmaku.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

public class JsonUtils {

    public static String parseDanMaKu(String json) {
        JsonMapper mapper = new JsonMapper();
        Map map = null;
        try {
            map = mapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Map data = (Map) map.get("data");
        List room = (List) data.get("room");

        StringBuilder str = new StringBuilder(50);

        room.forEach(value -> {
            Map tempMap = (Map) value;
            str.append(tempMap.get("nickname") + ":" + tempMap.get("text") + "\n");
        });
        return String.valueOf(str);
    }

}
