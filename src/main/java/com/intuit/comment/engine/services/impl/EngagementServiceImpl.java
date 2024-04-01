package com.intuit.comment.engine.services.impl;

import com.intuit.comment.engine.dao.EngagementRepository;
import com.intuit.comment.engine.dto.request.EngagementRequestDto;
import com.intuit.comment.engine.dto.response.EngagementResponseDto;
import com.intuit.comment.engine.dto.response.UserResponseDto;
import com.intuit.comment.engine.entities.EngagementEntity;
import com.intuit.comment.engine.entities.UserEntity;
import com.intuit.comment.engine.enums.EngagementType;
import com.intuit.comment.engine.exception.NoContentException;
import com.intuit.comment.engine.services.EngagementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 20 Mar, 2024 (Wed)
 */

@Service
public class EngagementServiceImpl implements EngagementService {

    @Autowired
    EngagementRepository engagementRepository;


    @Autowired
//    @Qualifier("engMapper")
    ModelMapper mapper;

    @Override
    public EngagementResponseDto createEngagement(String resourceId, String userId, EngagementRequestDto engagementRequestDto) {
        UserEntity userEntity = UserEntity.builder().id(userId).build();
        EngagementEntity.EngagementId engagementId = EngagementEntity.EngagementId.builder().resourceId(resourceId).user(userEntity).build();
        EngagementEntity engagement = EngagementEntity.builder().engagementId(engagementId).createdAt(new Date()).engagement(engagementRequestDto.getEngagementType()).build();
        EngagementEntity recordedEngagement = engagementRepository.save(engagement);
        return mapper.map(recordedEngagement, EngagementResponseDto.class);
    }

    @Override
    public List<EngagementResponseDto> getEngagements(String resourceId, EngagementType engagementType) {
        List<EngagementEntity> engagements = engagementRepository.findByEngagementIdResourceIdAndEngagement(resourceId, engagementType);
        if (engagements.isEmpty()) {
            throw new NoContentException("No Engagements found");
        }
        return engagements.stream().map(engagementEntity -> {
            EngagementResponseDto dest = mapper.map(engagementEntity, EngagementResponseDto.class);
            dest.setUser(mapper.map(engagementEntity.getEngagementId().getUser(), UserResponseDto.class));
            return dest;
        }).toList();
    }
}
