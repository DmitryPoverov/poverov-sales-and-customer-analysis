package by.innowise.poverov;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

