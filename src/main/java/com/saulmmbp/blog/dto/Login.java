package com.saulmmbp.blog.dto;

import jakarta.validation.constraints.NotBlank;

public record Login(@NotBlank String usernameOrEmail, @NotBlank String password) {
	
}
