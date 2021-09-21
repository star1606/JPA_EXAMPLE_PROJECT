package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 트랜잭션은 롤백을 한다 (테스트 반복해서 써야되기 때문에)
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
   // @Rollback(false) // insert문 나가는거 볼 수 있음
    public void 회원가입() throws Exception{
        // given
        Member member = new Member();
        member.setName("kim");


        // when
        Long saveId = memberService.join(member);


        // then
        em.flush(); // 쿼리 날림
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");


        // when
//        memberService.join(member1);
//        try {
//            memberService.join(member2); // 예외가 발생해야한다!!
//        } catch (IllegalStateException e) {
//            return;
//        }
         memberService.join(member1);
         memberService.join(member2);


        // then
        fail("예외가 발생해야 한다.");
    }

}