package com.intuit.comment.engine.dao;

import com.intuit.comment.engine.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

public interface PostRepository extends JpaRepository<PostEntity, String> {
}
