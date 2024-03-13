package com.codeflix.catalogo.domain;

import com.codeflix.catalogo.domain.castmember.CastMember;
import com.codeflix.catalogo.domain.castmember.CastMemberType;
import com.codeflix.catalogo.domain.category.Category;
import com.codeflix.catalogo.domain.genre.Genre;
import com.codeflix.catalogo.domain.utils.IdUtils;
import com.codeflix.catalogo.domain.utils.InstantUtils;
import com.codeflix.catalogo.domain.video.Rating;
import com.codeflix.catalogo.domain.video.Video;
import net.datafaker.Faker;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.codeflix.catalogo.domain.utils.InstantUtils.now;

public final class Fixture {

    private static final Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }

    public static Integer year() {
        return FAKER.random().nextInt(2020, 2030);
    }

    public static Double duration() {
        return FAKER.options().option(120.0, 15.5, 35.5, 10.0, 2.0);
    }

    public static boolean bool() {
        return FAKER.bool().bool();
    }

    public static String title() {
        return FAKER.options().option(
                "System Design no Mercado Livre na prática",
                "Não cometa esses erros ao trabalhar com Microsserviços",
                "Testes de Mutação. Você não testa seu software corretamente"
        );
    }

    public static String checksum() {
        return "03fe62de";
    }

    public static final class Categories {

        public static Category aulas() {
            return Category.with(
                    UUID.randomUUID().toString().replace("-", ""),
                    "Aulas",
                    "Conteudo gravado",
                    true,
                    now(),
                    now(),
                    null
            );
        }

        public static Category talks() {
            return Category.with(
                    UUID.randomUUID().toString().replace("-", ""),
                    "Talks",
                    "Conteudo ao vivo",
                    false,
                    now(),
                    now(),
                    now()
            );
        }

        public static Category lives() {
            return Category.with(
                    UUID.randomUUID().toString().replace("-", ""),
                    "Lives",
                    "Conteudo ao vivo",
                    true,
                    now(),
                    now(),
                    null
            );
        }
    }

    public static final class CastMembers {

        public static CastMemberType type() {
            return FAKER.options().option(CastMemberType.values());
        }

        public static CastMember wesley() {
            return CastMember.with(UUID.randomUUID().toString(), "Wesley FullCycle", CastMemberType.ACTOR, now(), now());
        }

        public static CastMember gabriel() {
            return CastMember.with(UUID.randomUUID().toString(), "Gabriel FullCycle", CastMemberType.ACTOR, now(), now());
        }

        public static CastMember leonan() {
            return CastMember.with(UUID.randomUUID().toString(), "Leonan FullCycle", CastMemberType.DIRECTOR, now(), now());
        }
    }

    public static final class Genres {

        public static Genre tech() {
            return Genre.with(IdUtils.uniqueId(), "Technology", true, Set.of("c456"), now(), now(), null);
        }

        public static Genre business() {
            return Genre.with(IdUtils.uniqueId(), "Business", false, new HashSet<>(), now(), now(), now());
        }

        public static Genre marketing() {
            return Genre.with(IdUtils.uniqueId(), "Marketing", true, Set.of("c123"), now(), now(), null);
        }
    }

    public static final class Videos {

        public static Rating rating() {
            return FAKER.options().option(Rating.values());
        }

        public static Video systemDesign() {
            return Video.with(
                    IdUtils.uniqueId(),
                    "System Design no Mercado Livre na prática",
                    "O vídeo mais assistido",
                    Fixture.year(),
                    Fixture.duration(),
                    rating().getName(),
                    Fixture.bool(),
                    Fixture.bool(),
                    InstantUtils.now().toString(),
                    InstantUtils.now().toString(),
                    "http://video",
                    "http://trailer",
                    "http://banner",
                    "http://thumb",
                    "http://thumbhalf",
                    Set.of(IdUtils.uniqueId()),
                    Set.of(IdUtils.uniqueId()),
                    Set.of(IdUtils.uniqueId())
            );
        }

        public static Video java21() {
            return Video.with(
                    IdUtils.uniqueId(),
                    "Java 21",
                    "O vídeo mais assistido",
                    Fixture.year(),
                    Fixture.duration(),
                    rating().getName(),
                    Fixture.bool(),
                    Fixture.bool(),
                    InstantUtils.now().toString(),
                    InstantUtils.now().toString(),
                    "http://video",
                    "http://trailer",
                    "http://banner",
                    "http://thumb",
                    "http://thumbhalf",
                    Set.of(IdUtils.uniqueId()),
                    Set.of(IdUtils.uniqueId()),
                    Set.of(IdUtils.uniqueId())
            );
        }
    }
}
