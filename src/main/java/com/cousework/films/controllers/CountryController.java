package com.cousework.films.controllers;

import com.cousework.films.entity.Country;
import com.cousework.films.entity.Genre;
import com.cousework.films.entity.Post;
import com.cousework.films.repo.CountryRepository;
import com.cousework.films.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CountryController {
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/countries")
    public String countryMain(@RequestParam(value = "countryId", defaultValue = "none") String countryId, Model countryModel, Model postModel){

        Iterable<Country> countries = countryRepository.selectAllCountries();
        countryModel.addAttribute("countries", countries);

        Iterable<Post> posts = postRepository.QueryFindFilmByCountry(countryId);
        postModel.addAttribute("posts", posts);

        return "country-main";
    }


}
