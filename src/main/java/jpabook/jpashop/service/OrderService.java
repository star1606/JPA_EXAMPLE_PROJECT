package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;



    /*
    * 주문
    *
    * */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 설정
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // 이거 외에 다른 스타일의 생성 로직을 막아줘야한다.
        // OrderItem orderItem = new OrderItem(); protected로 막음

        // 주문 생성
        Order order = Order.createdOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        // 하나만 저장해줘도 cascade = CascadeType.ALL 해줘서 다 persist해줌
        // Cascade의 범위는? : 참조 다른데서 안하는데만
        // oritem -> order만 참조,,,,,,,,, 다른 것이 참조할 수 없는 경우  // 다른곳에서 갖다쓰면 Cascade쓰면안됨
        // private Owner일 경우
        return order.getId();
    }


    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {

        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel();
    }


    // 단순한 조회는 컨트롤러 만들어도된다.
    // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
