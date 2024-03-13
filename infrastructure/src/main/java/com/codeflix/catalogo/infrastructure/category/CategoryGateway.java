package com.codeflix.catalogo.infrastructure.category;

public interface CategoryGateway {
    Optional<Category> categoryOfId(String anId);
}
