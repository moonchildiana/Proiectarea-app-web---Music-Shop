package com.example.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String order;

    private String name;

    private String adress;

    private String nr_tel;

    private Enum products;

    private Long price;

    private String order_status;

}
