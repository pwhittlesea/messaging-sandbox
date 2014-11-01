package uk.me.thega.messaging.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.me.thega.messaging.core.CoreListener;

public class CoreService {

	final List<CoreListener> cores = new ArrayList<CoreListener>();

	public void addCore(final CoreListener core) {
		if (!cores.contains(core)) {
			cores.add(core);
			final Thread runnable = new Thread(core);
			runnable.start();
		}
	}

	public void stopCores() throws IOException {
		for (final CoreListener thread : cores) {
			thread.stop();
			while (thread.isRunning()) {
				try {
					Thread.sleep(1000);
				} catch (final InterruptedException e) {
				}
			}
			thread.close();
		}
	}
}
