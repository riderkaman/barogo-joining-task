package com.test.barogo.member.controller;

import com.test.barogo.member.dto.MemberDto;
import com.test.barogo.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<MemberDto.CreateRes> create(@RequestBody @Valid MemberDto.CreateReq req) {
        MemberDto.CreateRes createRes = memberService.create(req);
        return ResponseEntity.status(HttpStatus.OK).body(createRes);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberDto.LoginRes> login(@RequestBody @Valid MemberDto.LoginReq req) {
        MemberDto.LoginRes loginRes = memberService.login(req);
        return ResponseEntity.status(HttpStatus.OK).body(loginRes);
    }

}
