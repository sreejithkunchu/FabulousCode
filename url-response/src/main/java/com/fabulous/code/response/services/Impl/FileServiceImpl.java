package com.fabulous.code.response.services.Impl;

import com.fabulous.code.response.services.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public Object fetchResponse(String url) {

        JsonPath jsonPath = given().baseUri(url).log().all()
                .contentType(ContentType.JSON)
                .when().get().then().
                        log().all().statusCode(200).extract().response().jsonPath();
        ObjectMapper objectMapper = new ObjectMapper();
        Object completeObject = new Object();
        try {
            completeObject = objectMapper.readValue(objectMapper.writeValueAsString(jsonPath.get()), Object.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return completeObject;
    }
}
