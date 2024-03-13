package com.codeflix.catalogo.application.castmember.list;

import com.codeflix.catalogo.domain.castmember.CastMember;
import com.codeflix.catalogo.domain.castmember.CastMemberType;

import java.time.Instant;

public record ListCastMembersOutput(
        String id,
        String name,
        CastMemberType type,
        Instant createdAt,
        Instant updatedAt
) {
    public static ListCastMembersOutput from(final CastMember castMember) {
        return new ListCastMembersOutput(
                castMember.id(),
                castMember.name(),
                castMember.type(),
                castMember.createdAt(),
                castMember.updatedAt()
        );
    }
}
