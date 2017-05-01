package com.xjh1994.helloandroid.core.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class GsonUtils {
    private static Gson gson = new Gson();

    /**
     * json字符串转对象或容器
     */
    public static <T> T fromJson(String json, TypeToken<T> token) {
        try {
            return gson.fromJson(json, token.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转对象或容器
     */
    public static <T> T fromJson(String json, Class<T> c) {
        try {
            return gson.fromJson(json, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(JsonReader jsonReader, Class<T> c) {
        try {
            return gson.fromJson(jsonReader, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }
}