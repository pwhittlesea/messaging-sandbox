package uk.me.thega.messaging.service;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class CoreProducerFactory {

	private final Destination destination;
	private final Session session;

	public CoreProducerFactory(final Session session, final Destination destination) {
		this.session = session;
		this.destination = destination;
	}

	public CoreProducer createNewCoreProducer() throws JMSException {
		final MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		final CoreProducer coreProducer = new CoreProducer(producer);
		return coreProducer;
	}

}
