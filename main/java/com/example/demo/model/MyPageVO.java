package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MyPageVO {
    private int id;
    private String instagram;
    private String blog;
    private String location;
    private String style;
    private int height;
    private int weight;
}
