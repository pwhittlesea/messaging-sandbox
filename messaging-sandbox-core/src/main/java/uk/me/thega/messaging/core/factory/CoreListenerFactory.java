package uk.me.thega.messaging.core.factory;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import uk.me.thega.messaging.core.CoreListener;

public class CoreListenerFactory {

	private final Session session;

	private final Destination destination;

	public CoreListenerFactory(final Session session, final Destination destination) {
		this.session = session;
		this.destination = destination;
	}

	public CoreListener createNewCoreListener() throws JMSException {
		final MessageConsumer consumer = session.createConsumer(destination);
		final CoreListener listener = new CoreListener(consumer);
		return listener;
	}

}
