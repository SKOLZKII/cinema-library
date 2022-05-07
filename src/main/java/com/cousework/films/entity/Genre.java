package com.cousework.films.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String genre_name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "followed_genres",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> genrePosts = new ArrayList<>();

    public void followPost(Post post){
        genrePosts.add(post);
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getGenre_name() {return genre_name;}
    public void setGenre_name(String genre_name) {this.genre_name = genre_name;}

    public Genre() {
    }
}