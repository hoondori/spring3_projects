package org.viagratiae.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.viagratiae.springbootdeveloper.domain.Article;
import org.viagratiae.springbootdeveloper.dto.AddArticleRequest;
import org.viagratiae.springbootdeveloper.dto.UpdateArticleRequest;
import org.viagratiae.springbootdeveloper.repository.BlogRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 추가")
    @Test
    public void addArticle() throws Exception {

        // given
        final String url ="/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest request = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));
        // then
        result.andExpect(status().isCreated());

        // when
        List<Article> articles = blogRepository.findAll();
        // then
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: 블로그 목록 조회")
    @Test
    public void findAllArticles() throws Exception {

        // given
        final String url ="/api/articles";
        final String title = "title";
        final String content = "content";

        // 임의 데이터 1개 추가
        blogRepository.save(Article.builder().title(title).content(content).build());

        // when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("findArticle: 블로그 조회")
    @Test
    public void findArticle() throws Exception {

        // given
        final String url ="/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        // 임의 데이터 1개 추가
        Article article = blogRepository.save(Article.builder().title(title).content(content).build());

        // when
        final ResultActions result = mockMvc.perform(get(url, article.getId()));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("deleteArticle: 블로그 삭제")
    @Test
    public void deleteArticle() throws Exception {

        // given
        final String url ="/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        // 임의 데이터 1개 추가
        Article article = blogRepository.save(Article.builder().title(title).content(content).build());

        // when
        ResultActions result = mockMvc.perform(delete(url, article.getId()));

        // then
        result.andExpect(status().isOk());
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 블로그 수정")
    @Test
    public void updateArticle() throws Exception {

        // given
        final String url ="/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        // 임의 데이터 1개 추가
        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        // 수정
        final String newTitle = "new title";
        final String newContent = "new content";
        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }
}

