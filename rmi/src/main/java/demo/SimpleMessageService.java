package demo;

class SimpleMessageService implements MessageService {

	@Override
	public Message greet(String n) {
		return new Message("Hello, " + n + "!");
	}
}
