package com.fabulous.code.response.junit;

import com.fabulous.code.response.config.Conf;
import com.fabulous.code.response.utils.CsvReader;
import com.fabulous.code.response.utils.ObjectComparator;
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

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Conf.class)
public class UtilTest {

    @Autowired
    private CsvReader csvReader;

    /*check if the file is non empty*/
    @Test
    public void nonEmptyCsvReaderTest() {
        Assert.assertThat(csvReader.getFileContent("file1.csv").isEmpty(), is(false));
    }

    /*compare 2 list of map of string when 2 way ignore is set as false and it returns mismatch in size error*/
    @Test(expected = AssertionError.class)
    public void objectCompareTestWithIgnoreTwoWaysSetAsFalseWithDiffMapSize() {
        try {
            List<Map<String, String>> expectedList = new ArrayList<>();
            Map<String, String> expectedMap1 = new HashMap<>();
            expectedMap1.put("name", "ABC");
            expectedMap1.put("Age", "30");

            Map<String, String> expectedMap2 = new HashMap<>();
            expectedMap1.put("name", "DEF");
            expectedMap1.put("Age", "35");

            expectedList.add(expectedMap1);
            expectedList.add(expectedMap2);

            List<Map<String, String>> actualList = new ArrayList<>();

            Map<String, String> actualMap1 = new HashMap<>();
            actualMap1.put("name", "ABC");
            actualMap1.put("Age", "30");


            actualList.add(actualMap1);

            ObjectComparator.compare(expectedList, actualList, false);
        } catch (AssertionError error) {
            Assert.assertThat(error.getMessage(), is("Mismatch in size of expected data 2 and actual data 1"));
            throw new AssertionError();
        }
    }

    /*compare 2 list of map of string when 2 way ignore is set as true but one list if empty*/
    @Test(expected = AssertionError.class)
    public void objectCompareTestWithIgnoreTwoWaysSetAsTrueWithEmptyActualList() {
        try {
            List<Map<String, String>> expectedList = new ArrayList<>();

            Map<String, String> expectedMap1 = new HashMap<>();
            expectedMap1.put("name", "DEF");
            expectedMap1.put("Age", "35");

            expectedList.add(expectedMap1);

            List<Map<String, String>> actualList = new ArrayList<>();

            ObjectComparator.compare(expectedList, actualList, true);
        } catch (AssertionError error) {
            Assert.assertThat(error.getMessage(), is("Row {name=DEF, Age=35} is not found in Actual Data"));
            throw new AssertionError();
        }

    }

    /*compare 2 list of map of string when 2 way ignore is set as true but expected list is not present in actual*/
    @Test(expected = AssertionError.class)
    public void objectCompareTestWithIgnoreTwoWaysSetAsTrueWithExceptedDataNotAvailableInActual() {
        try {
            List<Map<String, String>> expectedList = new ArrayList<>();

            Map<String, String> expectedMap1 = new HashMap<>();
            expectedMap1.put("name", "DEF");
            expectedMap1.put("Age", "35");

            expectedList.add(expectedMap1);

            List<Map<String, String>> actualList = new ArrayList<>();

            Map<String, String> actualMap1 = new HashMap<>();
            actualMap1.put("name", "EFG");
            actualMap1.put("Age", "31");

            Map<String, String> actualMap2 = new HashMap<>();
            actualMap2.put("name", "ABC");
            actualMap2.put("Age", "25");

            actualList.add(actualMap1);
            actualList.add(actualMap2);

            ObjectComparator.compare(expectedList, actualList, true);
        } catch (AssertionError error) {
            Assert.assertThat(error.getMessage(), is("Row {name=DEF, Age=35} is not found in Actual Data"));
            throw new AssertionError();
        }

    }

    /*compare 2 list of map of string when 2 way ignore is set as false with same size but expected data is not present in Actual*/
    @Test(expected = AssertionError.class)
    public void objectCompareTestWithIgnoreTwoWaysSetAsFalseWithSameMapSizeButDiffContent() {
        try {
            List<Map<String, String>> expectedList = new ArrayList<>();

            Map<String, String> expectedMap1 = new HashMap<>();
            expectedMap1.put("name", "ABC");
            expectedMap1.put("Age", "30");

            Map<String, String> expectedMap2 = new HashMap<>();
            expectedMap2.put("name", "DEF");
            expectedMap2.put("Age", "35");

            expectedList.add(expectedMap1);
            expectedList.add(expectedMap2);

            List<Map<String, String>> actualList = new ArrayList<>();

            Map<String, String> actualMap1 = new HashMap<>();
            actualMap1.put("name", "EFG");
            actualMap1.put("Age", "31");

            Map<String, String> actualMap2 = new HashMap<>();
            actualMap2.put("name", "DEF");
            actualMap2.put("Age", "35");

            actualList.add(actualMap1);
            actualList.add(actualMap2);

            ObjectComparator.compare(expectedList, actualList, false);
        } catch (AssertionError error) {
            Assert.assertThat(error.getMessage(), is("Row {name=ABC, Age=30} is not found in Actual Data"));
            throw new AssertionError();
        }
    }

