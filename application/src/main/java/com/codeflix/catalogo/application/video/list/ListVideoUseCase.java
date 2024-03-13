package com.codeflix.catalogo.application.video.list;

import com.codeflix.catalogo.application.UseCase;
import com.codeflix.catalogo.domain.pagination.Pagination;
import com.codeflix.catalogo.domain.video.Video;
import com.codeflix.catalogo.domain.video.VideoGateway;
import com.codeflix.catalogo.domain.video.VideoSearchQuery;

import java.util.Objects;
import java.util.Set;

public class ListVideoUseCase extends UseCase<ListVideoUseCase.Input, Pagination<ListVideoUseCase.Output>> {

    private final VideoGateway videoGateway;

    public ListVideoUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public Pagination<Output> execute(final ListVideoUseCase.Input input) {
        final var aQuery = new VideoSearchQuery(
                input.page(),
                input.perPage(),
                input.terms(),
                input.sort(),
                input.direction(),
                input.rating(),
                input.launchedAt(),
                input.categories(),
                input.castMembers(),
                input.genres()
        );

        return this.videoGateway.findAll(aQuery)
                .map(Output::from);
    }

    public record Input(
            int page,
            int perPage,
            String terms,
            String sort,
            String direction,
            String rating,
            Integer launchedAt,
            Set<String> categories,
            Set<String> castMembers,
            Set<String> genres
    ) {

    }

    public record Output(
            String id,
            String title,
            String description,
            boolean published,
            int yearLaunched,
            String rating,
            String video,
            String trailer,
            String banner,
            String thumbnail,
            String thumbnailHalf,
            Set<String> categories,
            Set<String> castMembers,
            Set<String> genres
    ) {

        public static Output from(Video video) {
            return new Output(
                    video.id(),
                    video.title(),
                    video.description(),
                    video.published(),
                    video.launchedAt().getValue(),
                    video.rating().getName(),
                    video.video(),
                    video.trailer(),
                    video.banner(),
                    video.thumbnail(),
                    video.thumbnailHalf(),
                    video.categories(),
                    video.castMembers(),
                    video.genres()
            );
        }
    }
}
