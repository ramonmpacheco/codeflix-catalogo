package com.codeflix.catalogo.infrastructure.genre;

import com.codeflix.catalogo.infrastructure.genre.models.GenreDTO;

import java.util.Optional;

public interface GenreGateway {

    Optional<GenreDTO> genreOfId(String genreId);

}
