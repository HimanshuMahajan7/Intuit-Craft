package com.intuit.comment.engine.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.intuit.comment.engine.enums.EngagementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 20 Mar, 2024 (Wed)
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "engagements")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EngagementEntity {

//    @Id
//    @JsonProperty("resource_id")
//    private String resourceId;
//
//    @Id
//    @JsonProperty("user_id")
//    private String userId;

    @EmbeddedId
    private EngagementId engagementId;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private EngagementType engagement;

    @JsonProperty("created_at")
    @Column(name = "created_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @JsonProperty("modified_at")
    private Date modifiedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class EngagementId implements Serializable {

        @JsonProperty("resource_id")
        private String resourceId;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private UserEntity user;
    }
}
