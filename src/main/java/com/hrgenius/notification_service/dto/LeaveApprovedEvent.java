package com.hrgenius.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveApprovedEvent implements Serializable {
    private Long leaveRequestId;
    private String employeeCode;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}