package ua.maestr0.second.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import ua.maestr0.second.anotation.Column;
import ua.maestr0.second.anotation.Entity;
import ua.maestr0.second.anotation.Id;
import ua.maestr0.second.anotation.Table;

@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;
    private String phone;

    public Customer() {}

    public Customer(String firstName,
                    String lastName,
                    String email,
                    String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
}
