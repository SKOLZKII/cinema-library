package com.cousework.films.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "Actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Заповніть поле")
    @Size(min = 1, max = 30, message = "Доступно лише 30 символів")
    private String first_name;

    @NotEmpty(message = "Заповніть поле")
    @Size(min = 1, max = 30, message = "Доступно лише 30 символів")
    private String last_name;

    private String picture;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Поле не може бути пустим")
    @Past(message = "Invalid date")
    private Date born;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "actor_post",
            joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> actorPosts = new ArrayList<>();

    public Date getBorn() {
        return born;
    }
    public void setBorn(Date born) {
        this.born = born;
    }

    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Actor() {
    }

    public Actor(String first_name, String last_name, Date born, String picture) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.picture = picture;
        this.born = born;
    }

    public Actor(Long id, String first_name, String last_name, String picture, Date born, List<Post> actorPosts) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.picture = picture;
        this.born = born;
        this.actorPosts = actorPosts;
    }
}
