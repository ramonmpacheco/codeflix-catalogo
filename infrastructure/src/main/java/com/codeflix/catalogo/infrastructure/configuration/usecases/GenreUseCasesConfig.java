package com.codeflix.catalogo.infrastructure.configuration.usecases;

import com.codeflix.catalogo.application.genre.delete.DeleteGenreUseCase;
import com.codeflix.catalogo.application.genre.list.ListGenreUseCase;
import com.codeflix.catalogo.application.genre.save.SaveGenreUseCase;
import com.codeflix.catalogo.domain.genre.GenreGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration(proxyBeanMethods = false)
public class GenreUseCasesConfig {

    private final GenreGateway genreGateway;

    public GenreUseCasesConfig(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Bean
    SaveGenreUseCase saveGenreUseCase() {
        return new SaveGenreUseCase(genreGateway);
    }

    @Bean
    DeleteGenreUseCase deleteGenreUseCase() {
        return new DeleteGenreUseCase(genreGateway);
    }

    @Bean
    ListGenreUseCase listGenreUseCase() {
        return new ListGenreUseCase(genreGateway);
    }
}
