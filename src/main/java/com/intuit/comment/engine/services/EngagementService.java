package com.intuit.comment.engine.services;

import com.intuit.comment.engine.dto.request.EngagementRequestDto;
import com.intuit.comment.engine.dto.response.EngagementResponseDto;
import com.intuit.comment.engine.enums.EngagementType;

import java.util.List;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 20 Mar, 2024 (Wed)
 */

public interface EngagementService {
    EngagementResponseDto createEngagement(String resourceId, String userId, EngagementRequestDto engagementType);

    List<EngagementResponseDto> getEngagements(String resourceId, EngagementType engagementType);
}
