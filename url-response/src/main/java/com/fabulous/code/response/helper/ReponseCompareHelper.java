package com.fabulous.code.response.helper;

import com.fabulous.code.response.models.ComparisonStatus;
import com.fabulous.code.response.services.FileService;
import com.fabulous.code.response.utils.ObjectComparator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ReponseCompareHelper {

    @Autowired
    private FileService fileService;

    @Autowired
    private KnowsData knowsData;

    public void executeParallel() {
        List<ComparisonStatus> compareResults = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        try {
            List<Future<ComparisonStatus>> futures = executorService.invokeAll(IntStream.range(0, knowsData.getFileContent1().size()).mapToObj(value -> {
                return compare(value);
            }).collect(Collectors.toList()));

            compareResults = futures.stream().map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    return ComparisonStatus.builder().createStatus(ComparisonStatus.CreateStatus.FAILED).message(e.getMessage()).build();
                }
            }).collect(Collectors.toList());
            System.out.println("-------------------Compare Results-------------------  : ");
            compareResults.forEach(message -> {
                System.out.println(message.getMessage());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private Callable<ComparisonStatus> compare(int value) {
        return () -> {
            try {
                Object file1Data = fileService.fetchResponse(knowsData.getFileContent1().get(value));
                Object file2Data = fileService.fetchResponse(knowsData.getFileContent2().get(value));
                List<Map<String, String>> fileData1 = new ArrayList<>();
                List<Map<String, String>> fileData2 = new ArrayList<>();
                fileData1.addAll(convertToObject(file1Data));
                fileData2.addAll(convertToObject(file2Data));
                ObjectComparator.compare(fileData1, fileData2, false);
                return ComparisonStatus.builder().message(knowsData.getFileContent1().get(value) + "  equals  " + knowsData.getFileContent2().get(value)).createStatus(ComparisonStatus.CreateStatus.SUCCESSFUL).build();
            } catch (Throwable throwable) {
                return ComparisonStatus.builder().throwable(throwable).message(knowsData.getFileContent1().get(value) + "  not equals  " + knowsData.getFileContent2().get(value)).createStatus(ComparisonStatus.CreateStatus.FAILED).build();
            }
        };
    }

    public List<Map<String, String>> convertToObject(Object value) {
        List<Map<String, String>> fileData = new ArrayList<>();
        if (value instanceof Map) {
            mapData(value, fileData);
        } else if (value instanceof List) {
            listData(value, fileData);
        }

        return fileData;
    }

    private void mapData(Object value, List<Map<String, String>> fileData1) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> initialMap = objectMapper.convertValue(value, Map.class);

        for (Map.Entry<String, Object> entry : initialMap.entrySet()) {
            if (entry.getValue() instanceof Integer) {
                Map<String, String> integer = objectMapper.convertValue(entry, Map.class);
                fileData1.addAll(Collections.singletonList(integer));
            } else if (entry.getValue() instanceof String) {
                Map<String, String> string = objectMapper.convertValue(entry, Map.class);
                fileData1.addAll(Collections.singletonList(string));
            } else if (entry.getValue() instanceof Boolean) {
                Map<String, String> bools = objectMapper.convertValue(entry, Map.class);
                fileData1.addAll(Collections.singletonList(bools));
            } else if (entry.getValue() instanceof Double) {
                Map<String, String> doubl = objectMapper.convertValue(entry, Map.class);
                fileData1.addAll(Collections.singletonList(doubl));
            } else if (entry.getValue() instanceof List) {
                listData(entry.getValue(), fileData1);
            } else if (entry.getValue() instanceof Map) {
                mapData(entry.getValue(), fileData1);
            }
        }
    }

    private void listData(Object value, List<Map<String, String>> fileData1) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (objectMapper.convertValue(value, List.class).get(0) instanceof Map) {
            List<Map<String, String>> lists = objectMapper.convertValue(value, List.class);

            lists.forEach(list -> {
                mapData(list, fileData1);
            });
        } else {
            List<Map<String, String>> listMap = new ArrayList<>();
            Map<String, String> singleListMap = new HashMap<>();
            AtomicInteger index = new AtomicInteger();
            objectMapper.convertValue(value, List.class).forEach(list -> {
                singleListMap.put(String.valueOf(index.get()), String.valueOf(list));
                index.getAndIncrement();
            });
            listMap.add(singleListMap);
            fileData1.addAll(listMap);
        }


    }

}
