package com.intuit.comment.engine.controllers;

import com.intuit.comment.engine.dto.request.EngagementRequestDto;
import com.intuit.comment.engine.dto.response.ApiResponse;
import com.intuit.comment.engine.dto.response.EngagementResponseDto;
import com.intuit.comment.engine.enums.EngagementType;
import com.intuit.comment.engine.exception.UnauthorizedException;
import com.intuit.comment.engine.services.EngagementService;
import com.intuit.comment.engine.utils.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 20 Mar, 2024 (Wed)
 */

@Slf4j
@RestController
@RequestMapping("v1/engagements")
@Tag(name = "Engagement Controller", description = "APIs related to Engagement (like, dislike)")
public class EngagementController {

    @Autowired
    Util util;

    @Autowired
    private EngagementService engagementService;

    @PostMapping("/{resourceId}")
    @Operation(summary = "Create Engagement", description = "Like/Dislike a Post/Comment")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ApiResponse<EngagementResponseDto>> createEngagement(
            @Valid @NotBlank @PathVariable String resourceId,
            @Valid @RequestBody EngagementRequestDto engagementType
    ) {
        String userId = util.getUserId().orElseThrow(() -> new UnauthorizedException("Author id not found in auth token"));
        EngagementResponseDto engagement = engagementService.createEngagement(resourceId, userId, engagementType);
        ApiResponse<EngagementResponseDto> response = new ApiResponse<>(HttpStatus.CREATED, "Engagement Recorded", engagement);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{resourceId}/{engagementType}")
    @Operation(summary = "Get Engagements", description = "Get Likes/Dislikes on a Post/Comment")
    public ResponseEntity<ApiResponse<List<EngagementResponseDto>>> getEngagements(
            @Valid @NotBlank @PathVariable String resourceId,
            @PathVariable EngagementType engagementType
    ) {
        List<EngagementResponseDto> engagements = engagementService.getEngagements(resourceId, engagementType);
        ApiResponse<List<EngagementResponseDto>> response = new ApiResponse<>(HttpStatus.OK, "Success", engagements);
        return ResponseEntity.ok(response);
    }
}
