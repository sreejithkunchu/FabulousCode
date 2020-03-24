package com.fabulous.code.response.junit;

import com.fabulous.code.response.config.Conf;
import com.fabulous.code.response.helper.KnowsData;
import com.fabulous.code.response.helper.ReponseCompareHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Conf.class)
public class ResponseCompareTest {

    @Autowired
    private ReponseCompareHelper reponseCompareHelper;

    @Autowired
    private KnowsData knowsData;

    //Test convert to object for Map containing String, boolean, Integer, Double and List of Object
    @Test
    public void convertToObjectTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> actualMap = new HashMap<>();
        actualMap.put("string", "A1");
        actualMap.put("bolean", false);
        actualMap.put("number", 3);
        actualMap.put("double", 3.0);

        List<Object> lists = new ArrayList<>();
        lists.add("first");
        lists.add("Second");
        actualMap.put("list", lists);

        Object obj = objectMapper.convertValue(actualMap, Object.class);

        List<Map<String, String>> actual = reponseCompareHelper.convertToObject(obj);

        List<Map<String, String>> expected = new ArrayList<>();
        Map<String, Object> expectedMap1 = new HashMap<>();
        expectedMap1.put("string", "A1");
        Map<String, Object> expectedMap2 = new HashMap<>();
        expectedMap2.put("bolean", false);
        Map<String, Object> expectedMap3 = new HashMap<>();
        expectedMap3.put("number", 3);
        Map<String, Object> expectedMap4 = new HashMap<>();
        expectedMap4.put("double", 3.0);

        Map<String, Object> listMap = new HashMap<>();
        listMap.put("0", "first");
        listMap.put("1", "Second");
        expected.add(objectMapper.convertValue(expectedMap1, Map.class));
        expected.add(objectMapper.convertValue(expectedMap2, Map.class));
        expected.add(objectMapper.convertValue(expectedMap3, Map.class));
        expected.add(objectMapper.convertValue(expectedMap4, Map.class));
        expected.add(objectMapper.convertValue(listMap, Map.class));

