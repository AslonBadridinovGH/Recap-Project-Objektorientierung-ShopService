package bonus;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Product laptop = new Product("1", "laptop");
        Product printer = new Product("2", "printer");
        Product handy = new Product("3", "Handy");
        Product pc = new Product("4", "PC");
        Product table = new Product("5", "Table");
        Product book = new Product("6", "Book");

        ProductRepo productRepo = new ProductRepo(List.of(laptop, printer, handy, pc, table, book));

        Order order = new Order("1", List.of(laptop,printer,handy), OrderStatus.PROCESSING, ZonedDateTime.now());
        Order order1 = new Order("2", List.of(laptop,handy,pc), OrderStatus.PROCESSING, ZonedDateTime.now());
        Order order2 = new Order("3", List.of(pc,table,book), OrderStatus.PROCESSING, ZonedDateTime.now());

        Map<String, Order> orderMap = new HashMap<>();
        orderMap.put(order.id(), order);
        orderMap.put(order1.id(), order1);
        orderMap.put(order2.id(), order2);

        OrderMapRepo orderMapRepo = new OrderMapRepo(orderMap);

         IdService idService = new IdService(UUID.randomUUID());

         ShopService shopService = new ShopService(idService, productRepo, orderMapRepo);
         System.out.println(shopService);

    }


}
