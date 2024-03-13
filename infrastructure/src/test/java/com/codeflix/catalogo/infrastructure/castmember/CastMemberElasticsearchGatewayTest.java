package com.codeflix.catalogo.infrastructure.castmember;

import com.codeflix.catalogo.AbstractElasticsearchTest;
import com.codeflix.catalogo.domain.Fixture;
import com.codeflix.catalogo.domain.castmember.CastMemberSearchQuery;
import com.codeflix.catalogo.domain.category.CategorySearchQuery;
import com.codeflix.catalogo.infrastructure.castmember.persistence.CastMemberDocument;
import com.codeflix.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import com.codeflix.catalogo.infrastructure.category.persistence.CategoryDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.org.apache.commons.lang3.StringUtils;

class CastMemberElasticsearchGatewayTest extends AbstractElasticsearchTest {

    @Autowired
    private CastMemberElasticsearchGateway castMemberGateway;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @Test
    public void testInjection() {
        Assertions.assertNotNull(this.castMemberRepository);
        Assertions.assertNotNull(this.castMemberGateway);
    }

    @Test
    public void givenValidCastMember_whenCallsSave_shouldPersistIt() {
        // given
        final var gabriel = Fixture.CastMembers.gabriel();

        // when
        final var actualOutput = this.castMemberGateway.save(gabriel);

        // then
        Assertions.assertEquals(gabriel, actualOutput);

        final var actualMember = this.castMemberRepository.findById(gabriel.id()).get();
        Assertions.assertEquals(gabriel.id(), actualMember.id());
        Assertions.assertEquals(gabriel.name(), actualMember.name());
        Assertions.assertEquals(gabriel.type(), actualMember.type());
        Assertions.assertEquals(gabriel.createdAt(), actualMember.createdAt());
        Assertions.assertEquals(gabriel.updatedAt(), actualMember.updatedAt());
    }

    @Test
    public void givenValidId_whenCallsDeleteById_shouldDeleteIt() {
        // given
        final var gabriel = Fixture.CastMembers.gabriel();

        this.castMemberRepository.save(CastMemberDocument.from(gabriel));

        final var expectedId = gabriel.id();
        Assertions.assertTrue(this.castMemberRepository.existsById(expectedId));

        // when
        this.castMemberGateway.deleteById(expectedId);

        // then
        Assertions.assertFalse(this.castMemberRepository.existsById(expectedId));
    }

    @Test
    public void givenInvalidId_whenCallsDeleteById_shouldBeOk() {
        // given
        final var expectedId = "any";

        // when/then
        Assertions.assertDoesNotThrow(() -> this.castMemberGateway.deleteById(expectedId));
    }

    @Test
    public void givenValidId_whenCallsFindById_shouldRetrieveIt() {
        // given
        final var wesley = Fixture.CastMembers.wesley();

        this.castMemberRepository.save(CastMemberDocument.from(wesley));

        final var expectedId = wesley.id();
        Assertions.assertTrue(this.castMemberRepository.existsById(expectedId));

        // when
        final var actualOutput = this.castMemberGateway.findById(expectedId).get();

        // then
        Assertions.assertEquals(wesley.id(), actualOutput.id());
        Assertions.assertEquals(wesley.name(), actualOutput.name());
        Assertions.assertEquals(wesley.type(), actualOutput.type());
        Assertions.assertEquals(wesley.createdAt(), actualOutput.createdAt());
        Assertions.assertEquals(wesley.updatedAt(), actualOutput.updatedAt());
    }

    @Test
    public void givenInvalidId_whenCallsFindById_shouldReturnEmpty() {
        // given
        final var expectedId = "any";

        // when
        final var actualOutput = this.castMemberGateway.findById(expectedId);

        // then
        Assertions.assertTrue(actualOutput.isEmpty());
    }

    @Test
    public void givenEmptyCastMembers_whenCallsFindAll_shouldReturnEmptyList() {
        // given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var aQuery =
                new CastMemberSearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualOutput = this.castMemberGateway.findAll(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.meta().currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.meta().perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.meta().total());
        Assertions.assertEquals(expectedTotal, actualOutput.data().size());
    }

    @ParameterizedTest
    @CsvSource({
            "gab,0,10,1,1,Gabriel FullCycle",
            "leo,0,10,1,1,Leonan FullCycle"
    })
    public void givenValidTerm_whenCallsFindAll_shouldReturnElementsFiltered(
            final String expectedTerms,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedName
    ) {
        // given
        mockCastMembers();

        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var aQuery =
                new CastMemberSearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualOutput = this.castMemberGateway.findAll(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.meta().currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.meta().perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.meta().total());
        Assertions.assertEquals(expectedItemsCount, actualOutput.data().size());
        Assertions.assertEquals(expectedName, actualOutput.data().get(0).name());
    }

    @ParameterizedTest
    @CsvSource({
            "name,asc,0,10,3,3,Gabriel FullCycle",
            "name,desc,0,10,3,3,Wesley FullCycle",
            "created_at,asc,0,10,3,3,Gabriel FullCycle",
            "created_at,desc,0,10,3,3,Leonan FullCycle",
    })
    public void givenValidSortAndDirection_whenCallsFindAll_shouldReturnElementsSorted(
            final String expectedSort,
            final String expectedDirection,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedName
    ) {
        // given
        mockCastMembers();

        final var expectedTerms = "";

        final var aQuery =
                new CastMemberSearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualOutput = this.castMemberGateway.findAll(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.meta().currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.meta().perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.meta().total());
        Assertions.assertEquals(expectedItemsCount, actualOutput.data().size());
        Assertions.assertEquals(expectedName, actualOutput.data().get(0).name());
    }

    @ParameterizedTest
    @CsvSource({
            "0,1,1,3,Gabriel FullCycle",
            "1,1,1,3,Leonan FullCycle",
            "2,1,1,3,Wesley FullCycle",
            "3,1,0,3,",
    })
    public void givenValidPage_whenCallsFindAll_shouldReturnElementsPaged(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedName
    ) {
        // given
        mockCastMembers();

        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var aQuery =
                new CastMemberSearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualOutput = this.castMemberGateway.findAll(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.meta().currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.meta().perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.meta().total());
        Assertions.assertEquals(expectedItemsCount, actualOutput.data().size());

        if (StringUtils.isNotEmpty(expectedName)) {
            Assertions.assertEquals(expectedName, actualOutput.data().get(0).name());
        }
    }

    private void mockCastMembers() {
        this.castMemberRepository.save(CastMemberDocument.from(Fixture.CastMembers.gabriel()));
        this.castMemberRepository.save(CastMemberDocument.from(Fixture.CastMembers.wesley()));
        this.castMemberRepository.save(CastMemberDocument.from(Fixture.CastMembers.leonan()));
    }
}
