package com.kurly.wms.message.send;

import javax.jms.Destination;
import java.util.List;

public interface MessageQueueService {

	void sendReceivingReplyMessage(Destination destination, String sendMessageId, List<Long> message);
	void sendOrderReplyMessage(Destination destination, String sendMessageId, List<String> message, int degreeCount, String orderType);
}
