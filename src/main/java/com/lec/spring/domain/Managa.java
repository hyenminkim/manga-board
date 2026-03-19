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

    // 상세 API 필드
    private String isbn;           // ISBN
    private String setIsbn;        // 세트 ISBN
    private String outline;        // 줄거리
    private String subtitl;        // 부제목
    private String edtn;           // 에디션
    private String plscmpnIdNm;    // 출판사명
    private String pltfomCdNm;     // 플랫폼명
    private String ageGradCdNm;    // 연령등급 (bookAndWebtoonList)
    private String orginlTitle;    // 원작제목
    private String originClCdNm;   // 연령등급 (dcmtDtaList)
    private String pessOfficNm;    // 소장위치
    private String callno;         // 청구기호
}
