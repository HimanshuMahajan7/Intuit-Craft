package com.intuit.comment.engine.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterDto {

    @Email
    @NotBlank(message = "Blank Email is not allowed")
    private String email;

    @NotBlank(message = "Blank Password is not allowed")
    @Size(max = 70, min = 3, message = "Name must be min 3 and max 70 character long")
    private String password;

    @NotBlank(message = "Blank Name is not allowed")
    @Size(max = 70, min = 3, message = "Name must be min 3 and max 70 character long")
    private String name;

    @JsonProperty("profile_pic")
    private String profilePic;

}
