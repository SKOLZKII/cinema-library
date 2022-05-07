package com.cousework.films.repo;

import com.cousework.films.entity.Actor;
import com.cousework.films.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ActorRepository extends CrudRepository<Actor, Long> {

    Page<Actor> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM actor ORDER BY first_name", nativeQuery = true)
    List<Actor> QUERYfindActors();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO actor_post (actor_id, post_id) VALUES (?1, ?2)", nativeQuery = true)
    void InsertActorPost(long genre_id, long post_id);

    @Query(value = "SELECT * FROM actor a INNER JOIN actor_post ap ON ap.actor_id = a.id AND ap.post_id = ?1", nativeQuery = true)
    List<Actor> findActorsOnFilm(Long id);

    @Query("SELECT a FROM Actor a WHERE LOWER(a.first_name) LIKE %?1% OR LOWER (a.last_name) LIKE %?1%")
    Page<Actor> findByNameLike(Pageable pageable, String keyword);

    @Query(value = "SELECT p.film_name, p.year, p.budget, p.title, p.poster FROM post p INNER JOIN actor_post ap ON p.id = ap.post_id AND ap.actor_id = ?1", nativeQuery = true)
    List<Post> findPostsByActor(Long id);

    @Query(value = "SELECT born FROM actor WHERE id = ?1", nativeQuery = true)
    Date actorBirthday (Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM actor_post WHERE actor_id = ?1", nativeQuery = true)
    void DeleteActorFilmRel(long id);

}
