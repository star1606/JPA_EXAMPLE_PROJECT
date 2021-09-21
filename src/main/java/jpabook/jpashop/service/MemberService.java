package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // 필수임
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 회원가입

    // 중복회원은 안된다
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복회원 검증
        // 동시에 save 호출될 수도 있다.
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");


        }


    }

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        // 영속 상태에서 set 해줌
        member.setName(name);
        // 변경감지함
    }
}
