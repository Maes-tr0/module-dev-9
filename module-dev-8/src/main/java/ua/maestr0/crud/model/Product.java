package ua.maestr0.crud.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import ua.maestr0.crud.anotation.Column;
import ua.maestr0.crud.anotation.Entity;
import ua.maestr0.crud.anotation.Id;
import ua.maestr0.crud.anotation.Table;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    private String description;
    private BigDecimal price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    public Product(){}

    public Product(String productName,
                   String description,
                   BigDecimal price,
                   Integer stockQuantity) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
