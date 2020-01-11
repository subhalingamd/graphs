public class Edge implements EdgeInterface,Comparable<Edge> {
	Point[] e=new Point[2];

	public Edge(Point p1,Point p2){
		e[0]=p1;
		e[1]=p2;
	}
	public PointInterface [] edgeEndPoints(){	return e;	}

	ArrayListForTriangles triangles = new ArrayListForTriangles();

	public void addTriangle(Triangle t){
		triangles.add(t);
	}

	public ArrayListForTriangles getTriangles(){
		return triangles;
	}

	public boolean same(float[] c){
		return (e[0].same(c[0],c[1],c[2])&&e[1].same(c[3],c[4],c[5]))||(e[1].same(c[0],c[1],c[2])&&e[0].same(c[3],c[4],c[5]));
	}

	public Point other(float[] c){
		if (e[0].same(c))
			return e[1];
		return e[0];
	}

	public Point other(Point c){
		if (e[0]==c)
			return e[1];
		return e[0];
	}




	@Override
	public int compareTo(Edge edge){
		float[] c1=this.e[0].getXYZcoordinate(),c2=this.e[1].getXYZcoordinate();
		float dt=(float)Math.sqrt(Math.pow(c1[0]-c2[0],2)+Math.pow(c1[1]-c2[1],2)+Math.pow(c1[2]-c2[2],2));
		c1=edge.e[0].getXYZcoordinate();
		c2=edge.e[1].getXYZcoordinate();
		float de=(float)Math.sqrt(Math.pow(c1[0]-c2[0],2)+Math.pow(c1[1]-c2[1],2)+Math.pow(c1[2]-c2[2],2));
		if (dt<de) return -1;
		if (dt==de) return 0;
		return 1;
	}

	public int compareTo(float de){
		float[] c1=this.e[0].getXYZcoordinate(),c2=this.e[1].getXYZcoordinate();
		float dt=(float)Math.sqrt(Math.pow(c1[0]-c2[0],2)+Math.pow(c1[1]-c2[1],2)+Math.pow(c1[2]-c2[2],2));
		if (dt<de) return -1;
		if (dt==de) return 0;
		return 1;
	}


}