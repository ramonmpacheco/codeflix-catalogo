package com.codeflix.catalogo.application.video.delete;

import com.codeflix.catalogo.application.UseCaseTest;
import com.codeflix.catalogo.application.genre.delete.DeleteGenreUseCase;
import com.codeflix.catalogo.domain.Fixture;
import com.codeflix.catalogo.domain.genre.GenreGateway;
import com.codeflix.catalogo.domain.video.VideoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DeleteVideoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DeleteVideoUseCase useCase;

    @Mock
    private VideoGateway videoGateway;

    @Test
    public void givenValidId_whenCallsDelete_shouldBeOk() {
        // given
        final var java21 = Fixture.Videos.java21();
        final var expectedId = java21.id();

        doNothing()
                .when(this.videoGateway).deleteById(anyString());

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(new DeleteVideoUseCase.Input(expectedId)));

        // then
        verify(this.videoGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenNullInput_whenCallsDelete_shouldBeOk() {
        // given
        final DeleteVideoUseCase.Input input = null;

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(input));

        // then
        verify(this.videoGateway, never()).deleteById(any());
    }

    @Test
    public void givenInvalidId_whenCallsDelete_shouldBeOk() {
        // given
        final String expectedId = null;

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(new DeleteVideoUseCase.Input(expectedId)));

        // then
        verify(this.videoGateway, never()).deleteById(any());
    }
}
