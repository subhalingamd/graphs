public class StackNodes{
	Object[] points;
	int t;

	public StackNodes(int size){
		points = new Object[size];
		t=0;
	}

	public void push(RedBlackNode<Float,Edge> p){
		points[t++] = (Object)p;
	}

	public RedBlackNode<Float,Edge> pop() {
		return (RedBlackNode<Float,Edge>)points[--t];
	}

	public boolean isEmpty() {
		return t==0;
	}

}