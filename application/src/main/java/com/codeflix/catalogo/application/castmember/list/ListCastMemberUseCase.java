package com.codeflix.catalogo.application.castmember.list;

import com.codeflix.catalogo.application.UseCase;
import com.codeflix.catalogo.domain.castmember.CastMemberGateway;
import com.codeflix.catalogo.domain.castmember.CastMemberSearchQuery;
import com.codeflix.catalogo.domain.pagination.Pagination;

import java.util.Objects;

public class ListCastMemberUseCase extends UseCase<CastMemberSearchQuery, Pagination<ListCastMembersOutput>> {

    private final CastMemberGateway castMemberGateway;

    public ListCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public Pagination<ListCastMembersOutput> execute(final CastMemberSearchQuery aQuery) {
        return this.castMemberGateway.findAll(aQuery)
                .map(ListCastMembersOutput::from);
    }
}
