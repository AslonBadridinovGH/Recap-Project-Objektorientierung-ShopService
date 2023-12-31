import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepo {

    private List<Product> products;

    public ProductRepo() {
        products = new ArrayList<>();
        products.add(new Product("1", "Apfel"));
    }

    public List<Product> getProducts() {
        return products;
    }


    public Optional<Product> getProductById(String id) {
        for (Product product : products) {
            Optional<Product> optionalProduct = Optional.ofNullable(product);
            if (optionalProduct.isPresent()){
                Product productInOp = optionalProduct.get();
                if (productInOp.id().equals(id)) {
                    return optionalProduct;
                }
            }
            return Optional.empty();

        }return Optional.empty();
    }

    public Product addProduct(Product newProduct) {
        products.add(newProduct);
        return newProduct;
    }

    public void removeProduct(String id) {
        for (Product product : products) {
           if (product.id().equals(id)) {
               products.remove(product);
               return;
           }
        }
    }
}
