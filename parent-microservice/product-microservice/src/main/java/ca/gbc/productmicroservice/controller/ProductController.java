package ca.gbc.productmicroservice.controller;

import ca.gbc.productmicroservice.dto.ProductRequest;
import ca.gbc.productmicroservice.dto.ProductResponse;
import ca.gbc.productmicroservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // getting information
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProduct();
    }

    //http://localhost:8081/api/product/
    @PutMapping("/{productId}")
    //PutMapping means updating
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    // we can return the status in annotation or when return the response entity, add it there

    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest,
                                           @PathVariable("productId") String id){
        String updatedProductId = productService.updateProduc t(productRequest, id);

        // set the location header attribute
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/product/" + updatedProductId);

        // return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") String id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
