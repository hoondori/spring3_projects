package org.viagratiae.springbootdeveloper.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.viagratiae.springbootdeveloper.domain.Article;

@Repository
public interface BlogRepository extends JpaRepository<Article, Long>{

}
