package com.fabulous.code.response.helper;

import com.fabulous.code.response.models.ComparisonStatus;
import com.fabulous.code.response.services.FileService;
import com.fabulous.code.response.utils.ObjectComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
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
                List<Map<String, String>> fetchResponse1 = fileService.fetchResponse(knowsData.getFileContent1().get(value));
                List<Map<String, String>> fetchResponse2 = fileService.fetchResponse(knowsData.getFileContent2().get(value));
                ObjectComparator.compareObjects(fetchResponse1, fetchResponse2, false);
                return ComparisonStatus.builder().message(knowsData.getFileContent1().get(value) + "  equals  " + knowsData.getFileContent2().get(value)).createStatus(ComparisonStatus.CreateStatus.SUCCESSFUL).build();
            } catch (Throwable throwable) {
                return ComparisonStatus.builder().throwable(throwable).message(knowsData.getFileContent1().get(value) + "  not equals  " + knowsData.getFileContent2().get(value)).createStatus(ComparisonStatus.CreateStatus.FAILED).build();
            }
        };
    }
}
