package ca.gbc.productmicroservice.service;

import ca.gbc.productmicroservice.dto.ProductRequest;
import ca.gbc.productmicroservice.dto.ProductResponse;
import ca.gbc.productmicroservice.model.Product;
import ca.gbc.productmicroservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

// this can be injected

@Service
@Slf4j
@RequiredArgsConstructor
// using lombok to avoid ProductRepository's constructor initialization
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        log.debug("Creating a new project {}", productRequest.name());
        // build a new product using builder
        // same thing as using new Product syntax but reduce some redundancy
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        // persist a product to the database
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());

        return new ProductResponse(product.getId(), product.getName(),
                product.getDescription(), product.getPrice());

    }

    @Override
    public List<ProductResponse> getAllProduct() {

        log.debug("Retrieving a list products");
        List<Product> products = productRepository.findAll();
        // stream is like javascript array
        return products.stream().map(product ->
                mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product){
        return new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }

    @Override
    public String updateProduct(ProductRequest productRequest, String id) {
        log.debug("Updating a product with id {}", id);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Product product = mongoTemplate.findOne(query, Product.class);

        if(product != null) {
            product.setDescription(productRequest.description());
            product.setName(productRequest.name());
            product.setPrice(productRequest.price());

            //this returns the id of the new update item
            return productRepository.save(product).getId();
        }
        return id;
    }

    @Override
    public void deleteProduct(String id) {
        log.debug("Deleting a product with id {}", id);
        productRepository.deleteById(id);
    }
}
