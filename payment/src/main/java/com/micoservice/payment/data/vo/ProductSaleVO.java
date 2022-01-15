package com.micoservice.payment.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.micoservice.payment.entity.ProductSale;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@JsonPropertyOrder({"id","productId", "quantity"})
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductSaleVO extends RepresentationModel<ProductVO> implements Serializable {

    private static final long serialVersionUID = -1948492305612973779L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("quantity")
    private Integer quantity;

    public static ProductSale create(ProductSaleVO productSale){
        return new ModelMapper().map(productSale, ProductSale.class);
    }


    public static ProductSaleVO create(ProductSale productSale){
        return new ModelMapper().map(productSale, ProductSaleVO.class);
    }

}
