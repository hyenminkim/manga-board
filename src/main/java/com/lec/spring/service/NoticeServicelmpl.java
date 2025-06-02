package com.lec.spring.service;

import com.lec.spring.domain.Notice;
import com.lec.spring.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServicelmpl implements NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public void noticecreate(Notice notice) {
        noticeRepository.save(notice);
    }

    @Override
    public List<Notice> noticelist() {
        return noticeRepository.findAll();
    }

    @Override
    public int noticedelete(Long id) {
        if (noticeRepository.existsById(id)) {
            noticeRepository.deleteById(id);
            return 1; // 삭제 성공
        }
        return 0; // 해당 ID가 존재하지 않음
    }

    @Override
    public Notice findByid(Long id) {
        return noticeRepository.findById(id).orElse(null);
    }
}
