package com.kurly.wms.message.service;

import com.kurly.wms.message.send.NotificationMessage;

public interface MessagingService {

	public void sendMessage(String channel, NotificationMessage message);

	public void sendSlackMessage(String displayName, String channel, String message);
}
