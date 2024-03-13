package com.codeflix.catalogo.infrastructure.genre;

import com.codeflix.catalogo.domain.genre.Genre;
import com.codeflix.catalogo.domain.genre.GenreGateway;
import com.codeflix.catalogo.domain.genre.GenreSearchQuery;
import com.codeflix.catalogo.domain.pagination.Pagination;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

//@Component
public class GenreInMemoryGateway implements GenreGateway {

    private final Map<String, Genre> db;

    public GenreInMemoryGateway() {
        this.db = new ConcurrentHashMap<>();
    }

    @Override
    public Genre save(final Genre aGenre) {
        this.db.put(aGenre.id(), aGenre);
        return aGenre;
    }

    @Override
    public void deleteById(String genreId) {
        this.db.remove(genreId);
    }

    @Override
    public Optional<Genre> findById(String genreId) {
        return Optional.ofNullable(this.db.get(genreId));
    }

    @Override
    public Pagination<Genre> findAll(GenreSearchQuery aQuery) {
        return new Pagination<>(
                aQuery.page(),
                aQuery.perPage(),
                this.db.values().size(),
                this.db.values().stream().toList()
        );
    }
}
