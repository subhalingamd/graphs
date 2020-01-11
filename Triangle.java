
public class Triangle implements TriangleInterface,Comparable<Triangle> {
	Point[] t=new Point[3];
	Edge[] e_s=new Edge[3];
	int id;
	Edge e1,e2,e3;
	boolean visited=false;


	public Triangle(Point p1,Point p2,Point p3,int id,Edge e1,Edge e2,Edge e3){
		t[0]=p1;
		t[1]=p2;
		t[2]=p3;
		this.id=id;
		e_s[0]=e1;
		e_s[1]=e2;
		e_s[2]=e3;
	} 

	public Edge[] getEdges(){
		return e_s;
	}

	public boolean same(float[] c){
		//036 063 306 360 603 630
		return (t[0].p[0]==c[0] && t[0].p[1]==c[1] && t[0].p[2]==c[2] && t[1].p[0]==c[3] && t[1].p[1]==c[4] && t[1].p[2]==c[5] && t[2].p[0]==c[6] && t[2].p[1]==c[7] && t[2].p[2]==c[8]) || (t[0].p[0]==c[0] && t[0].p[1]==c[1] && t[0].p[2]==c[2] && t[1].p[0]==c[6] && t[1].p[1]==c[7] && t[1].p[2]==c[8] && t[2].p[0]==c[3] && t[2].p[1]==c[4] && t[2].p[2]==c[5]) || (t[0].p[0]==c[3] && t[0].p[1]==c[4] && t[0].p[2]==c[5] && t[1].p[0]==c[0] && t[1].p[1]==c[1] && t[1].p[2]==c[2] && t[2].p[0]==c[6] && t[2].p[1]==c[7] && t[2].p[2]==c[8]) || (t[0].p[0]==c[3] && t[0].p[1]==c[4] && t[0].p[2]==c[5] && t[1].p[0]==c[6] && t[1].p[1]==c[7] && t[1].p[2]==c[8] && t[2].p[0]==c[0] && t[2].p[1]==c[1] && t[2].p[2]==c[2]) || (t[0].p[0]==c[6] && t[0].p[1]==c[7] && t[0].p[2]==c[8] && t[1].p[0]==c[0] && t[1].p[1]==c[1] && t[1].p[2]==c[2] && t[2].p[0]==c[3] && t[2].p[1]==c[4] && t[2].p[2]==c[5]) || (t[0].p[0]==c[6] && t[0].p[1]==c[7] && t[0].p[2]==c[8] && t[1].p[0]==c[3] && t[1].p[1]==c[4] && t[1].p[2]==c[5] && t[2].p[0]==c[0] && t[2].p[1]==c[1] && t[2].p[2]==c[2]);
	}

	public boolean contains(float[] c){
		return ((t[0].p[0]==c[0]&&t[0].p[1]==c[1]&&t[0].p[2]==c[2])||(t[1].p[0]==c[0]&&t[1].p[1]==c[1]&&t[1].p[2]==c[2])||(t[2].p[0]==c[0]&&t[2].p[1]==c[1]&&t[2].p[2]==c[2]));
	}

	public PointInterface [] triangle_coord(){		return t;	}

	@Override 
	public int compareTo(Triangle t){
		//System.out.println("\t\t I'll return "+this.id+t.id);
		if (this.id>t.id) return -1;
		if (this.id==t.id) return 0;
		return 1;
	}


}