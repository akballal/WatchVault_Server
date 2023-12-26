package com.movierepo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;

@Entity  @Component @Table(name="app_data")
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int dataid;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp watchedon;

    public int getDataid() {
        return dataid;
    }

    public void setDataid(int dataid) {
        this.dataid = dataid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getWatchedon() {
        return watchedon;
    }

    public void setWatchedon(Timestamp watchedon) {
        this.watchedon = watchedon;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private float rating;
    private String type;

//    @Lob
//    @Column(name = "photo", columnDefinition = "bytea")
//    private byte[] photo;

    @Column(name = "photo", columnDefinition = "bytea")
    private byte[] photo;


    @ManyToOne
    @JoinColumn(name = "username") // This is the foreign key column name in the app_data table
    private User user;
}
