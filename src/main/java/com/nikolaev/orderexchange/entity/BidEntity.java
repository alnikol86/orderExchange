package com.nikolaev.orderexchange.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BidEntity {
    private Integer bidId;
    private Integer projectId;
    private Integer personId;
    private BigDecimal bidAmount;
    private String proposal;
    private String status;
    private LocalDateTime createdAt;
}
