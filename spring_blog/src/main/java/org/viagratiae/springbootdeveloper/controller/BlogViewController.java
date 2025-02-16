package org.viagratiae.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.viagratiae.springbootdeveloper.domain.Article;
import org.viagratiae.springbootdeveloper.dto.ArticleListViewResponse;
import org.viagratiae.springbootdeveloper.service.BlogService;
import org.springframework.ui.Model;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();

        model.addAttribute("articles", articles);
        return "articleList";  // articleList.html

    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id,  Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleListViewResponse(article));
        return "article";  // article.html
    }

    /* 블로그 생성/수정 */
    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            // 신규 생성
            model.addAttribute("article", new ArticleListViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleListViewResponse(article));
        }

        return "newArticle";
    }
}
