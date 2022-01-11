package com.micoservice.payment.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.micoservice.payment.entity.ProductSale;
import com.micoservice.payment.entity.Sale;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonPropertyOrder({"id","date","totalValue","products"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SaleVO extends RepresentationModel<SaleVO> implements Serializable {

    private static final long serialVersionUID = 3915574773783470554L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("products")
    private List<ProductSale> products;

    @JsonProperty("totalValue")
    private Double totalValue;

    public static SaleVO create(Sale sale){
        return new ModelMapper().map(sale, SaleVO.class);
    }

    public static Sale create(SaleVO sale){
        return new ModelMapper().map(sale, Sale.class);
    }
}
