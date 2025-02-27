package com.test.barogo.member.repository;

import com.test.barogo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

    Member findByMemberInputId(String memberInputId);
}
