package com.test.barogo.member.service;

import com.test.barogo.config.JwtUtil;
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
    private final JwtUtil jwtUtil;

    public MemberDto.CreateRes create(MemberDto.CreateReq req) {
        req.setPassword(passwordEncoder.encode(req.getPassword()));

        Member save = memberRepository.save(req.dtoToDomain(req));
        return new MemberDto.CreateRes(save);
    }

    public MemberDto.LoginRes login(MemberDto.LoginReq req) {
        String password = req.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        Member member = memberRepository.findByMemberInputIdAndEncodedPassword(req.getMemberInputId(), encodedPassword);
        if (member == null) {
            throw new RuntimeException("no member found");
        }

        MemberDto.LoginRes loginRes = new MemberDto.LoginRes(member);
        String token = jwtUtil.generateToken(member.getMemberId());
        loginRes.setJwt(token);

        return loginRes;
    }
}
