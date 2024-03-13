package com.codeflix.catalogo.application.castmember.delete;

import com.codeflix.catalogo.application.UnitUseCase;
import com.codeflix.catalogo.domain.castmember.CastMemberGateway;

import java.util.Objects;

public class DeleteCastMemberUseCase extends UnitUseCase<String> {

    private final CastMemberGateway castMemberGateway;

    public DeleteCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public void execute(final String anIn) {
        if (anIn == null) {
            return;
        }

        this.castMemberGateway.deleteById(anIn);
    }
}
