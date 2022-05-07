package com.cousework.films.entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 1, max=50, message = "Введіть коректну назву фільму")
    private String film_name;
    @Size(min = 5, max = 500, message = "Замалий або завеликий опис")
    private String  title;
    private String poster;
    @Min(value = 1895, message = "Не вірно вказан рік")
    @Max(value = 2022, message = "Не вірно вказан рік")
    private int year;

    private int budget;

    @ManyToMany( mappedBy = "genrePosts")
    private List<Genre> genres = new ArrayList<Genre>();
    @ManyToMany(mappedBy = "actorPosts")
    private List<Actor> actors = new ArrayList<>();
    @ManyToMany(mappedBy = "countryPosts")
    private List<Country> countries = new ArrayList<>();
    @ManyToMany(mappedBy = "directorPosts")
    private List<Director> directors = new ArrayList<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFilm_name() {
        return film_name;
    }
    public void setFilm_name(String film_name) {
        this.film_name = film_name;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }


    public int getBudget() {
        return budget;
    }
    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {this.genres = genres;}
    public void addGenre(Genre genre){genres.add(genre);}



    public Post() {
    }

    public Post(String film_name, int year, int budget, String title, String poster) {
        this.film_name = film_name;
        this.title = title;
        this.poster = poster;
        this.year = year;
        this.budget = budget;
    }

    public Post(String film_name, String title, String poster, int year, int budget, List<Genre> genres) {
        this.film_name = film_name;
        this.title = title;
        this.poster = poster;
        this.year = year;
        this.budget = budget;
        this.genres = genres;
    }

}
