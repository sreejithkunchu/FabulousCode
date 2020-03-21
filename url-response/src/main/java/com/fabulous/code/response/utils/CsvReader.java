package com.fabulous.code.response.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class CsvReader {

    @Autowired
    private ResourceLoader resourceLoader;


    public List<String> getFileContent(String csvFilePath) {


        List<String> content = null;
        try {
            content = CommonUtils.readLines(new File(String.valueOf(resourceLoader.getResource("testdata/" + csvFilePath).getFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }


}
