package com.codeup.springblog.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    Ad findByTitle(String title);

    // The following method is equivalent to the built in `getOne` method, there's no need to create this example
    @Query("from Ad a where a.id = ?1")
    Ad getAdById(long id);

    // The following method shows you how to use named params in a HQL custom query:
    @Query("from Ad a where a.title like %:term%")
    List<Ad> searchByTitleLike(@Param("term") String term);
}
