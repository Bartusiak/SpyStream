package com.spystream.app.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class JacksonHelper {

    // pobiera JSON ObjectMapper
    public static ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

    //pobiera YAML ObjectMapepr
    public static ObjectMapper getYamlObjectMapepr(){
        return new ObjectMapper(new YAMLFactory());
    }

}
