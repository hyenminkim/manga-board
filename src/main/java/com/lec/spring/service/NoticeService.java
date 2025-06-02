package com.lec.spring.service;

import com.lec.spring.domain.Notice;

import java.util.List;

public interface NoticeService {


    public  void noticecreate(Notice notice);

    public List<Notice> noticelist();

    public  int  noticedelete(Long id);

    public Notice findByid(Long id);

}
