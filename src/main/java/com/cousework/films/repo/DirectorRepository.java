package com.cousework.films.repo;

import com.cousework.films.entity.Actor;
import com.cousework.films.entity.Director;
import com.cousework.films.entity.Genre;
import com.cousework.films.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DirectorRepository extends CrudRepository<Director, Long> {

    Page<Director> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM director d INNER JOIN director_post dp ON dp.director_id = d.id AND dp.post_id = ?1", nativeQuery = true)
    List<Director> findDirectorsOnFilm(Long id);

    @Query(value = "SELECT * FROM director ORDER BY name", nativeQuery = true)
    List<Director> QUERYfindDirectors();

    @Query("SELECT d FROM Director d WHERE LOWER(d.name)  LIKE %?1%")
    Page<Director> findByNameLike(Pageable pageable, String keyword);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO director_post (director_id, post_id) VALUES (?1, ?2)", nativeQuery = true)
    void InsertDirectorPost(long director_id, long post_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM director_post WHERE director_id = ?1", nativeQuery = true)
    void DeleteDirectorFilmRel(long id);
}
