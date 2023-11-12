package com.movierepo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity @Getter @Setter @Table(name="app_data")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dataid;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp watchedon;
    private float rating;
    private String type;

    @ManyToOne
    @JoinColumn(name = "username") // This is the foreign key column name in the app_data table
    private User user;
}
