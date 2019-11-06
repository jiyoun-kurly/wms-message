package com.kurly.wms.message.service.impl;

import com.kurly.wms.message.send.NotificationMessage;
import com.kurly.wms.message.service.MessagingService;
import in.ashwanthkumar.slack.webhook.Slack;
import in.ashwanthkumar.slack.webhook.SlackMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service("messagingService")
@RequiredArgsConstructor
public class MessagingServiceImpl implements MessagingService {

    private final static Logger logger = LoggerFactory.getLogger ( MessagingServiceImpl.class );
    private final JmsTemplate messagingTemplate;

    @Value("${slack.info.slackUrl}")
    private String slackUrl;


    @Override
    public void sendMessage(String channel, NotificationMessage message) {

        logger.debug ( "===== [Message Send Start] =====");
        logger.debug ( "[channel]: " + channel );
        logger.debug ( "[message]: " + message.toString () );

        messagingTemplate.convertAndSend ( channel, message);

        logger.debug ( "===== [Message Send End] =====");
    }

    @Override
    public void sendSlackMessage(String displayName, String channel, String message) {

        logger.debug("===== [Send Slack Start] ===== ");

        try {

            new Slack( slackUrl )
                    .displayName ( displayName )
                    .sendToChannel ( channel )
                    .push ( new SlackMessage( message ) );

         }catch(IOException ioe) {
            logger.error ( "[Slack Send Error]" + ioe.getMessage ( ) );
            ioe.printStackTrace ();
        }

        logger.debug("===== [Send Slack End] ===== ");

    }
}
