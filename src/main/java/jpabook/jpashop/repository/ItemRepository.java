package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            // id가 없다는 것은 새로 생성하는 객체라는 뜻임 (신규 등록)
            em.persist(item);
        } else {
            // 이미 등록되있는걸 가져옴 ( update)
            em.merge(item); // update
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    // 여러건을 가져오려면 쿼리를 써야함
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
