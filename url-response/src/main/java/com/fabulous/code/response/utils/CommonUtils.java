package com.fabulous.code.response.utils;


import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;


@EqualsAndHashCode
public class CommonUtils {
    private static final Logger logger = Logger.getLogger(CommonUtils.class);

    public static List<String> readLines(File file) {
        try {
            return FileUtils.readLines(file);
        } catch (IOException e) {
            logger.error(e);
            return Lists.newArrayList();
        }
    }

    public static boolean isEmpty(Object value) {
        return value == null || StringUtils.isEmpty(String.valueOf(value)) || "null".equalsIgnoreCase(String.valueOf(value));

    }


}
