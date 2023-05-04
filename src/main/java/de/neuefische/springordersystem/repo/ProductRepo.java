package de.neuefische.springordersystem.repo;

import de.neuefische.springordersystem.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepo {

    private Map<Integer, Product> products = new HashMap<>(Map.of(
            1, new Product(1, "Apfel"),
            2, new Product(2, "Banane"),
            3, new Product(3, "Zitrone"),
            4, new Product(4, "Mandarine"))
    );

    public Product getProduct(int id) {
        return products.get(id);
    }

    public List<Product> listProducts() {
        return new ArrayList<>(products.values());
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }
}
