package de.guderlei.pubsub.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MessageTest {
	@Test(expected=IllegalArgumentException.class)
	public void illegal_message_null_as_producer(){
		new Message(null, "foo");
	}

	@Test(expected=IllegalArgumentException.class)
	public void illegal_message_empty_string_as_producer(){
		new Message("", "foo");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegal_message_null_as_message(){
		new Message("foo", null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegal_message_empty_string_as_message(){
		new Message("foo", "");
	}
	
	@Test
	public void to_string(){
		Message msg = new Message("me", "message");
		assertEquals("wrong string representation", "me: message", msg.toString());
	}
}
