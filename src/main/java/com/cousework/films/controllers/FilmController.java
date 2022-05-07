package com.cousework.films.controllers;

import com.cousework.films.entity.*;
import com.cousework.films.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class FilmController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/")
    public String filmMain (Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
                            )
    {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, 3));
        model.addAttribute("posts", posts);
        model.addAttribute("numbers", IntStream.range(0,posts.getTotalPages()).toArray());
        return "film-main";
    }

    @GetMapping("/search")
    public String filmSearch (@Param("keyword") String keyword,
                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              Model model){
        Page<Post> posts = postRepository.findByNameLike(PageRequest.of(page, 3), keyword);
        model.addAttribute("posts", posts);
        model.addAttribute("numbers", IntStream.range(0,posts.getTotalPages()).toArray());
        return "film-main";
    }

    @GetMapping("/new/add")
    public String filmAdd (Model model){

        model.addAttribute("post", new Post());

        Iterable<Genre> genres = genreRepository.QUERYfindGenres();
        model.addAttribute("genres", genres);

        Iterable<Actor> actors = actorRepository.QUERYfindActors();
        model.addAttribute("actors", actors);

        Iterable<Director> directors = directorRepository.QUERYfindDirectors();
        model.addAttribute("directors", directors);

        Iterable<Country> countries = countryRepository.QUERYfindCountries();
        model.addAttribute("countries", countries);

        return "film-add";
    }

    @PostMapping("/new/add")
    public String filmPostAdd(@ModelAttribute("post") @Valid Post post,
                              BindingResult bindingResult,
                              @RequestParam(value = "genres", required = false) List<Long> genres,
                              @RequestParam(value = "actors", required = false) List<Long> actors,
                              @RequestParam(value = "directors", required = false) List<Long> directors,
                              @RequestParam(value = "country", required = false) List<Long> country){
        if(bindingResult.hasErrors()){
            return "film-add";
        }
        postRepository.save(post);
        long postId = post.getId();

        if(genres != null) {
            for (int i = 0; i < genres.size(); i++) {
                genreRepository.QUERYinsertGenrePost(genres.get(i), postId);
            }
        }

        if(actors != null) {
            for (int i = 0; i < actors.size(); i++) {
                actorRepository.InsertActorPost(actors.get(i), postId);
            }
        }

        if(directors != null) {
            for (int i = 0; i < directors.size(); i++) {
                directorRepository.InsertDirectorPost(directors.get(i), postId);
            }
        }

        if(country != null) {
            for (int i = 0; i < country.size(); i++) {
                countryRepository.InsertCountryPost(country.get(i), postId);
            }
        }

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String filmDetails (@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);

        Iterable<Genre> genres = genreRepository.findWhatNeed(id);
        model.addAttribute("genres", genres);

        Iterable<Director> directors = directorRepository.findDirectorsOnFilm(id);
        model.addAttribute("directors", directors);

        Iterable<Country> countries = countryRepository.findCountryOnFilm(id);
        model.addAttribute("countries", countries);

        Iterable<Actor> actors = actorRepository.findActorsOnFilm(id);
        model.addAttribute("actors", actors);
        return "film-details";
    }

    @GetMapping("/{id}/edit")
    public String filmEdit (@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);

        Iterable<Genre> genres = genreRepository.QUERYfindGenres();
        model.addAttribute("genres", genres);

        Iterable<Actor> actors = actorRepository.QUERYfindActors();
        model.addAttribute("actors", actors);

        Iterable<Director> directors = directorRepository.QUERYfindDirectors();
        model.addAttribute("directors", directors);

        Iterable<Country> countries = countryRepository.QUERYfindCountries();
        model.addAttribute("countries", countries);


        return "film-edit";
    }

    @PostMapping("/{id}/edit")
    public String filmPostUpdate(@PathVariable(value = "id") long id,
                                 @ModelAttribute("post") @Valid Post post,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "genres", required = false) List<Long> genres,
                                 @RequestParam(value = "actors", required = false) List<Long> actors,
                                 @RequestParam(value = "directors", required = false) List<Long> directors,
                                 @RequestParam(value = "country", required = false) List<Long> country){

        if(bindingResult.hasErrors()){
            return "film-edit";
        }

        postRepository.save(post);

        long postId = post.getId();
        if(country != null ){
            postRepository.QUERYdeleteFilmCountry(postId);
            for (int i = 0; i < country.size(); i++){
                countryRepository.InsertCountryPost(country.get(i), postId);
            }
        }
        if(directors != null) {
            postRepository.QUERYdeleteFilmDirector(postId);
            for (int i = 0; i < directors.size(); i++){
                directorRepository.InsertDirectorPost(directors.get(i), postId);
            }
        }
        if(actors != null) {
            postRepository.QUERYdeleteFilmActor(postId);
            for (int i = 0; i < actors.size(); i++){
                actorRepository.InsertActorPost(actors.get(i), postId);
            }
        }
        if(genres != null) {
            postRepository.QUERYdeleteFilmGenre(postId);
            for (int i = 0; i < genres.size(); i++) {
                genreRepository.QUERYinsertGenrePost(genres.get(i), postId);
            }
        }

        return "redirect:/";
    }



    @PostMapping("/{id}/remove")
    public String filmPostDelete(@PathVariable(value = "id") long id, Model model){

        postRepository.QUERYdeleteFilmGenre(id);
        postRepository.QUERYdeleteFilmActor(id);
        postRepository.QUERYdeleteFilmDirector(id);
        postRepository.QUERYdeleteFilmCountry(id);
        postRepository.QUERYdeleteFilm(id);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
