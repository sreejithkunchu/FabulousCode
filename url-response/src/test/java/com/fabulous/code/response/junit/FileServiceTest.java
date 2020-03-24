package com.fabulous.code.response.junit;

import com.fabulous.code.response.config.Conf;
import com.fabulous.code.response.services.FileService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Conf.class)
public class FileServiceTest {

    @Autowired
    private FileService fileServicel;

    @Test
    public void testFileService() {
        String url = "https://reqres.in/api/users/1";
        Object actual = fileServicel.fetchResponse(url);

        Assert.assertNotNull(fileServicel.fetchResponse(url));

        Map<String, Object> data = new HashMap<>();
        data.put("id", 1);
        data.put("email", "george.bluth@reqres.in");
        data.put("first_name", "George");
        data.put("last_name", "Bluth");
        data.put("avatar", "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");

        Map<String, Object> ad = new HashMap<>();
        ad.put("company", "StatusCode Weekly");
        ad.put("url", "http://statuscode.org/");
        ad.put("text", "A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.");

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("ad", ad);

        Assert.assertEquals(actual, response);


    }
}
