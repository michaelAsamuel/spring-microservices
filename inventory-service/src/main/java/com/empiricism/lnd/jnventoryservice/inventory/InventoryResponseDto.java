package com.empiricism.lnd.jnventoryservice.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponseDto {
    private String skuCode;
    private boolean isInStock;

}
