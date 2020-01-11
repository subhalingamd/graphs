class ArrayListForTriangles{
	Triangle[] elements;
	int MAX_SIZE,size;
	public ArrayListForTriangles(){
		elements = new Triangle[256];
		MAX_SIZE = 256;
		size = 0;
	}
	public ArrayListForTriangles(int MAX_SIZE){
		elements = new Triangle[MAX_SIZE];
		this.MAX_SIZE = MAX_SIZE;
		size = 0;
	}
	public void add(Triangle t){
		if (MAX_SIZE == size){	//COPY TO 
			Triangle[] array = new Triangle[2*MAX_SIZE];
			for (int i = 0;i<MAX_SIZE; i++){
				array[i]=elements[i];
			}
			MAX_SIZE*=2;
			elements=array;
		}
		elements[size++]=t;
	}

	public Triangle get(int index){
		return elements[index];
	}

	public int size(){
		return size;
	}

	public Triangle[] toArray(){
		return elements;
	}

	public Triangle[] trimmedArray(){
		Triangle[] result = new Triangle[size];
		for (int i = 0; i < size; i++)
			result[i] = elements[i];
		return result;
	}
}