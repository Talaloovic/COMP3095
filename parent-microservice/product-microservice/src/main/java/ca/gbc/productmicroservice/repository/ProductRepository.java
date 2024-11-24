package ca.gbc.productmicroservice.repository;

import ca.gbc.productmicroservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

//to create a product, just indicate the model by model name and the primary key type
//it'll do the rest
public interface ProductRepository extends MongoRepository<Product, String> {
}
