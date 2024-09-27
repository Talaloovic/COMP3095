package ca.gbc.productmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value="product")
// Document is an entity/table
public class Product {

    // assign id to primary key
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
