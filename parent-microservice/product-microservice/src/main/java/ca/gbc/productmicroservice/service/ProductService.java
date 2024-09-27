package ca.gbc.productmicroservice.service;

import ca.gbc.productmicroservice.dto.ProductRequest;
import ca.gbc.productmicroservice.dto.ProductResponse;

import java.util.List;

//
public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProduct();
    String updateProduct(ProductRequest productRequest, String id);
    void deleteProduct(String id);
}
