package io.thorntail.mozzy.api;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@ApplicationScoped
@Path("/sendmessage")
public class SendMessage {

	public static final String MY_TOPIC = "java:/jms/topic/my-topic";

	@Inject
	private JMSContext context;

	@Resource(lookup = MY_TOPIC)
	private Topic topic;

	@GET
	@Produces("text/plain")
	public String get() {
		context.createProducer().send(topic, "Hello!");
		return "Howdy!";
	}
}
