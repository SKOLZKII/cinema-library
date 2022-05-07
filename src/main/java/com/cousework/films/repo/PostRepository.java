package com.cousework.films.repo;

import com.cousework.films.entity.Genre;
import com.cousework.films.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM post p INNER JOIN actor_post ap ON p.id = ap.post_id AND ap.actor_id = ?1", nativeQuery = true)
    List<Post> findPostsByActor(Long id);

    @Query(value = "SELECT * FROM post p INNER JOIN director_post ap ON p.id = ap.post_id AND ap.director_id = ?1", nativeQuery = true)
    List<Post> findPostsByDirector(Long id);

    @Query("SELECT p FROM Post p WHERE LOWER(p.film_name)  LIKE %?1%")
    Page<Post> findByNameLike(Pageable pageable, String keyword);

    @Query(value = "SELECT p.id, p.film_name, p.budget, p.year, p.poster, p.title FROM post p INNER JOIN followed_genres fg ON p.id = fg.post_id INNER JOIN genre g ON fg.genre_id = g.id AND g.genre_name = ?1", nativeQuery = true)
    List<Post> QUERYfindFilmByGenre(String genre_name);


    //COUNTRY_QUERY

    @Query(value = "SELECT p.id, p.film_name, p.budget, p.year, p.poster, p.title FROM post p INNER JOIN country_post cp ON p.id = cp.post_id INNER JOIN country c ON cp.country_id = c.id AND c.name = ?1", nativeQuery = true)
    List<Post> QueryFindFilmByCountry(String country_name);

    //DELETE_FILM

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM followed_genres WHERE post_id = ?1", nativeQuery = true)
    void QUERYdeleteFilmGenre(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM actor_post WHERE post_id = ?1", nativeQuery = true)
    void QUERYdeleteFilmActor(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM director_post WHERE post_id = ?1", nativeQuery = true)
    void QUERYdeleteFilmDirector(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM country_post WHERE post_id = ?1", nativeQuery = true)
    void QUERYdeleteFilmCountry(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM post WHERE id = ?1", nativeQuery = true)
    void QUERYdeleteFilm(long id);


}