    /*compare 2 list of map of string when 2 way ignore is set as true but one of the key is expected list is not present in Actual*/
    @Test(expected = AssertionError.class)
    public void objectCompareTestWithIgnoreTwoWaysSetAsTrueWithSameMapSizeButDiffContentinMap() {
        try {
            List<Map<String, String>> expectedList = new ArrayList<>();

            Map<String, String> expectedMap1 = new HashMap<>();
            expectedMap1.put("name", "ABC");
            expectedMap1.put("Age", "30");
            expectedMap1.put("Address", "India");

            expectedList.add(expectedMap1);


            List<Map<String, String>> actualList = new ArrayList<>();

            Map<String, String> actualMap1 = new HashMap<>();
            actualMap1.put("name", "ABC");
            actualMap1.put("Age", "30");

            Map<String, String> actualMap2 = new HashMap<>();
            actualMap2.put("name", "DEF");
            actualMap2.put("Age", "35");

            actualList.add(actualMap1);
            actualList.add(actualMap2);

            ObjectComparator.compare(expectedList, actualList, true);
        } catch (AssertionError error) {
            Assert.assertThat(error.getMessage(), is("Mismatch found at row number 2 and column Address expected:<[]> but was:<[India]>"));
            throw new AssertionError();
        }
    }

    /*compare 2 list of map of string when 2 way ignore is set as true when size are different but expected list data is present in Actual*/
    @Test
    public void objectCompareTestWithIgnoreTwoWaysSetAsTrueWithDiffMapSize() {
        List<Map<String, String>> expectedList = new ArrayList<>();

        Map<String, String> expectedMap1 = new HashMap<>();
        expectedMap1.put("name", "ABC");
        expectedMap1.put("Age", "30");

        expectedList.add(expectedMap1);


        List<Map<String, String>> actualList = new ArrayList<>();

        Map<String, String> actualMap1 = new HashMap<>();
        actualMap1.put("name", "ABC");
        actualMap1.put("Age", "30");

        Map<String, String> actualMap2 = new HashMap<>();
        actualMap2.put("name", "DEF");
        actualMap2.put("Age", "35");

        actualList.add(actualMap1);
        actualList.add(actualMap2);

        ObjectComparator.compare(expectedList, actualList, true);
    }


    /*compare 2 list of map of string when 2 way ignore is set as true when size are different but expected list data is present in Actual
    when actual has more content*/
    @Test
    public void objectCompareTestWithIgnoreTwoWaysSetAsTrueWithDiffMapSizeButDiffMapContent() {
        List<Map<String, String>> expectedList = new ArrayList<>();

        Map<String, String> expectedMap1 = new HashMap<>();
        expectedMap1.put("name", "ABC");
        expectedMap1.put("Age", "30");

        expectedList.add(expectedMap1);


        List<Map<String, String>> actualList = new ArrayList<>();

        Map<String, String> actualMap1 = new HashMap<>();
        actualMap1.put("name", "ABC");
        actualMap1.put("Age", "30");
        actualMap1.put("Address", "India");

        Map<String, String> actualMap2 = new HashMap<>();
        actualMap2.put("name", "DEF");
        actualMap2.put("Age", "35");

        actualList.add(actualMap1);
        actualList.add(actualMap2);

        ObjectComparator.compare(expectedList, actualList, true);
    }


    /*compare 2 list of map of string when 2 way ignore is set as true when size and content are same*/
    @Test
    public void objectCompareTestWithIgnoreTwoWaysSetAsTrueWithSameMapSizeAndSameContent() {
        List<Map<String, String>> expectedList = new ArrayList<>();

        Map<String, String> expectedMap1 = new HashMap<>();
        expectedMap1.put("name", "ABC");
        expectedMap1.put("Age", "30");

        Map<String, String> expectedMap2 = new HashMap<>();
        expectedMap2.put("name", "DEF");
        expectedMap2.put("Age", "35");

        expectedList.add(expectedMap1);
        expectedList.add(expectedMap2);


        List<Map<String, String>> actualList = new ArrayList<>();

        Map<String, String> actualMap1 = new HashMap<>();
        actualMap1.put("name", "ABC");
        actualMap1.put("Age", "30");

        Map<String, String> actualMap2 = new HashMap<>();
        actualMap2.put("name", "DEF");
        actualMap2.put("Age", "35");

        actualList.add(actualMap1);
        actualList.add(actualMap2);

        ObjectComparator.compare(expectedList, actualList, true);
    }

    /*compare 2 list of map of string when 2 way ignore is set as true when size and content are same*/
    @Test
    public void objectCompareTestWithIgnoreTwoWaysSetAsTrueWithSameMapSizeAndMoreContentInActual() {
        List<Map<String, String>> expectedList = new ArrayList<>();

        Map<String, String> expectedMap1 = new HashMap<>();
        expectedMap1.put("name", "ABC");
        expectedMap1.put("Age", "30");

        Map<String, String> expectedMap2 = new HashMap<>();
        expectedMap2.put("name", "DEF");
        expectedMap2.put("Age", "35");

        expectedList.add(expectedMap1);
        expectedList.add(expectedMap2);


        List<Map<String, String>> actualList = new ArrayList<>();

        Map<String, String> actualMap1 = new HashMap<>();
        actualMap1.put("name", "ABC");
        actualMap1.put("Age", "30");
        actualMap1.put("address", "India");

        Map<String, String> actualMap2 = new HashMap<>();
        actualMap2.put("name", "DEF");
        actualMap2.put("Age", "35");
        actualMap1.put("address", "Thailand");

        actualList.add(actualMap1);
        actualList.add(actualMap2);

        ObjectComparator.compare(expectedList, actualList, true);
    }
}
