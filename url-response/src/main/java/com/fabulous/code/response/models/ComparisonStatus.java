package com.fabulous.code.response.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
public class ComparisonStatus {


    String message;
    Throwable throwable;
    CreateStatus createStatus;

    @Tolerate
    public ComparisonStatus() {
    }

    public enum CreateStatus {
        SUCCESSFUL,
        FAILED,
    }
}
