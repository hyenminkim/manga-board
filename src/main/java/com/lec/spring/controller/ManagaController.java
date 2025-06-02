package com.lec.spring.controller;


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
