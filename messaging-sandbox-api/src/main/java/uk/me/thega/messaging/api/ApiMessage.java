package uk.me.thega.messaging.api;

import java.io.Serializable;

public class ApiMessage implements Serializable {

	/** Serial ID. */
	private static final long serialVersionUID = 2619423100346657316L;

	private final String string;

	public ApiMessage(final String string) {
		this.string = string;
	}

	public String getContent() {
		return string;
	}
}
