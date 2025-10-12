package org.puzre.adapter.infrastructure.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class ProductEntity {

    private Long id;
    private String name;
    private BigDecimal price;

}
