package com.codeflix.catalogo.application.category.save;

import com.codeflix.catalogo.application.UseCase;
import com.codeflix.catalogo.domain.category.Category;
import com.codeflix.catalogo.domain.category.CategoryGateway;
import com.codeflix.catalogo.domain.exceptions.NotificationException;
import com.codeflix.catalogo.domain.validation.Error;
import com.codeflix.catalogo.domain.validation.handler.Notification;

import java.util.Objects;

public class SaveCategoryUseCase extends UseCase<Category, Category> {

    private final CategoryGateway categoryGateway;

    public SaveCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Category execute(final Category aCategory) {
        if (aCategory == null) {
            throw NotificationException.with(new Error("'aCategory' cannot be null"));
        }

        final var notification = Notification.create();
        aCategory.validate(notification);

        if (notification.hasError()) {
            throw NotificationException.with("Invalid category", notification);
        }

        return this.categoryGateway.save(aCategory);
    }
}
