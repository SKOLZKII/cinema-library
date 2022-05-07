package com.cousework.films.controllers;

import com.cousework.films.entity.Actor;
import com.cousework.films.entity.Director;
import com.cousework.films.entity.Genre;
import com.cousework.films.entity.Post;
import com.cousework.films.repo.ActorRepository;
import com.cousework.films.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.text.SimpleDateFormat;
import java.util.stream.IntStream;

@Controller
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/actors")
    public String actorsMain (Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
                              )
    {
        Page<Actor> actors = actorRepository.findAll(PageRequest.of(page, 3));
        //Iterable<Actor> actors = actorRepository.findAll();
        model.addAttribute("actors", actors);
        model.addAttribute("numbers", IntStream.range(0,actors.getTotalPages()).toArray());
        return "actors";
    }

    @GetMapping("/actors/new/add")
    public String actorsAdd (Model model){
        model.addAttribute("actor", new Actor());
        return "actors-add";
    }

    @PostMapping("/actors/new/add")
    public String actorsAddPost(@ModelAttribute("actor") @Valid Actor actor,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "actors-add";
        }
        actorRepository.save(actor);
        return "redirect:/actors";
    }

    @GetMapping("/actors/{id}")
    public String actorDetails (@PathVariable(value = "id") long id,
                                Model model){
        if(!actorRepository.existsById(id)){
            return "redirect:/actors";
        }
        Optional<Actor> actor = actorRepository.findById(id);
        ArrayList<Actor> res = new ArrayList<>();
        actor.ifPresent(res::add);
        model.addAttribute("actor", res);

        Iterable<Post> posts = postRepository.findPostsByActor(id);
        model.addAttribute("posts", posts);

        return "actors-details";
    }

    @GetMapping("/actors/{id}/edit")
    public String actorsEdit (@PathVariable(value = "id") long id,
                              Model model){
        if(!actorRepository.existsById(id)){
            return "redirect:/actors";
        }
        Optional<Actor> actor = actorRepository.findById(id);
        ArrayList<Actor> res = new ArrayList<>();
        actor.ifPresent(res::add);
        model.addAttribute("actor", res);
        return "actors-edit";
    }

    @PostMapping("/actors/{id}/edit")
    public String actorPostUpdate(@PathVariable(value = "id") long id,
                                  @ModelAttribute("actor") @Valid Actor actor,
                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "actors-edit";
        }

        actorRepository.save(actor);

        return "redirect:/actors";
    }



    @PostMapping("/actors/{id}/remove")
    public String actorPostDelete(@PathVariable(value = "id") long id, Model model){
        Actor actor = actorRepository.findById(id).orElseThrow();

        actorRepository.DeleteActorFilmRel(id);
        actorRepository.delete(actor);
        return "redirect:/actors";
    }

    @GetMapping("/actors/search")
    public String actorsSearch (@Param("keyword") String keyword,
                                @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                Model model){
        Page<Actor> actors = actorRepository.findByNameLike(PageRequest.of(page, 3), keyword);
        model.addAttribute("actors", actors);
        model.addAttribute("numbers", IntStream.range(0,actors.getTotalPages()).toArray());
        return "actors";
    }
}
