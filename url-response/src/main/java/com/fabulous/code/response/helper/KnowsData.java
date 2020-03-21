package com.fabulous.code.response.helper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
public class KnowsData {

    List<Map<String, String>> response1;
    List<Map<String, String>> response2;
    List<String> fileContent1;
    List<String> fileContent2;
}
