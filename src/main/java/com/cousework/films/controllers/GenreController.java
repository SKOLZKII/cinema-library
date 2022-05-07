package com.cousework.films.controllers;

import com.cousework.films.entity.Genre;
import com.cousework.films.entity.Post;
import com.cousework.films.repo.GenreRepository;
import com.cousework.films.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GenreController {

    @Autowired
    GenreRepository genreRepository;
    @Autowired
    PostRepository postRepository;

    @GetMapping("/genres")
    public String genresMain (@RequestParam(value = "genreId", defaultValue = "none") String genreId, Model genresModel, Model postsModel){

        Iterable<Genre> genres = genreRepository.QUERYfindAllOrderByName();
        genresModel.addAttribute("genres", genres);

        Iterable<Post> posts = postRepository.QUERYfindFilmByGenre(genreId);
        postsModel.addAttribute("posts", posts);

        return "genres-main";
    }


}
