package com.micoservice.payment.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.micoservice.payment.entity.Product;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@JsonPropertyOrder({"id","stock"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductVO extends RepresentationModel<ProductVO> implements Serializable {

    private static final long serialVersionUID = 5038799822580277889L;


    @JsonProperty("id")
    private Long id;

    @JsonProperty("stock")
    private Integer stock;

    public static ProductVO create(Product product){
        return new ModelMapper().map(product, ProductVO.class);
    }

    public static Product create(ProductVO product){
        return new ModelMapper().map(product, Product.class);
    }
}
