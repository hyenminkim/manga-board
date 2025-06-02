package com.lec.spring.config;

import com.lec.spring.domain.Member;

import com.lec.spring.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberService.findByUsername(username);


        // DB 에 해당 username 이 존재할 때
        if (member != null) {
            PrincipalDetails userDetails = new PrincipalDetails(member);
            userDetails.setMemberService(memberService);
            return userDetails;
        }

        // DB 에 해당 username이 존재하지 않을 때
        throw new UsernameNotFoundException(username);

    }
}
