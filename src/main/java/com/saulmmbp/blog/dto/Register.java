package com.saulmmbp.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Register(@NotBlank String name, @NotBlank String username, @NotBlank @Email String email, 
		@NotBlank String password ) {

}
