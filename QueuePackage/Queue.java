package QueuePackage;
import java.util.*;
public class Queue {

	private LinkedList list;

	public Queue() {
		list = new LinkedList();
	}

	public boolean empty() {
		if (list.size() == 0) {
			return true;
		}	
	return false;
	}

	public void enqueue(Object o) {
		if (o != null) {
			list.addFirst(o);
		}	
	}

	public Object dequeue() {
		if (!this.empty()) {
			return list.removeLast();
		}	
		throw new EmptyQueueException();
	}
}
