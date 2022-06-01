package com.example.napoleon.util;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.google.gson.Gson;

public class ObjectUtil {
    
    
    public static Map<String,Object> convertObjectToMap(Object source) {
        ObjectMapper oMapper = new ObjectMapper();
        oMapper.setAnnotationIntrospector( new JacksonAnnotationIntrospector() {
            @Override
            public PropertyName findNameForSerialization(Annotated a) {
                return null;
            }
        });
        return oMapper.convertValue(source, Map.class);
    }

    public static Map<String,Object> patchObjectBySource(Map<String,Object> source, Map<String,Object> target) {
        
        for (Map.Entry<String,Object> entry : source.entrySet()){
            source.put(entry.getKey(), target.get(entry.getKey()));
        }
        return source;
    }

    public static Map<String,Object> patchObjectByTarget(Map<String,Object> source, Map<String,Object> target) {
        
        for (Map.Entry<String,Object> entry : target.entrySet()){
            source.put(entry.getKey(), entry.getValue());
        }
        return source;
    }

}
