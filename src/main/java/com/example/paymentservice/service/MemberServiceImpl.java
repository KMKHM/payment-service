package com.example.paymentservice.service;

import com.example.paymentservice.entity.Member;
import com.example.paymentservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Member autoRegister() {
        Member member = Member.builder()
                .username("김명균")
                .email("kyun9151@naver.com")
                .address("경기도 의정부시")
                .build();
        return memberRepository.save(member);
    }
}
