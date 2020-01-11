public class StackTriangles{
	Triangle[] points;
	int t;

	public StackTriangles(int size){
		points = new Triangle[size];
		t=0;
	}

	public void push(Triangle p){
		points[t++] = p;
	}

	public Triangle pop() {
		return points[--t];
	}

	public boolean isEmpty() {
		return t==0;
	}

}