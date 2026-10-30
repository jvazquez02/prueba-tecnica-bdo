package com.bdo.pruebaTecnica.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String email;
}
