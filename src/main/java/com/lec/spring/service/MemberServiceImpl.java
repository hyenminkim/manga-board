package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.Member;
import com.lec.spring.domain.Post;
import com.lec.spring.repository.AuthorityRepository;
import com.lec.spring.repository.MemberRepository;
import com.lec.spring.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthorityRepository authorityRepository;



    @Autowired
    private PostRepository postRepository;

    // 회원 가입
    @Override
    public void save(Member member) {
        member.setUsername(member.getUsername().toUpperCase());
        member.setPassword(passwordEncoder.encode(member.getPassword()));



        Authority auth = authorityRepository.findByName("ROLE_MEMBER");


        member.addAuthority(auth);
        System.out.println("권한 추가됨: " + member.getAuthorities());

        memberRepository.save(member);
    }


    // 로그인
    @Override
    public Member check(String username, String password) {

        Optional<Member> member = memberRepository.findByUsernameAndPassword(username,password);


        if(member.isPresent()) { // findMember 에 값이 있는지 확인
            return member.get();
        } else {
            return null;
        }
    }

    @Override
    public Member findByUsername(String username) {


        return memberRepository.findByUsername(username.toUpperCase());
    }



    @Override
    public List<Authority> selectAuthoritiesById(Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        if(member != null)
            return member.getAuthorities();

        return new ArrayList<>();
    }

    @Override
    public boolean selectusername(String username) {
        boolean result = memberRepository.existsByUsername(username.trim().toUpperCase());


        return result;
    }


    // 관리자
    @Override
    public List<Member> memberlist() {
        return memberRepository.findAll();
    }

    @Override
    public int memberdelete(Long id) {

        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return 1; // 삭제 성공
        }
        return 0; // 해당 ID가 존재하지 않음
    }

    @Override
    public Optional<Member> findByuser(Long id) {

        return memberRepository.findById(id);
    }

    @Override
    public int update(Member member, String role) {
        int result = 0;

        Member m = memberRepository.findById(member.getId()).orElse(null);

        Authority auth = authorityRepository.findByName(role);


        if(m != null){
            m.setUsername(member.getUsername().toUpperCase());
//            m.setPassword((passwordEncoder.encode(member.getPassword())));
            m.setName(member.getName());


            m.getAuthorities().clear();

            m.addAuthority(auth);

            memberRepository.saveAndFlush(m);

            result = 1;
        }


        return result;
    }
    // 게시글
    @Override
    public List<Post> post() {
        return postRepository.findAll();
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    // 마이 페이지
    @Override
    public int mypageupdate(Member member) {
        int result = 0;

        Member re_member = memberRepository.findByUsername(member.getUsername());


        if(re_member != null) {
            re_member.setName(member.getName());
            re_member.setPassword(passwordEncoder.encode(member.getPassword()));

            memberRepository.save(re_member);

            result = 1;
        }

        return result;
    }

    // 삭제
    @Override
    @Transactional
    public int mypagedelete(String username) {


        if(memberRepository.existsByUsername(username)){

            memberRepository.deleteByUsername(username);

            return 1;
        }

        return 0;
    }

}
