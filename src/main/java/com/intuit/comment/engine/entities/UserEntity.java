package com.intuit.comment.engine.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Email
    private String email;

    private String password;

    @Size(max = 70, min = 3, message = "Name must be min 3 and max 70 character long")
    @NotBlank(message = "Blank Name is not allowed")
    private String name;

    private String profilePic;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Short status = 1;
}
