package org.puzre.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class Product {

    private Long id;
    private String name;
    private BigDecimal price;

}
