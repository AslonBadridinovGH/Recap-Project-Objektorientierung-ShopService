package bonus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@RequiredArgsConstructor
public class ShopService {

  /*   private final IdService uuid = new IdService();
     private final ProductRepo productRepo = new ProductRepo();
     private final OrderRepo orderRepo = new OrderMapRepo();*/

    private  IdService uuid = new IdService();
    private  ProductRepo productRepo = new ProductRepo();
    private  OrderRepo orderRepo = new OrderMapRepo();



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

        List<Order> orders = orderRepo.getOrders();
        List<Order> orderList = orders.stream().filter(order -> order.orderStatus().equals(orderStatus)).toList();

         Map<String, Order> map = new HashMap<>();

        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).zonedDateTime().isBefore(orderList.get(i+1).zonedDateTime())){
               map.put( orderList.get(i).id(), orderList.get(i));
            }
        }

        return map;
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
