package ua.maestr0.second.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import ua.maestr0.second.anotation.Column;
import ua.maestr0.second.anotation.Entity;
import ua.maestr0.second.anotation.Id;
import ua.maestr0.second.anotation.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    private String status;

    public Order() {}

    public Order(LocalDateTime orderDate, BigDecimal totalAmount, String status) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }
}
