package com.cousework.films.controllers;

import com.cousework.films.entity.Actor;
import com.cousework.films.entity.Director;
import com.cousework.films.entity.Post;
import com.cousework.films.repo.DirectorRepository;
import com.cousework.films.repo.PostRepository;
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
import java.util.Date;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class DirectorController {
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/directors")
    public String directorsMain(Model model,
                                @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
                                )
    {
        Page<Director> directors = directorRepository.findAll(PageRequest.of(page, 3));
        //Iterable<Director> directors = directorRepository.findAll();
        model.addAttribute("directors", directors);
        model.addAttribute("numbers", IntStream.range(0,directors.getTotalPages()).toArray());

        return "directors-main";
    }

    @GetMapping("/directors/new/add")
    public String directorsAdd(Model model){
        model.addAttribute("director", new Director());
        return "directors-add";
    }

    @PostMapping("/directors/new/add")
    public String directorsAddPost(@ModelAttribute("director") @Valid Director director,
                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "directors-add";
        }
        directorRepository.save(director);
        return "redirect:/directors";
    }

    @GetMapping("/directors/{id}")
    public String directorDetails (@PathVariable(value = "id") long id, Model model){
        if(!directorRepository.existsById(id)){
            return "redirect:/directors";
        }
        Optional<Director> director = directorRepository.findById(id);
        ArrayList<Director> res = new ArrayList<>();
        director.ifPresent(res::add);
        model.addAttribute("director", res);

        Iterable<Post> posts = postRepository.findPostsByDirector(id);
        model.addAttribute("posts", posts);

        return "director-details";
    }

    @GetMapping("/directors/{id}/edit")
    public String directorsEdit (@PathVariable(value = "id") long id,
                                 Model model){
        if(!directorRepository.existsById(id)){
            return "redirect:/directors";
        }
        Optional<Director> director = directorRepository.findById(id);
        ArrayList<Director> res = new ArrayList<>();
        director.ifPresent(res::add);
        model.addAttribute("director", res);
        return "director-edit";
    }

    @PostMapping("/directors/{id}/edit")
    public String directorPostUpdate(@PathVariable(value = "id") long id,
                                     @ModelAttribute("director") @Valid Director director,
                                     BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "director-edit";
        }
        directorRepository.save(director);

        return "redirect:/directors";
    }



    @PostMapping("/directors/{id}/remove")
    public String directorPostDelete(@PathVariable(value = "id") long id, Model model){
        Director director = directorRepository.findById(id).orElseThrow();
        directorRepository.DeleteDirectorFilmRel(id);
        directorRepository.delete(director);
        return "redirect:/directors";
    }

    @GetMapping("/directors/search")
    public String directorSearch (@Param("keyword") String keyword,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  Model model){
        Page<Director> directors = directorRepository.findByNameLike(PageRequest.of(page, 3), keyword);
        model.addAttribute("directors", directors);
        model.addAttribute("numbers", IntStream.range(0,directors.getTotalPages()).toArray());
        return "directors-main";
    }
}
