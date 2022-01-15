package com.micoservice.payment.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.micoservice.payment.entity.Sale;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@JsonPropertyOrder({"id","date","totalValue","products"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class SaleVO extends RepresentationModel<SaleVO> implements Serializable {

    private static final long serialVersionUID = 3915574773783470554L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("products")
    private List<ProductSaleVO> products;

    @JsonProperty("totalValue")
    private Double totalValue;

    public static SaleVO create(Sale sale){
        return SaleVO.builder()
                .id(sale.getId())
                .date(sale.getDate())
                .totalValue(sale.getTotalValue())
                .products(sale.getProducts().stream()
                        .map(ProductSaleVO::create)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Sale create(SaleVO sale){
        return Sale.builder()
                .id(sale.getId())
                .date(sale.getDate())
                .totalValue(sale.getTotalValue())
                .products(sale.getProducts().stream()
                        .map(ProductSaleVO::create)
                        .collect(Collectors.toList()))
                .build();
    }
}
