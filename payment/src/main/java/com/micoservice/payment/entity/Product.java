package com.micoservice.payment.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = -2898573608203441921L;

    @Id
    private Long id;

    @Column(name = "stock", nullable = false, length = 10)
    private Integer stock;
}
