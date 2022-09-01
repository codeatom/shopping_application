package shopping.application.data.dao;

import shopping.application.model.Product;

import java.util.List;

public interface ProductDAO extends GenericCrud<Product, Integer> {
     List<Product> findByName(String name);

    List<Product> findByPriceBetween(double low, double high);
}
