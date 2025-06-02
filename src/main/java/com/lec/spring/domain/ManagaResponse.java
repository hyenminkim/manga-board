package com.lec.spring.domain;

import com.lec.spring.domain.Managa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagaResponse {
    private Result result;
    private List<Managa> itemList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private int viewItemCnt;
        private int pageNo;
        private String resultState;
        private String resultMessage;
        private int totalCount;

    }
}
