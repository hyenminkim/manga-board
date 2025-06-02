package com.lec.spring.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Managa {
    private Long pageNo; // 페이지 번호
    private String title; // 제목
    private String prdctNm; // 작품명
    private String mainGenreCdNm; // 장르
    private String imageDownloadUrl; // 이미지
    private int totalCount; // 검색 수
    private  String pictrWritrNm; // 그림 작가
    private  String sntncWritrNm; // 글 작가


}
