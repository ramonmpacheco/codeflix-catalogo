package com.codeflix.catalogo.application.genre.list;

import com.codeflix.catalogo.application.UseCaseTest;
import com.codeflix.catalogo.application.category.list.ListCategoryOutput;
import com.codeflix.catalogo.domain.Fixture;
import com.codeflix.catalogo.domain.category.CategorySearchQuery;
import com.codeflix.catalogo.domain.genre.GenreGateway;
import com.codeflix.catalogo.domain.genre.GenreSearchQuery;
import com.codeflix.catalogo.domain.pagination.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ListGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private ListGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Test
    public void givenValidQuery_whenCallsListGenres_shouldReturnIt() {
        // given
        final var genres = List.of(
                Fixture.Genres.business(),
                Fixture.Genres.tech()
        );

        final var expectedItems = genres.stream()
                .map(ListGenreUseCase.Output::from)
                .toList();

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "Algo";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 2;
        final var expectedCategories = Set.of("c1");

        final var aQuery =
                new ListGenreUseCase.Input(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection, expectedCategories);

        final var pagination =
                new Pagination<>(expectedPage, expectedPerPage, genres.size(), genres);

        when(this.genreGateway.findAll(any()))
                .thenReturn(pagination);

        // when
        final var actualOutput = this.useCase.execute(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.meta().currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.meta().perPage());
        Assertions.assertEquals(expectedItemsCount, actualOutput.meta().total());
        Assertions.assertTrue(
                expectedItems.size() == actualOutput.data().size() &&
                        expectedItems.containsAll(actualOutput.data())
        );
    }
}
