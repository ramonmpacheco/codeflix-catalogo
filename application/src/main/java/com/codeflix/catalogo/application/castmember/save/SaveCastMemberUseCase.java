package com.codeflix.catalogo.application.castmember.save;

import com.codeflix.catalogo.application.UseCase;
import com.codeflix.catalogo.domain.castmember.CastMember;
import com.codeflix.catalogo.domain.castmember.CastMemberGateway;
import com.codeflix.catalogo.domain.exceptions.NotificationException;
import com.codeflix.catalogo.domain.validation.Error;
import com.codeflix.catalogo.domain.validation.handler.Notification;

import java.util.Objects;

public class SaveCastMemberUseCase extends UseCase<CastMember, CastMember> {

    private final CastMemberGateway castMemberGateway;

    public SaveCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public CastMember execute(final CastMember aMember) {
        if (aMember == null) {
            throw NotificationException.with(new Error("'aMember' cannot be null"));
        }

        final var notification = Notification.create();
        aMember.validate(notification);

        if (notification.hasError()) {
            throw NotificationException.with("Invalid cast member", notification);
        }

        return this.castMemberGateway.save(aMember);
    }
}
