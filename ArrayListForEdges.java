class ArrayListForEdges{
	Edge[] elements;
	int MAX_SIZE,size;
	public ArrayListForEdges(){
		elements = new Edge[384];
		MAX_SIZE = 384;
		size = 0;
	}
	public ArrayListForEdges(int MAX_SIZE){
		elements = new Edge[MAX_SIZE];
		this.MAX_SIZE = MAX_SIZE;
		size = 0;
	}
	public void add(Edge t){
		if (MAX_SIZE == size){	//COPY TO 
			Edge[] array = new Edge[2*MAX_SIZE];
			for (int i = 0;i<MAX_SIZE; i++){
				array[i]=elements[i];
			}
			MAX_SIZE*=2;
			elements=array;
		}
		elements[size++]=t;
	}

	public Edge get(int index){
		return elements[index];
	}

	public int size(){
		return size;
	}

	public Edge[] toArray(){
		return elements;
	}

	public Edge[] trimmedArray(){
		Edge[] result = new Edge[size];
		for (int i = 0; i < size; i++)
			result[i] = elements[i];
		return result;
	}
}