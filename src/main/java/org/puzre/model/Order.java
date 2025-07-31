package org.puzre.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Order {

    private Long id;
    private Status status;
    private BigDecimal total;
    private long user_id;

}
