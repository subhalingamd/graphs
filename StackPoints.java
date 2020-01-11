public class StackPoints{
	Point[] points;
	int t;

	public StackPoints(int size){
		points = new Point[size];
		t=0;
	}

	public void push(Point p){
		points[t++] = p;
	}

	public Point pop() {
		return points[--t];
	}

	public boolean isEmpty() {
		return t==0;
	}

}