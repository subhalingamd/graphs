class ArrayListForPoints{
	Point[] elements;
	int MAX_SIZE,size;
	public ArrayListForPoints(){
		elements = new Point[384];
		MAX_SIZE = 384;
		size = 0;
	}
	public ArrayListForPoints(int MAX_SIZE){
		elements = new Point[MAX_SIZE];
		this.MAX_SIZE = MAX_SIZE;
		size = 0;
	}
	public void add(Point t){
		if (MAX_SIZE == size){	//COPY TO 
			Point[] array = new Point[2*MAX_SIZE];
			for (int i = 0;i<MAX_SIZE; i++){
				array[i]=elements[i];
			}
			MAX_SIZE*=2;
			elements=array;
		}
		elements[size++]=t;
	}

	public Point get(int index){
		return elements[index];
	}

	public int size(){
		return size;
	}

	public Point[] toArray(){
		return elements;
	}

	public Point[] trimmedArray(){
		Point[] result = new Point[size];
		for (int i = 0; i < size; i++)
			result[i] = elements[i];
		return result;
	}
}