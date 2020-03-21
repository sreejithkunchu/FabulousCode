package com.fabulous.code.response.utils;


import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@EqualsAndHashCode
public class ObjectComparator {

    private static final Logger logger = Logger.getLogger(ObjectComparator.class);

    public static <T> void compareObjects(List<T> expectedObjectList, List<T> actualObjectList, Boolean ignoreTwoWay) {
        List<Map<String, String>> actualMapList = actualObjectList.stream().map(ObjectConverter::convertObjectToMap).collect(Collectors.toList());
        List<Map<String, String>> expectedMapList = expectedObjectList.stream().map(ObjectConverter::convertObjectToMap).collect(Collectors.toList());
        if (ignoreTwoWay) {
            expectedMapList = expectedMapList.stream().map(map -> map.entrySet().stream().
                    filter(entry -> !CommonUtils.isEmpty(entry.getValue())).collect(Collectors.toMap(Map.Entry::getKey,
                    Map.Entry::getValue))).collect(Collectors.toList());
        }
        compare(expectedMapList, actualMapList, ignoreTwoWay);
    }

    public static <T> void compareObjects(List<T> expectedObjectList, List<T> actualObjectList) {
        compareObjects(expectedObjectList, actualObjectList, false);
    }

    public static void compare(List<Map<String, String>> expectedData, List<Map<String, String>> actualData, boolean ignoreTwoWayComparison) {
        List<Map<String, String>> expected = expectedData.stream().map(HashMap::new).collect(Collectors.toList());
        List<Map<String, String>> actual = actualData.stream().map(HashMap::new).collect(Collectors.toList());


        if (!ignoreTwoWayComparison) {
            if (actual.size() != expected.size()) {
                logger.error("Mismatch in size of expected data and actual data");
                throw new AssertionError(String.format("Mismatch in size of expected data %s and actual data %s", expected.size(), actual.size()));
            }
        }
        int rowNumber = 1;
        for (Map<String, String> expectedRowData : expected) {
            Map<String, String> actualRow = getClosestMatchingItem(actual, expectedRowData);
            if (actualRow == null) {
                logger.info("Below row is not found in actual  data list");

                throw new AssertionError(String.format("Row %s is not found in Actual Data", expectedRowData));
            }
            for (String key : expectedRowData.keySet()) {
                String actualValue = String.valueOf(actualRow.get(key));
                String expectedValue = String.valueOf(expectedRowData.get(key));
                actualValue = (StringUtils.isBlank(actualValue) || StringUtils.equals(actualValue, "null")) ? "" : actualValue;
                expectedValue = (StringUtils.isBlank(expectedValue) || StringUtils.equals(expectedValue, "null")) ? "" : expectedValue;

                Assert.assertEquals(String.format("Mismatch found at row number %s and column %s", rowNumber + 1, key), actualValue, expectedValue);
            }
            rowNumber++;
        }
    }


    private static Map<String, String> getClosestMatchingItem(List<Map<String, String>> expectedDataList, Map<String, String> actualData) {
        Map<String, String> matchingRow = null;
        int maxMatchingCount = 0;
        for (Map<String, String> expectedData : expectedDataList) {
            int matchCount = 0;
            for (String key : expectedData.keySet()) {
                if (expectedData.get(key) == null) {
                    if (actualData.get(key) == null) {
                        matchCount++;
                    }
                } else if (String.valueOf(expectedData.get(key)).equals(String.valueOf(actualData.get(key)))) {
                    matchCount++;
                }
            }
            if (matchCount > maxMatchingCount) {
                maxMatchingCount = matchCount;
                matchingRow = expectedData;
            }
        }
        if (isThresholdMeeting(actualData, maxMatchingCount)) {
            expectedDataList.remove(matchingRow);
            return matchingRow;
        }
        return null;
    }

    private static boolean isThresholdMeeting(Map<String, String> actualData, int maxMatchingCount) {
        List<Map.Entry> notNullEntries = actualData.entrySet().stream().filter(entry -> entry.getValue() != null).collect(Collectors.toList());
        return maxMatchingCount > ((notNullEntries.size() * 30.0) / 100);
    }
}