package com.nikolaev.orderexchange.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MessageEntity {
    private Integer messageId;
    private Integer senderId;
    private Integer receiverId;
    private Integer projectId; // Может быть null
    private String content;
    private LocalDateTime createdAt;
}
