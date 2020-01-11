public class QueuePoints{
	Point[] points;
	int f,r;

	public QueuePoints(int size){
		points = new Point[size+19];
		f=0;
		r=0;
	}

	public void enqueue(Point p){
		points[r++] = p;
	}

	public Point dequeue() {
		return points[f++];
	}

	public boolean isEmpty() {
		return r==f;
	}

	//CHECK
	public void resetToUnvisited(){
		for (int i=0;i<r;i++) {
			points[i].visited=false;
		}
	}
}