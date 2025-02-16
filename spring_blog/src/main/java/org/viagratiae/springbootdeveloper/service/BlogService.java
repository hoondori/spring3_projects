package org.viagratiae.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viagratiae.springbootdeveloper.domain.Article;
import org.viagratiae.springbootdeveloper.dto.AddArticleRequest;
import org.viagratiae.springbootdeveloper.dto.UpdateArticleRequest;
import org.viagratiae.springbootdeveloper.repository.BlogRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found" + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found" + id));
        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
