package com.sid.tutorials.springboot.batch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "car")
public class CarEntity implements Serializable {
    @Id
    private Integer id;
    @Column
    private String make;
    @Column
    private String model;
    @Column
    private String color;
    @Column
    private Integer year;
    @Column
    private Double price;
}
