public class Point implements PointInterface,Comparable<Point> {
	float[] p=new float[3];
	ArrayListForEdges e=new ArrayListForEdges();
	ArrayListForTriangles t=new ArrayListForTriangles();
	boolean visited=false;

	public void addEdge(Edge edge){
		e.add(edge);
	}

	public void addTriangle(Triangle triangle){
		t.add(triangle);
	}

	public ArrayListForEdges getEdges(){
		return e;
	}

	public ArrayListForTriangles getTriangles(){
		return t;
	}

	public Point(float x, float y, float z){
		p[0]=x;
		p[1]=y;
		p[2]=z;
	}

	public boolean same(float[] p1){
		return p[0]==p1[0]&&p[1]==p1[1]&&p[2]==p1[2];
	}
	public boolean same(float p0,float p1,float p2){
		return p[0]==p0&&p[1]==p1&&p[2]==p2;
	}

	public float getX(){	return p[0];	}
	public float getY(){	return p[1];	}
	public float getZ(){	return p[2];	}

    public float [] getXYZcoordinate(){	return p;	}


    public void add(Point addp){
    	this.p[0]+=addp.p[0];
    	this.p[1]+=addp.p[1];
    	this.p[2]+=addp.p[2];
    }

    public void avg (int n){
    	this.p[0]/=n;
    	this.p[1]/=n;
    	this.p[2]/=n;
    }

    @Override
    public int compareTo (Point xyz){
    	if (this.p[0]<xyz.p[0]) return -1;
    	if (this.p[0]>xyz.p[0]) return 1;
    	if (this.p[1]<xyz.p[1]) return -1;
    	if (this.p[1]>xyz.p[1]) return 1;
    	if (this.p[2]<xyz.p[2]) return -1;
    	if (this.p[2]==xyz.p[2]) return 0;
    	return 1;
    }


}