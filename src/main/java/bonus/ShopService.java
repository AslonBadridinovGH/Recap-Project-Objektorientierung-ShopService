package bonus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.security.Provider;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class ShopService {

    // private ProductRepo productRepo = new ProductRepo();
    // private OrderRepo orderRepo = new OrderMapRepo();

    private IdService uuid;
    private ProductRepo productRepo;
    private OrderRepo orderRepo;

    public ShopService(IdService uuid, ProductRepo productRepo, OrderRepo orderRepo) {
        this.uuid = uuid;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public Order addOrder(List<String> productIds) {

        List<Product> products = new ArrayList<>();

        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                // System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                throw new NoSuchElementException("Product mit der Id: "+ productId + " konnte nicht bestellt werden!");
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.IN_DELIVERY, ZonedDateTime.now());

        return orderRepo.addOrder(newOrder);
    }


    public List<Order> getOrdersWithStatus(){

        OrderListRepo orderListRepo = new OrderListRepo();

        return orderListRepo.getOrders().stream().collect(Collectors.toList());
    }

    public Map<String, Order> getOldestOrderPerStatus(OrderStatus orderStatus){


           return null;
    }


    public void  updateOrder(String orderId, OrderStatus orderStatus){

       Optional<Order> orderById = Optional.ofNullable(orderRepo.getOrderById(orderId));

       if (orderById.isPresent()){

           Order order = orderById.get();
           Order newOrder = order.withOrderStatus(orderStatus);
           orderRepo.removeOrder(orderId);
           orderRepo.addOrder(newOrder);
       }

     }
}
