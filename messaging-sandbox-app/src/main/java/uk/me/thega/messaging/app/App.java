package uk.me.thega.messaging.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import uk.me.thega.messaging.api.ApiMessage;
import uk.me.thega.messaging.service.CoreProducer;
import uk.me.thega.messaging.service.CoreProducerFactory;

public class App {

	public static void main(final String[] args) throws Exception {
		@SuppressWarnings("resource")
		final AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(ContextConfiguration.class);
		ctx.registerShutdownHook();
		final CoreProducerFactory coreProducerFactory = ctx.getBean(CoreProducerFactory.class);
		final CoreProducer coreProducer = coreProducerFactory.createNewCoreProducer();
		final Runnable job = new Runnable() {

			@Override
			public void run() {
				try {
					int i = 0;
					while (i++ < 10) {
						// Create a messages
						final String text = "Hello world! Time: " + System.currentTimeMillis();
						final ApiMessage apiMessage = new ApiMessage(text);
						coreProducer.send(apiMessage);
						// Tell the producer to send the message
					}
					Thread.sleep(100000);
				} catch (final Exception e) {
					System.err.println("Caught: " + e);
				}
			}
		};
		final Thread thread = new Thread(job);
		thread.run();
		while (thread.isAlive()) {
			Thread.sleep(1000);
		}
		System.exit(0);
	}
}
