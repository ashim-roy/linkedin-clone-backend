package com.ashim.linkedinClone.notification_service.service;


import com.ashim.linkedinClone.notification_service.entity.Notification;
import com.ashim.linkedinClone.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // receive notifictaion object san saev it
    public void addNotification(Notification notification) {
        log.info("Adding notification to DB, message: {}", notification.getMessages());
        notificationRepository.save(notification);

         // SendMailer to send email notification
        // in app ios or android - FCM lib - you need user device id- connect with firebase
    }
}
