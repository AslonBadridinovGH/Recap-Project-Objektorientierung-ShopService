import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ShopService {

     private  ProductRepo productRepo = new ProductRepo();
     private   OrderRepo orderRepo = new OrderMapRepo();


    public Order addOrder(List<String> productIds) {

        List<Product> products = new ArrayList<>();

        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new NoSuchElementException("Product mit der Id: "+ productId + " konnte nicht bestellt werden!");
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.IN_DELIVERY, ZonedDateTime.now());

        return orderRepo.addOrder(newOrder);
    }


    public List<Order> getOrdersWithStatus(OrderStatus orderStatus){

        OrderListRepo orderListRepo = new OrderListRepo();
        return orderListRepo.getOrders().stream().filter(order -> order.orderStatus().equals(orderStatus)).toList();
    }


     public Optional<Order> updateOrder(String orderId, OrderStatus  orderStatus){

       Optional<Order> orderById = Optional.ofNullable(orderRepo.getOrderById(orderId));

       if (orderById.isPresent()){

           Order order = orderById.get();
           Order newOrder = order.withOrderStatus(orderStatus);
           orderRepo.removeOrder(orderId);
           orderRepo.addOrder(newOrder);
           return Optional.of(newOrder);
       }else {
           return Optional.empty();
       }

     }
}
