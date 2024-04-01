package com.intuit.comment.engine.enums;

import lombok.Getter;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 27 Mar, 2024 (Wed)
 */

@Getter
public enum Status {
    DELETED((short) 0),
    ACTIVE((short) 1);

    private final short value;

    Status(short value) {
        this.value = value;
    }

//    public short getValue() {
//        return value;
//    }

}
