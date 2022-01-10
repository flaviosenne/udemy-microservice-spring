package com.microservice.crud.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product  implements Serializable {

    private static final long serialVersionUID = 6256661164519340686L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private  String name;

    @Column(name = "stock", nullable = false, length = 10)
    private Integer stock;

    @Column(name = "price", nullable = false, length = 10)
    private Double price;

}
