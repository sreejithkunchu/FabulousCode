package com.fabulous.code.response.services.Impl;

import com.fabulous.code.response.services.FileService;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public List<Map<String, String>> fetchResponse(String url) {

        List<Map<String, String>> response = new ArrayList<>();
        JsonPath jsonPath = given().baseUri(url).log().all()
                .contentType(ContentType.JSON)
                .when().get().then().
                        log().all().statusCode(200).extract().response().jsonPath();

        Map<String, String> dataResponse = jsonPath.getObject("data", Map.class);
        Map<String, String> adResponse = jsonPath.getObject("ad", Map.class);
        response.add(dataResponse);
        response.add(adResponse);
        return response;

    }
}
