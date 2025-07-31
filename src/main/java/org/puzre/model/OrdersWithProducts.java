package org.puzre.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrdersWithProducts {

    private long order_id;
    private long product_id;
    private Long quantity;

}
