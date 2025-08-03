package org.puzre.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class Order {

    private Long id;
    private Status status;
    private BigDecimal total;
    private long userId;

}
