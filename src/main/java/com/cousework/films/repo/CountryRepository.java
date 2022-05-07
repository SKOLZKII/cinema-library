package com.cousework.films.repo;

import com.cousework.films.entity.Country;
import com.cousework.films.entity.Director;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

    @Query(value = "SELECT * FROM country ORDER BY name", nativeQuery = true)
    List<Country> selectAllCountries();

    @Query(value = "SELECT * FROM country c INNER JOIN country_post cp ON cp.country_id = c.id AND cp.post_id = ?1", nativeQuery = true)
    List<Country> findCountriessOnFilm(Long id);

    @Query(value = "SELECT * FROM country ORDER BY name", nativeQuery = true)
    public List<Country> QUERYfindCountries();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO country_post (country_id, post_id) VALUES (?1, ?2)", nativeQuery = true)
    void InsertCountryPost(long country_id, long post_id);

    @Query(value = "SELECT * FROM country c INNER JOIN country_post cp ON cp.country_id = c.id AND cp.post_id = ?1", nativeQuery = true)
    public List<Country> findCountryOnFilm(Long id);
}
