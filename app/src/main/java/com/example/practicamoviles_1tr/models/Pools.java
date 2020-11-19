package com.example.practicamoviles_1tr.models;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class Pools implements Serializable {
    private String title;
    private Location location;
}
