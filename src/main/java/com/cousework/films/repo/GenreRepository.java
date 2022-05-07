package com.cousework.films.repo;

import com.cousework.films.entity.Genre;
import com.cousework.films.entity.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {

    @Query(value = "SELECT p.id, g.genre_name FROM post p INNER JOIN followed_genres f ON p.id = f.post_id INNER JOIN genre g ON g.id = f.genre_id AND genre_name = 'Комедия'", nativeQuery = true)
    List<Genre> queryFindAll();

    @Query(value = "SELECT g.genre_name FROM genre g INNER JOIN followed_genres f ON g.id = f.genre_id INNER JOIN post p ON p.id = f.post_id WHERE p.id = ?1", nativeQuery = true)
    List<Genre> queryFindGenre(Long id);

    @Query(value = "SELECT g.genre_name FROM genre g INNER JOIN followed_genres f ON g.id = f.genre_id WHERE f.post_id = ?1", nativeQuery = true)
    List<String> findOneGenre(Long id);

    @Query(value = "SELECT * FROM genre g INNER JOIN followed_genres f ON f.genre_id = g.id AND f.post_id = ?1", nativeQuery = true)
    List<Genre> findWhatNeed(Long id);

    @Query(value = "SELECT * FROM genre ORDER BY genre_name", nativeQuery = true)
    List<Genre> QUERYfindGenres();


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO followed_genres (genre_id, post_id) VALUES (?1, ?2)", nativeQuery = true)
    void QUERYinsertGenrePost(long genre_id, long post_id);


    @Query(value = "SELECT * FROM genre ORDER BY genre_name", nativeQuery = true)
    List<Genre> QUERYfindAllOrderByName();
}
