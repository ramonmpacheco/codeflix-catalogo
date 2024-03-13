package com.codeflix.catalogo.domain.castmember;

import com.codeflix.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface CastMemberGateway {

    CastMember save(CastMember aMember);

    void deleteById(String anId);

    Optional<CastMember> findById(String anId);

    Pagination<CastMember> findAll(CastMemberSearchQuery aQuery);
}
