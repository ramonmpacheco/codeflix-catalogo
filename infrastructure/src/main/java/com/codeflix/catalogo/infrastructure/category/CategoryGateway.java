package com.codeflix.catalogo.infrastructure.category;

import com.codeflix.catalogo.domain.category.Category;

import java.util.Optional;

public interface CategoryGateway {
    Optional<Category> categoryOfId(String anId);
}
