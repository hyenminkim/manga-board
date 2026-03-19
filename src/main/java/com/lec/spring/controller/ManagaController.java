package com.lec.spring.controller;


import com.lec.spring.domain.Managa;
import com.lec.spring.domain.ManagaResponse;
import com.lec.spring.service.NoticeService;
import com.lec.spring.util.U;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/managa")
public class ManagaController {

    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;
    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;


    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public String ManagaData(Model model, @RequestParam(required = false) String keyword,
                             @RequestParam(required = false, defaultValue = "1") Integer page) {
        String prvKey = "8c47fbb6a61f0945abadec1287c3e49d";

        String url = "https://www.kmas.or.kr/openapi/search/dcmtDtaList" +
                "?prvKey=" + prvKey +
                "&title="+keyword +"&pageNo="+page;

        int totalPage;
        int startPage;
        int endPage;
        int cnt = 0;

        RestTemplate restTemplate = new RestTemplate();
        ManagaResponse response = restTemplate.getForObject(url, ManagaResponse.class);

        if (response != null && response.getItemList() != null) {
            model.addAttribute("items", response.getItemList());
            model.addAttribute("result",response.getResult());
        } else {
            model.addAttribute("items", List.of());
            model.addAttribute("result",null);
            System.out.println("⚠️ 응답이 비어있거나 itemList가 없습니다.");
        }

        if (page == null) page = 1;   // 디폴트 1 page
        if (page < 1) page = 1;

        // paging
        HttpSession session = U.getSession();
        Integer writePages = (Integer) session.getAttribute("writePages");
        if (writePages == null) writePages = WRITE_PAGES;  // 만약 session 에 없으면 기본값으로 동작
        Integer pageRows = (Integer) session.getAttribute("pageRows");
        if (pageRows == null) pageRows = PAGE_ROWS; // 만약 session 에 없으면 기본값으로 동작
        session.setAttribute("page", page);  // 현재 페이지 번호 -> session 에 저장


        cnt = response.getResult().getTotalCount();



        totalPage = (int) Math.ceil(cnt / (double) pageRows);
        startPage = (((page - 1) / writePages) * writePages) + 1;
        endPage = startPage + writePages - 1;


        if (endPage >= totalPage) endPage = totalPage;


        model.addAttribute("page", page); // 현재 페이지
        model.addAttribute("totalPage", totalPage);  // 총 '페이지' 수
        model.addAttribute("pageRows", pageRows);  // 한 '페이지' 에 표시할 글 개수
        model.addAttribute("keyword",keyword);
        model.addAttribute("url", U.getRequest().getRequestURI());  // 목록 url
        model.addAttribute("writePages", writePages); // [페이징] 에 표시할 숫자 개수
        model.addAttribute("startPage", startPage);  // [페이징] 에 표시할 시작 페이지
        model.addAttribute("endPage", endPage);   // [페이징] 에 표시할 마지막 페이지




        return "/managa/list";
    }


    @GetMapping("/detail")
    public String detail(Model model,
                         @RequestParam String title,
                         @RequestParam(required = false) String isbn,
                         @RequestParam(required = false) String prdctNm,
                         @RequestParam(required = false) String imageDownloadUrl,
                         @RequestParam(required = false) String mainGenreCdNm,
                         @RequestParam(required = false) String pictrWritrNm,
                         @RequestParam(required = false) String sntncWritrNm) {
        String prvKey = "8c47fbb6a61f0945abadec1287c3e49d";
        RestTemplate restTemplate = new RestTemplate();
        Managa detail = null;

        // 1차: dcmtDtaList (소장 도서 API - 확실히 동작)
        try {
            String url = "https://www.kmas.or.kr/openapi/search/dcmtDtaList"
                    + "?prvKey=" + prvKey
                    + "&title=" + title;

            System.out.println("[Detail] dcmtDtaList 호출: " + url);
            ManagaResponse response = restTemplate.getForObject(url, ManagaResponse.class);

            if (response != null && response.getItemList() != null && !response.getItemList().isEmpty()) {
                var items = response.getItemList();
                System.out.println("[Detail] dcmtDtaList 결과: " + items.size() + "건");

                detail = items.get(0);
                // isbn 또는 title 정확 매칭
                if (isbn != null && !isbn.isEmpty()) {
                    for (var item : items) {
                        if (isbn.equals(item.getIsbn())) { detail = item; break; }
                    }
                } else {
                    for (var item : items) {
                        if (title.equals(item.getTitle())) { detail = item; break; }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[Detail] dcmtDtaList 오류: " + e.getMessage());
        }

        // 2차: bookAndWebtoonList로 줄거리(outline) 등 추가 정보 보강
        try {
            String searchKeyword = (prdctNm != null && !prdctNm.isEmpty()) ? prdctNm : title;
            String url2 = "https://www.kmas.or.kr/openapi/search/bookAndWebtoonList"
                    + "?prvKey=" + prvKey
                    + "&title=" + searchKeyword;

            System.out.println("[Detail] bookAndWebtoonList 호출: " + url2);
            ManagaResponse response2 = restTemplate.getForObject(url2, ManagaResponse.class);

            if (response2 != null && response2.getItemList() != null && !response2.getItemList().isEmpty()) {
                var items2 = response2.getItemList();
                System.out.println("[Detail] bookAndWebtoonList 결과: " + items2.size() + "건");

                // isbn 매칭 또는 첫 번째
                Managa extra = items2.get(0);
                if (isbn != null && !isbn.isEmpty()) {
                    for (var item : items2) {
                        if (isbn.equals(item.getIsbn())) { extra = item; break; }
                    }
                }

                // detail에 줄거리 등 보강
                if (detail != null) {
                    if (extra.getOutline() != null) detail.setOutline(extra.getOutline());
                    if (extra.getPlscmpnIdNm() != null) detail.setPlscmpnIdNm(extra.getPlscmpnIdNm());
                    if (extra.getPltfomCdNm() != null) detail.setPltfomCdNm(extra.getPltfomCdNm());
                    if (extra.getAgeGradCdNm() != null) detail.setAgeGradCdNm(extra.getAgeGradCdNm());
                    if (extra.getSubtitl() != null) detail.setSubtitl(extra.getSubtitl());
                    if (extra.getEdtn() != null) detail.setEdtn(extra.getEdtn());
                } else {
                    detail = extra;
                }
            }
        } catch (Exception e) {
            System.out.println("[Detail] bookAndWebtoonList 오류: " + e.getMessage());
        }

        // 3차: 모두 실패 시 URL 파라미터로 전달받은 기본 정보
        if (detail == null) {
            System.out.println("[Detail] 모든 API 실패 - 기본 정보로 표시");
            detail = Managa.builder()
                    .title(title)
                    .prdctNm(prdctNm)
                    .imageDownloadUrl(imageDownloadUrl)
                    .mainGenreCdNm(mainGenreCdNm)
                    .pictrWritrNm(pictrWritrNm)
                    .sntncWritrNm(sntncWritrNm)
                    .isbn(isbn)
                    .build();
        }

        model.addAttribute("manga", detail);
        return "/managa/detail";
    }

    // 공지사항
    @GetMapping("/notice")
    public void notice(Model model){

        model.addAttribute("list",noticeService.noticelist());


    }

    @GetMapping("/notice_detail/{id}")
    public String  notice_detail(Model model, @PathVariable Long id){

        model.addAttribute("notice",noticeService.findByid(id));

        return "/managa/notice_detail";
    }
}
