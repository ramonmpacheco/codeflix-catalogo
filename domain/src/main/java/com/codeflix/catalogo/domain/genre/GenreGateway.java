package com.codeflix.catalogo.domain.genre;

import com.codeflix.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface GenreGateway {

    Genre save(Genre aGenre);

    void deleteById(String genreId);

    Optional<Genre> findById(String genreId);

    Pagination<Genre> findAll(GenreSearchQuery aQuery);

}
