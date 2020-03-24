package com.fabulous.code.response.steps;

import com.fabulous.code.response.helper.KnowsData;
import com.fabulous.code.response.helper.ReponseCompareHelper;
import com.fabulous.code.response.utils.CsvReader;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class UrlResponsesteps {

    @Autowired
    private CsvReader csVreader;

    @Autowired
    private KnowsData knowsData;

    @Autowired
    private ReponseCompareHelper reponseCompareHelper;


    @Given("^user fetch the response of end URl given in file1 '(.*)'$")
    public void responseFromFIle1(String file) {
        knowsData.setFileContent1(csVreader.getFileContent(file));
        Assert.assertTrue("file is empty", !knowsData.getFileContent1().isEmpty());
    }


    @Then("^user fetch the response of end URl given in file2 '(.*)'$")
    public void responseFromFIle2(String file) {
        knowsData.setFileContent2(csVreader.getFileContent(file));
        Assert.assertTrue("file is empty", !knowsData.getFileContent2().isEmpty());
    }

    @Then("^user compare the response of the above files$")
    public void compareResponse() {
        reponseCompareHelper.executeParallel();
    }


}
