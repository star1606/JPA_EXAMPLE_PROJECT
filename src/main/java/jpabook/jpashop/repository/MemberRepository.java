package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// 컴포넌트 스캔으로
@Repository
public class MemberRepository {


    // JPA 엔티티 매니저를 주입 받을 수 있음
   @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 리스트는 JPQL 필요함
    public List<Member> findAll(){

        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 특정 이름을 찾는 것
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
