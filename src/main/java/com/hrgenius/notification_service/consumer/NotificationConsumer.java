package com.hrgenius.notification_service.consumer;

import com.hrgenius.notification_service.dto.LeaveApprovedEvent;
import com.hrgenius.notification_service.dto.PayrollProcessedEvent;
import com.hrgenius.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    // 1. Payroll Processed Listener
    @RabbitListener(queues = "${app.rabbitmq.queue.payroll-notify:notification.payroll.queue}")
    public void handlePayrollNotification(PayrollProcessedEvent event) {
        log.info("Processing payroll notification event for employee: {}", event.getEmployeeCode());

        String mockRecipientEmail = event.getEmployeeCode().toLowerCase() + "@hrgenius.com";
        String subject = String.format("Salary Credited - Payslip Ready for %02d/%d", event.getMonth(), event.getYear());
        String body = String.format("""
                Dear Employee (%s),
                
                Your monthly salary for %02d/%d has been processed successfully.
                
                Net Salary Credited: INR %.2f
                Status: %s
                
                You can download your detailed payslip from the HRGenius portal.
                
                Best Regards,
                HRGenius Payroll Team
                """, event.getEmployeeCode(), event.getMonth(), event.getYear(), event.getNetSalary(), event.getStatus());

        notificationService.sendEmail(mockRecipientEmail, subject, body);
    }

    // 2. Leave Approved Listener
    @RabbitListener(queues = "${app.rabbitmq.queue.leave-notify:notification.leave.queue}")
    public void handleLeaveNotification(LeaveApprovedEvent event) {
        log.info("Processing leave approval notification for employee: {}", event.getEmployeeCode());

        String mockRecipientEmail = event.getEmployeeCode().toLowerCase() + "@hrgenius.com";
        String subject = "Leave Application Approved - HRGenius";
        String body = String.format("""
                Dear Employee (%s),
                
                Your %s leave request from %s to %s has been APPROVED.
                
                Best Regards,
                HRGenius Leave Management
                """, event.getEmployeeCode(), event.getLeaveType(), event.getStartDate(), event.getEndDate());

        notificationService.sendEmail(mockRecipientEmail, subject, body);
    }
}