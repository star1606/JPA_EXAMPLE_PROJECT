//package jpabook.jpashop;
//
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MemberRepositoryTest {
//
//    @Autowired MemberRepository memberRepository;
//
//
//
//    @Test
//    @Transactional // 트랜잭션 걸어줘야 함
//    public void testMember () throws Exception {
//
//        // given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        // when
//        Long saveId = memberRepository.save(member);
//        Member findMember = memberRepository.find(saveId);
//
//
//        // then 검증
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member);
//        System.out.println("findMember == member : " + (findMember == member));
//        // 같은 트랜잭션이라 기존에 관리하던게 나와서 값이 같다
//
//    }
//}
