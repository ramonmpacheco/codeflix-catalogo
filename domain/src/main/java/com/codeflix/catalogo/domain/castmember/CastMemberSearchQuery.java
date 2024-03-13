package com.codeflix.catalogo.domain.castmember;

public record CastMemberSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
