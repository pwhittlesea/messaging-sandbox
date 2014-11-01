package uk.me.thega.messaging.app;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.me.thega.messaging.core.factory.CoreListenerFactory;
import uk.me.thega.messaging.service.CoreProducerFactory;
import uk.me.thega.messaging.service.CoreService;

@Configuration
public class ContextConfiguration {

	final static int numCores = 5;

	final static String brokerURL = "vm://localhost";

	final static String queueName = "TEST.FOO";

	@Bean
	public CoreListenerFactory coreListenerFactory(final Session session, final Destination queue, final CoreService coreService) throws JMSException {
		final CoreListenerFactory factory = new CoreListenerFactory(session, queue);
		for (int i = 0; i < numCores; i++) {
			coreService.addCore(factory.createNewCoreListener());
		}
		return factory;
	}

	@Bean
	public CoreProducerFactory coreProducerFactory(final Session session, final Destination queue) {
		return new CoreProducerFactory(session, queue);
	}

	@Bean(destroyMethod = "stopCores")
	public CoreService coreService() {
		return new CoreService();
	}

	@Bean
	public Destination messageQueue(final Session session) throws JMSException {
		return session.createQueue(queueName);
	}

	@Bean(destroyMethod = "close")
	public Connection queueConnection() throws JMSException {
		// Create a ConnectionFactory
		final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

		// Create a Connection
		final Connection connection = connectionFactory.createConnection();
		connection.start();

		return connection;
	}

	@Bean(destroyMethod = "close")
	public Session queueSession(final Connection connection) throws JMSException {
		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}
}
