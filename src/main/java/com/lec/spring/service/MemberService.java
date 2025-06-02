package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.Member;
import com.lec.spring.domain.Post;

import java.util.List;
import java.util.Optional;


public interface MemberService {

    public void save(Member member);

    public Member check(String username, String password);


    public Member findByUsername(String username);

    List<Authority> selectAuthoritiesById(Long id);


    public  boolean selectusername(String username);


    // 관리자
    public  List<Member> memberlist();

    public  int memberdelete(Long id);

    public Optional<Member> findByuser(Long id);

    public int update(Member member, String role);

    public List<Post> post();


    public Member findById(Long id);


    // 마이페이지
    public  int mypageupdate(Member member);

    public int mypagedelete(String username);
}
