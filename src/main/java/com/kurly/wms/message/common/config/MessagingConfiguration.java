package com.kurly.wms.message.common.config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;

@EnableJms
@Configuration
public class MessagingConfiguration {

    @Value( "${activemq.broker-url}" )
    private String MESSAGE_BROKER_URL;

    @Value( "${activemq.user}" )
    private String MESSAGE_BROKER_USER_NAME;

    @Value( "${activemq.password}" )
    private String MESSAGE_BROKER_PASSWORD;


    @Bean
    public ConnectionFactory connectionFactory() {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory( );
        connectionFactory.setBrokerURL ( MESSAGE_BROKER_URL );
        connectionFactory.setUserName ( MESSAGE_BROKER_USER_NAME );
        connectionFactory.setPassword ( MESSAGE_BROKER_PASSWORD );

        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory ( );
        pooledConnectionFactory.setConnectionFactory ( connectionFactory );
        pooledConnectionFactory.setMaxConnections ( 2 );

        return pooledConnectionFactory;
    }

    /**
     * Used for RcvTransaction Message
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory( );
        factory.setConnectionFactory ( connectionFactory );
        factory.setPubSubDomain ( false );
        factory.setMessageConverter ( jacksonMessageConverter ( ) );

        return factory;
    }

    /**
     * Used for Sending Message
     */
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {

        JmsTemplate jmsTemplate = new JmsTemplate( );
        jmsTemplate.setMessageConverter (jacksonMessageConverter ());
        jmsTemplate.setDeliveryMode ( DeliveryMode.NON_PERSISTENT );
        jmsTemplate.setExplicitQosEnabled ( true );
        jmsTemplate.setPubSubDomain ( true );
        jmsTemplate.setConnectionFactory ( connectionFactory );

        return jmsTemplate;

    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter( );
        converter.setTargetType ( MessageType.TEXT );
        converter.setTypeIdPropertyName ( "_type" );
        return converter;
    }
}

