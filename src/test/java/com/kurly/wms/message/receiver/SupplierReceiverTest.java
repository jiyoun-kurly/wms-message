package com.kurly.wms.message.receiver;

import com.kurly.wms.message.receive.SupplierMessageListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Message;
import javax.jms.TextMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class SupplierReceiverTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private SupplierMessageListener supplierMessageListener;

    @Test
    public void test() throws Exception {
        Message message = jmsTemplate.receive("Consumer.wms.VirtualTopic.supplierTest1");
        supplierMessageListener.receiveSupplierInfo((TextMessage) message);
    }



}
