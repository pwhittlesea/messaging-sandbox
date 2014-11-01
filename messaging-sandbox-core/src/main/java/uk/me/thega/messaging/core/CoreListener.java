package uk.me.thega.messaging.core;

import java.io.Closeable;
import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;

import org.apache.activemq.command.ActiveMQObjectMessage;

import uk.me.thega.messaging.api.ApiMessage;
import uk.me.thega.messaging.util.SessionIdentifierGenerator;

public class CoreListener implements Runnable, Closeable {

	final MessageConsumer consumer;

	private boolean running = true;

	private boolean halt = false;

	private final String uid;

	public CoreListener(final MessageConsumer consumer) {
		this.consumer = consumer;
		this.uid = SessionIdentifierGenerator.nextSessionId();
	}

	@Override
	public void close() throws IOException {
		try {
			consumer.close();
		} catch (final JMSException e) {
			throw new IOException(e);
		}
	}

	public synchronized boolean isRunning() {
		return running;
	}

	@Override
	public void run() {
		System.out.println("Starting core " + uid);
		while (!halt) {
			try {
				final Message message = consumer.receive(1000);
				if (message != null) {
					if (message instanceof ActiveMQObjectMessage) {
						final ActiveMQObjectMessage textMessage = (ActiveMQObjectMessage) message;
						final ApiMessage apiMessage = (ApiMessage) textMessage.getObject();
						System.out.println(uid + " Received: " + apiMessage.getContent());
					} else {
						System.out.println(uid + " Received: " + message);
					}
				}
			} catch (final JMSException e) {
				System.out.println("Consumer returned a fatal exception");
				halt = true;
			}
		}
		System.out.println("Stopping core " + uid);
		this.running = false;
	}

	public synchronized void stop() {
		this.halt = true;
	}
}
