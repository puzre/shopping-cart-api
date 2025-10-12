package org.puzre.adapter.infrastructure.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserEntity {

    private Long id;
    private String name;
    private String phone;

}
