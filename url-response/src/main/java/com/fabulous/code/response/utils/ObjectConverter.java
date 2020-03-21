package com.fabulous.code.response.utils;


import com.fabulous.code.response.exceptions.UnSupportedObjectException;
import lombok.EqualsAndHashCode;
import org.apache.commons.beanutils.BeanUtils;


import java.util.HashMap;
import java.util.Map;


@EqualsAndHashCode
public class ObjectConverter {

    public static <T> Map<String, String> convertObjectToMap(T t) {
        Map<String, String> map = new HashMap<>();
        try {
            Map<String, String> tempMap = BeanUtils.describe(t);
            tempMap.entrySet().stream().filter(entry -> !entry.getKey().equals("class")).forEach(entry -> map.put(entry.getKey(), String.valueOf(entry.getValue())));
        } catch (Exception e) {
            throw new UnSupportedObjectException(e);
        }
        return map;
    }

}
