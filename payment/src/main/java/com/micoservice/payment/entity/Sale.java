package com.micoservice.payment.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "sale")
public class Sale implements Serializable {

    private static final long serialVersionUID = 6617464312663331877L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "MM/dd/yyy")
    @Column(name = "date", nullable = false)
    private Date date;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sale", cascade = CascadeType.REFRESH)
    private List<ProductSale> products;

    @Column(name = "totalValue", nullable = false, length = 10)
    private Double totalValue;


}
