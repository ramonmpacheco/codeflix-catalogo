package com.codeflix.catalogo.application.video.delete;

import com.codeflix.catalogo.application.UnitUseCase;
import com.codeflix.catalogo.domain.genre.GenreGateway;
import com.codeflix.catalogo.domain.video.VideoGateway;

import java.util.Objects;

public class DeleteVideoUseCase extends UnitUseCase<DeleteVideoUseCase.Input> {

    private final VideoGateway videoGateway;

    public DeleteVideoUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public void execute(final Input input) {
        if (input == null || input.videoId() == null) {
            return;
        }

        this.videoGateway.deleteById(input.videoId());
    }

    public record Input(String videoId) {
    }
}
