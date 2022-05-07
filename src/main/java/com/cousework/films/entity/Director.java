package com.cousework.films.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.temporal.WeekFields.ISO;

@Entity
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Заповніть поле")
    @Size(min = 5, max = 30, message = "Значення повинно бути в діапазоні від 2 до 30 символів")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Поле не може бути пустим")
    @Past(message = "Invalid date")
    private Date born;

    private String picture;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "director_post",
            joinColumns = @JoinColumn(name = "director_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> directorPosts = new ArrayList<>();

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Date getBorn() {return born;}

    public void setBorn(Date born) {this.born = born;}

    public String getPicture() {return picture;}

    public void setPicture(String picture) {this.picture = picture;}

    public Director() {
    }

    public Director(Long id, String name, Date born, String picture) {
        this.id = id;
        this.name = name;
        this.born = born;
        this.picture = picture;
    }
}

