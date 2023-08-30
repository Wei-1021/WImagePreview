package com.wei.wimagepreviewlib.utils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * WeakReference数据存储工具类
 *
 * @author weizhanjie
 */
public class WeakDataHolder {

    private static WeakDataHolder instance;

    public static WeakDataHolder getInstance() {
        if (instance == null) {
            synchronized (WeakDataHolder.class) {
                if (instance == null) {
                    instance = new WeakDataHolder();
                }
            }
        }
        return instance;
    }

    private final Map<String, WeakReference<Object>> map = new HashMap<>();

    /**
     * 数据存储
     *
     * @param key
     * @param object
     */
    public void saveData(String key, Object object) {
        map.put(key, new WeakReference<>(object));
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public Object getData(String key) {
        WeakReference<Object> weakReference = map.get(key);
        if (weakReference == null) {
            return null;
        }

        Object object = weakReference.get();
        return object;
    }

    /**
     * 获取数据
     *
     * @param key
     * @param defaultValue 缺省
     * @return
     */
    public Object getData(String key, Object defaultValue) {
        Object object = getData(key);
        return getData(key) == null ? defaultValue : object;
    }
}