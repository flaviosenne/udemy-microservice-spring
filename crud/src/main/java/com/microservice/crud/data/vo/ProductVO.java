package com.microservice.crud.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.microservice.crud.entity.Product;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.io.Serializable;

@JsonPropertyOrder({"id", "name","price","stock"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ProductVO implements Serializable {

    private static final long serialVersionUID = -7218244318806305671L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private  String name;

    @JsonProperty("stock")
    private Integer stock;

    @JsonProperty("price")
    private Double price;

    public static ProductVO create(Product product){
        return new ModelMapper().map(product, ProductVO.class);
    }

    public static Product create(ProductVO product){
        return new ModelMapper().map(product, Product.class);
    }

}
