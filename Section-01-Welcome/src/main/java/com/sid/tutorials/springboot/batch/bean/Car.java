package com.sid.tutorials.springboot.batch.bean;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car implements Serializable {
    private Integer id;
    private String make;
    private String model;
    private String color;
    private Integer year;
    private Double price;
}
