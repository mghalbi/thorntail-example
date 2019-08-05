package io.thorntail.mozzy.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import io.thorntail.mozzy.api.SendMessage;

@MessageDriven(name = "MyTopicMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = SendMessage.MY_TOPIC),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
})
public class MyTopicMDB implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("received: " + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
