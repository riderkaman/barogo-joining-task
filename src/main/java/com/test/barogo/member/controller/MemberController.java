package com.test.barogo.member.controller;

import com.test.barogo.member.dto.MemberDto;
import com.test.barogo.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody @Valid MemberDto.RegisterReq req) {

        return ResponseEntity.status(HttpStatus.OK).body("userId");
    }

}
