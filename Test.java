public class Test{
	static ArrayList<String> a = new ArrayList<String>(2);

	public static void main(String[] args){
	a.add("Hi");
	System.out.println(a.size());
	a.add("Hello");
	System.out.println(a.size());
	a.add("World");
	System.out.println(a.size());
	System.out.println(a.get(3));
	}
}