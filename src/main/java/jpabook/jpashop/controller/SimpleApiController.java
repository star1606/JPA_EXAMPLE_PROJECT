package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne ( ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class SimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/vi/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for (Order order : all) {
            order.getMember().getName(); // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
        return all;

    }


    // 쿼리 5번 나감
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {

        // order 2개 조회
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        // N + 1 문제 첫번째 쿼리가 나감 N번만큼 가져옴
        // N + 1  + 회원 N + 배송 N => 5
        // Eager로 하면 예측이 안됨.
        List<SimpleOrderDto> result = orders.stream()
                    .map(o -> new SimpleOrderDto(o))
                    .collect(Collectors.toList());

        return result;
    }


    // 쿼리 한번만 나감
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {

        return orderSimpleQueryRepository.findOrderDtos();

    }



    @Data
    static class SimpleOrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        // DTO가 엔티티를 파라미터로 받는 것은 크게 문제가 되지 않는다.
        // 생성자에다가 DTO의 멤버변수를 다 set함
        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화

        }
    }


}
