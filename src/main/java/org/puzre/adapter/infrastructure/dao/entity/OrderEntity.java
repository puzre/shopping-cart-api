package org.puzre.adapter.infrastructure.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.puzre.model.Status;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class OrderEntity {

    private Long id;
    private Status status;
    private BigDecimal total;
    private Long userId;

}
