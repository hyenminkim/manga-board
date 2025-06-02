package com.lec.spring.repository;


import com.lec.spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByUsernameAndPassword(String username, String password);

    Member findByUsername(String username);


    boolean existsByUsername(String username);

    void deleteByUsername(String name);
}
