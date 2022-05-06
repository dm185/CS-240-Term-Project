
public class AddressQueue {
	Address[] arr;
	int front;
	int rear;
	int capacity;
	int count;
	
	AddressQueue(int size){
		arr = new Address[size];
		capacity = size;
		front = 0;
		rear = -1;
		count = 0;
	}
	
	public Address dequeue() {
		if(isEmpty()) {
			System.out.println("The stack is empty.");
			System.exit(-1);
		}
		
		Address x = arr[front];
		System.out.println("Removing " + x.toString());
		front = (front +1) % capacity;
		count--;
		return x;
	}
	
	public void enqueue(Address x) {
		if (isFull()) {
			System.out.println("The stack is full.");
			System.exit(-1);
		}
		
		System.out.println("Inserting " + x.toString());
		rear = rear+1%capacity;
		arr[rear]=x;
		count++;
	}
	
	public void peek() {
		if (isEmpty()) {
			System.out.println("The stack is empty.");
			return;
		}
		System.out.println("Next in the queue is: " + arr[front].toString());
	}
	
	public int size() {
		return count;
	}
	
	public boolean isFull() {
		return(size() == capacity);
	}
	
	public boolean isEmpty() {
		return (size() == 0);
	}
	
}
