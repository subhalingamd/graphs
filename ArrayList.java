class ArrayList<T>{
	Object[] elements;
	int MAX_SIZE,size;
	public ArrayList(){
		elements = new Object[256];
		MAX_SIZE = 256;
		size = 0;
	}
	public ArrayList(int MAX_SIZE){
		elements = new Object[MAX_SIZE];
		this.MAX_SIZE = MAX_SIZE;
		size = 0;
	}
	public void add(T t){
		if (MAX_SIZE == size){	//COPY TO 
			Object[] array = new Object[2*MAX_SIZE];
			for (int i = 0;i<MAX_SIZE; i++){
				array[i]=elements[i];
			}
			MAX_SIZE*=2;
			elements=array;
		}
		elements[size++]=(Object)t;
	}

	@SuppressWarnings("unchecked") 
	public T get(int index){
		return (T)elements[index];
	}

	public int size(){
		return size;
	}

	public Object[] toArray(){
		return elements;
	}
}