package com.test.barogo.member.service;

import com.test.barogo.member.domain.Member;
import com.test.barogo.member.dto.MemberDto;
import com.test.barogo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto.CreateRes create(MemberDto.CreateReq req) {
        req.setPassword(passwordEncoder.encode(req.getPassword()));

        Member save = memberRepository.save(req.dtoToDomain(req));
        return new MemberDto.CreateRes(save);
    }
}
