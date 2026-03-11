package com.jarik.rest_api_template.dto.error;

public record ValidationError(
        String field,
        String message
) {
}
