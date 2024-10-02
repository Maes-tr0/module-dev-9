package ua.maestr0.first.model;

import lombok.Builder;
import lombok.Data;
import ua.maestr0.first.anotation.RoundNumber;
import ua.maestr0.first.anotation.Trim;

@Data
@Builder
public class Student {
    @Trim
    private String firstName;
    @Trim
    private String lastName;
    @RoundNumber
    private double grade;
}
