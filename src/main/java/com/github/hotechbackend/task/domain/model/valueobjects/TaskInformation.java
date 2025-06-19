package com.github.hotechbackend.task.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record TaskInformation(
        String name,
        String information
) {
}
