public class QueueTriangles{
	Triangle[] points;
	int f,r;

	public QueueTriangles(int size){
		points = new Triangle[size+19];
		f=0;
		r=0;
	}

	public void enqueue(Triangle p){
		points[r++] = p;
	}

	public Triangle dequeue() {
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