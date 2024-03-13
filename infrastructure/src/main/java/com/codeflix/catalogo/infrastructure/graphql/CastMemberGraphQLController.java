package com.codeflix.catalogo.infrastructure.graphql;

import com.codeflix.catalogo.application.castmember.list.ListCastMemberUseCase;
import com.codeflix.catalogo.application.castmember.list.ListCastMembersOutput;
import com.codeflix.catalogo.application.castmember.save.SaveCastMemberUseCase;
import com.codeflix.catalogo.domain.castmember.CastMember;
import com.codeflix.catalogo.domain.castmember.CastMemberSearchQuery;
import com.codeflix.catalogo.infrastructure.castmember.models.CastMemberDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;

@Controller
public class CastMemberGraphQLController {

    private final ListCastMemberUseCase listCastMemberUseCase;
    private final SaveCastMemberUseCase saveCastMemberUseCase;

    public CastMemberGraphQLController(
            final ListCastMemberUseCase listCastMemberUseCase,
            final SaveCastMemberUseCase saveCastMemberUseCase
    ) {
        this.listCastMemberUseCase = Objects.requireNonNull(listCastMemberUseCase);
        this.saveCastMemberUseCase = Objects.requireNonNull(saveCastMemberUseCase);
    }

    @QueryMapping
    public List<ListCastMembersOutput> castMembers(
            @Argument final String search,
            @Argument final int page,
            @Argument final int perPage,
            @Argument final String sort,
            @Argument final String direction
    ) {
        final var query =
                new CastMemberSearchQuery(page, perPage, search, sort, direction);

        return this.listCastMemberUseCase.execute(query).data();
    }

    @MutationMapping
    public CastMember saveCastMember(@Argument CastMemberDTO input) {
        return this.saveCastMemberUseCase.execute(input.toCastMember());
    }
}
