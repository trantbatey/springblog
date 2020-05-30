package com.codeup.springblog.models;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);

    // find all ads with the specified title
    @Query("from Post a where a.title = :term")
    List<Post> findAllByTitle(@Param("term") String term);
}
