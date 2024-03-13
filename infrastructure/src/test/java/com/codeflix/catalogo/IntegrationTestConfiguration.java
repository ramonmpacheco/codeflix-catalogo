package com.codeflix.catalogo;

import com.codeflix.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import com.codeflix.catalogo.infrastructure.category.persistence.CategoryRepository;
import com.codeflix.catalogo.infrastructure.genre.persistence.GenreRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

// TODO: Create a test to remember that is required to create this bean
public class IntegrationTestConfiguration {

    @Bean
    public CategoryRepository categoryRepository() {
        return Mockito.mock(CategoryRepository.class);
    }

    @Bean
    public CastMemberRepository castMemberRepository() {
        return Mockito.mock(CastMemberRepository.class);
    }

    @Bean
    public GenreRepository genreRepository() {
        return Mockito.mock(GenreRepository.class);
    }
}
