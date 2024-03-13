package com.codeflix.catalogo.application.genre.delete;

import com.codeflix.catalogo.application.UseCaseTest;
import com.codeflix.catalogo.domain.Fixture;
import com.codeflix.catalogo.domain.genre.GenreGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DeleteGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DeleteGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Test
    public void givenValidId_whenCallsDelete_shouldBeOk() {
        // given
        final var business = Fixture.Genres.business();
        final var expectedId = business.id();

        doNothing()
                .when(this.genreGateway).deleteById(anyString());

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(new DeleteGenreUseCase.Input(expectedId)));

        // then
        verify(this.genreGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenNullInput_whenCallsDelete_shouldBeOk() {
        // given
        final DeleteGenreUseCase.Input input = null;

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(input));

        // then
        verify(this.genreGateway, never()).deleteById(any());
    }

    @Test
    public void givenInvalidId_whenCallsDelete_shouldBeOk() {
        // given
        final String expectedId = null;

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(new DeleteGenreUseCase.Input(expectedId)));

        // then
        verify(this.genreGateway, never()).deleteById(any());
    }
}