        Assert.assertThat(expected, containsInAnyOrder(actual.toArray()));
    }


    //Test convert to object for List of Map,  map containing String, boolean, Integer, Double and List of Object
    @Test
    public void convertToObjectTest2() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> actualMap = new HashMap<>();
        actualMap.put("string", "A1");
        actualMap.put("bolean", false);
        actualMap.put("number", 3);
        actualMap.put("double", 3.0);

        List<String> lists = new ArrayList<>();
        lists.add("first");
        lists.add("Second");
        actualMap.put("list", lists);

        list.add(actualMap);

        Object obj = objectMapper.convertValue(list, Object.class);

        List<Map<String, String>> actual = reponseCompareHelper.convertToObject(obj);

        List<Map<String, String>> expected = new ArrayList<>();
        Map<String, Object> expectedMap1 = new HashMap<>();
        expectedMap1.put("string", "A1");
        Map<String, Object> expectedMap2 = new HashMap<>();
        expectedMap2.put("bolean", false);
        Map<String, Object> expectedMap3 = new HashMap<>();
        expectedMap3.put("number", 3);
        Map<String, Object> expectedMap4 = new HashMap<>();
        expectedMap4.put("double", 3.0);

        Map<String, Object> listMap = new HashMap<>();
        listMap.put("0", "first");
        listMap.put("1", "Second");
        expected.add(objectMapper.convertValue(expectedMap1, Map.class));
        expected.add(objectMapper.convertValue(expectedMap2, Map.class));
        expected.add(objectMapper.convertValue(expectedMap3, Map.class));
        expected.add(objectMapper.convertValue(expectedMap4, Map.class));
        expected.add(objectMapper.convertValue(listMap, Map.class));

        Assert.assertThat(expected, containsInAnyOrder(actual.toArray()));
    }


    /*Test convert to object for List of Maps, first map containing String, boolean, Integer, Double and List of Object ,
     second map containing String, boolean, Integer, Double and List of another map which has String, boolean, Integer, Double*/
    @Test
    public void convertToObjectTest3() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> actualMap1 = new HashMap<>();
        actualMap1.put("string", "A1");
        actualMap1.put("bolean", false);
        actualMap1.put("number", 3);
        actualMap1.put("double", 3.0);

        List<String> lists = new ArrayList<>();
        lists.add("first");
        lists.add("Second");
        actualMap1.put("list", lists);


        Map<String, Object> actualMap2 = new HashMap<>();
        actualMap2.put("string", "A2");
        actualMap2.put("bolean", false);
        actualMap2.put("number", 32);
        actualMap2.put("double", 32.0);
        List<Map<String, Object>> list2 = new ArrayList<>();
        Map<String, Object> actualMap3 = new HashMap<>();
        actualMap3.put("string", "A3");
        actualMap3.put("bolean", false);
        actualMap3.put("number", 33);
        actualMap3.put("double", 33.0);
        list2.add(actualMap3);
        actualMap2.put("list2", list2);

        list.add(actualMap1);
        list.add(actualMap2);

        Object obj = objectMapper.convertValue(list, Object.class);

        List<Map<String, String>> actual = reponseCompareHelper.convertToObject(obj);

        List<Map<String, String>> expected = new ArrayList<>();
        Map<String, Object> expectedMap1 = new HashMap<>();
        expectedMap1.put("string", "A1");
        Map<String, Object> expectedMap2 = new HashMap<>();
        expectedMap2.put("bolean", false);
        Map<String, Object> expectedMap3 = new HashMap<>();
        expectedMap3.put("number", 3);
        Map<String, Object> expectedMap4 = new HashMap<>();
        expectedMap4.put("double", 3.0);

        Map<String, Object> listMap = new HashMap<>();
        listMap.put("0", "first");
        listMap.put("1", "Second");

        Map<String, Object> expectedMap5 = new HashMap<>();
        expectedMap5.put("string", "A2");
        Map<String, Object> expectedMap6 = new HashMap<>();
        expectedMap6.put("bolean", false);
        Map<String, Object> expectedMap7 = new HashMap<>();
        expectedMap7.put("number", 32);
        Map<String, Object> expectedMap8 = new HashMap<>();
        expectedMap8.put("double", 32.0);

        Map<String, Object> expectedMap9 = new HashMap<>();
        expectedMap9.put("string", "A3");
        Map<String, Object> expectedMap10 = new HashMap<>();
        expectedMap10.put("bolean", false);
        Map<String, Object> expectedMap11 = new HashMap<>();
        expectedMap11.put("number", 33);
        Map<String, Object> expectedMap12 = new HashMap<>();
        expectedMap12.put("double", 33.0);

        expected.add(objectMapper.convertValue(expectedMap1, Map.class));
        expected.add(objectMapper.convertValue(expectedMap2, Map.class));
        expected.add(objectMapper.convertValue(expectedMap3, Map.class));
        expected.add(objectMapper.convertValue(expectedMap4, Map.class));
        expected.add(objectMapper.convertValue(listMap, Map.class));
        expected.add(objectMapper.convertValue(expectedMap5, Map.class));
        expected.add(objectMapper.convertValue(expectedMap6, Map.class));
        expected.add(objectMapper.convertValue(expectedMap7, Map.class));
        expected.add(objectMapper.convertValue(expectedMap8, Map.class));
        expected.add(objectMapper.convertValue(expectedMap9, Map.class));
        expected.add(objectMapper.convertValue(expectedMap10, Map.class));
        expected.add(objectMapper.convertValue(expectedMap11, Map.class));
        expected.add(objectMapper.convertValue(expectedMap12, Map.class));

        Assert.assertThat(expected, containsInAnyOrder(actual.toArray()));
    }

    /*compare the response when  both url are same*/
    @Test
    public void compareResponse() {
        List<String> url1 = new ArrayList<>();
        url1.add("https://reqres.in/api/users/1");
        knowsData.setFileContent1(url1);

        List<String> url2 = new ArrayList<>();
        url2.add("https://reqres.in/api/users/1");
        knowsData.setFileContent2(url2);

        reponseCompareHelper.executeParallel();

    }

    /*Compare the response when both url are different*/
    @Test
    public void compareResponse2() {
        List<String> url1 = new ArrayList<>();
        url1.add("https://reqres.in/api/users/1");
        knowsData.setFileContent1(url1);

        List<String> url2 = new ArrayList<>();
        url2.add("https://reqres.in/api/users/2");
        knowsData.setFileContent2(url2);

        reponseCompareHelper.executeParallel();

    }

}
