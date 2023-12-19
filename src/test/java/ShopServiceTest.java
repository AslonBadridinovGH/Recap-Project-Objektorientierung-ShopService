import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest(){

        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")),OrderStatus.PROCESSING, ZonedDateTime.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());

    }

    @Test
    void addOrderTest_whenNotFoundProductId_expect() {

        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {

        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //THEN
        assertThrows(NoSuchElementException.class, () -> shopService.addOrder(productsIds));
    }

    @Test
    void getOrdersTest_whenOrderStatus_expectedList(){

        // GIVEN
        ShopService shopService = new ShopService();

        //WHEN
        List<Order> ordersWithStatus = shopService.getOrdersWithStatus(OrderStatus.PROCESSING);

        //THEN
        List<Order> expected = new ArrayList<>();
        assertEquals(expected,ordersWithStatus);
    }

    @Test
    void updateOrderTest_whenOrderIdAndOrderStatus(){

        // GIVEN
        ShopService shopService = new ShopService();
        OrderRepo orderRepo = new OrderMapRepo();

        //WHEN
        Optional<Order> id = shopService.updateOrder("Id", OrderStatus.PROCESSING);

        Optional<Order> orderById = Optional.ofNullable(orderRepo.getOrderById("id"));

        //THEN
        assertEquals(orderById, id);

    }
}
