package jpabook.jpashop.repository.order.simplequery;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    // 이 레포지토리는 순수한 엔티티를 조회하는게 아니라, 화면에 박힌 것이다.
    // queryService, queryRepository로 뽑아낸다.

    private final EntityManager em;
    public List<OrderSimpleQueryDto> findOrderDtos() {
        // 쿼리를 한 번 할 때 JPQL로 짜서 가져옴. 재사용성이 안 좋다.

        // 맵핑이 안됨 이 때는 entity나 value object만 반환가능함
        // 나머지 DTO는 안됨 그래서 new operation 써줘야 함.
        // 셀렉트절에서 원하는 것만 셀렉트해줌
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();

        // DTO로 조회했기 때문에 변경 못함
        // 조회전용으로 화면에 딱 맞춰서 쓰는 용도.
    }
}




