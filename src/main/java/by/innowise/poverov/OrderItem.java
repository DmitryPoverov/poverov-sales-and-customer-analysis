package by.innowise.poverov;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class OrderItem {
    private String productName;
    private int quantity;
    private double price;
    private Category category;
}

