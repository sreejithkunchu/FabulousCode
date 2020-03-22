package com.fabulous.code.response.services.Impl;

import com.fabulous.code.response.services.FileService;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public Map<String, Object> fetchResponse(String url) {

        JsonPath jsonPath = given().baseUri(url).log().all()
                .contentType(ContentType.JSON)
                .when().get().then().
                        log().all().statusCode(200).extract().response().jsonPath();

        Map<String, Object> responseFetch = new HashMap<>();
        responseFetch.put("data", jsonPath.get("data"));
        responseFetch.put("ad", jsonPath.get("ad"));
        return responseFetch;

    }
}
