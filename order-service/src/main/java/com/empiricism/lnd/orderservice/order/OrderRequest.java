package com.empiricism.lnd.orderservice.order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    //@OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineitemDto> orderLineitemDtoList;
}
