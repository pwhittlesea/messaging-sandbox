package uk.me.thega.messaging.service;

import javax.jms.JMSException;
import javax.jms.MessageProducer;

import org.apache.activemq.command.ActiveMQObjectMessage;

import uk.me.thega.messaging.api.ApiMessage;

public class CoreProducer {

	private final MessageProducer producer;

	public CoreProducer(final MessageProducer producer) {
		this.producer = producer;
	}

	public void send(final ApiMessage outgoing) throws JMSException {
		final ActiveMQObjectMessage message = new ActiveMQObjectMessage();
		message.setObject(outgoing);
		producer.send(message);
	}
}
