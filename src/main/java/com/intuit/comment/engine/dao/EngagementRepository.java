package com.intuit.comment.engine.dao;

import com.intuit.comment.engine.entities.EngagementEntity;
import com.intuit.comment.engine.enums.EngagementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 20 Mar, 2024 (Wed)
 */

@Repository
public interface EngagementRepository extends JpaRepository<EngagementEntity, EngagementEntity.EngagementId> {

    List<EngagementEntity> findByEngagementIdResourceIdAndEngagement(String resourceId, EngagementType engagementType);

}
