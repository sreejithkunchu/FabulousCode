package com.fabulous.code.response.services;

import java.util.List;
import java.util.Map;

public interface FileService {


    public List<Map<String, String>> fetchResponse(String url);
}
