//TODO
////////////////////////////////
// ! Remove System.out.prinntln from everwhere
// * Empty array possible? => ret null
// * Return extra spzce
// * Check validity of triangle
// * Sort centroids

// * TYPEMESH=PROPER=>BOUNDARY null
//* SIZE OF BOUNDARY + SEMI_MESH

//* Check ID#123
//*!!!!!! Change connectivity to edges

public class Shape implements ShapeInterface
{	
	private final int HASH_SIZE=100;

	private int getHashCode(float hash){
		return ((int)Math.abs(hash * 1000))%HASH_SIZE;
	}

	int triID=0; 	//FIFO Triangle

	private RBTreeForEdges allEdges;
	private ArrayListForTriangles[] allTriangles;
	private ArrayListForPoints[] allPoints;

	public Shape(){
		allEdges = new RBTreeForEdges();
		allTriangles = new ArrayListForTriangles[HASH_SIZE];
		allPoints = new ArrayListForPoints[(int)HASH_SIZE];
		for (int i=0;i<HASH_SIZE;i++){
			allTriangles[i] = new ArrayListForTriangles(128);
			allPoints[i] = new ArrayListForPoints();
		}
	}


	boolean IMPROPER_MESH = false;
	int SEMI_MESH = 0;
	int tot_points = 0;
	int tot_tri = 0;

	boolean added_new=true;
	boolean added_new_c=true;

	public boolean ADD_TRIANGLE(float [] triangle_coord){
		//CHECK PENDING
		float area=0.5f*(float)Math.sqrt(Math.pow(((triangle_coord[3]*triangle_coord[1])-(triangle_coord[6]*triangle_coord[1])-(triangle_coord[0]*triangle_coord[4])+(triangle_coord[6]*triangle_coord[4])+(triangle_coord[0]*triangle_coord[7])-(triangle_coord[3]*triangle_coord[7])),2)+Math.pow(((triangle_coord[3]*triangle_coord[2])-(triangle_coord[6]*triangle_coord[2])-(triangle_coord[0]*triangle_coord[5])+(triangle_coord[6]*triangle_coord[5])+(triangle_coord[0]*triangle_coord[8])-(triangle_coord[3]*triangle_coord[8])),2)+Math.pow(((triangle_coord[4]*triangle_coord[2])-(triangle_coord[7]*triangle_coord[2])-(triangle_coord[1]*triangle_coord[5])+(triangle_coord[7]*triangle_coord[5])+(triangle_coord[1]*triangle_coord[8])-(triangle_coord[4]*triangle_coord[8])),2));
		//float area=0;
		if (area<0.001f) return false;

		int i=0,index;
		Point p1=null,p2=null,p3=null;

		ArrayListForPoints temp_points=new ArrayListForPoints();
		ArrayListForEdges temp_edges=new ArrayListForEdges();

		//Points
		temp_points=allPoints[getHashCode(triangle_coord[0]+triangle_coord[1]+triangle_coord[2])];
		for (i=0;i<temp_points.size();i++){
			float[]	temp_coord=temp_points.get(i).getXYZcoordinate();
			if (temp_coord[0]==triangle_coord[0]&&temp_coord[1]==triangle_coord[1]&&temp_coord[2]==triangle_coord[2]){
				p1=temp_points.get(i);
				break;
			}
		}
		if (p1==null){
			p1=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
			temp_points.add(p1);
			tot_points++;
			//System.out.println("NEW POINT1");

		}
		
		temp_points=allPoints[getHashCode(triangle_coord[3]+triangle_coord[4]+triangle_coord[5])];
		for ( i=0;i<temp_points.size();i++){
			float[]	temp_coord=temp_points.get(i).getXYZcoordinate();
			if (temp_coord[0]==triangle_coord[3]&&temp_coord[1]==triangle_coord[4]&&temp_coord[2]==triangle_coord[5]){
				p2=temp_points.get(i);
				break;
			}
		}
		if (p2==null){
			p2=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
			temp_points.add(p2);
			tot_points++;
			//System.out.println("NEW POINT2");

		}

		temp_points=allPoints[getHashCode(triangle_coord[6]+triangle_coord[7]+triangle_coord[8])];
		for ( i=0;i<temp_points.size();i++){
			float[]	temp_coord=temp_points.get(i).getXYZcoordinate();
			if (temp_coord[0]==triangle_coord[6]&&temp_coord[1]==triangle_coord[7]&&temp_coord[2]==triangle_coord[8]){
				p3=temp_points.get(i);
				break;
			}
		}
		if (p3==null){
			p3=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
			temp_points.add(p3);
			tot_points++;
			//System.out.println("NEW POINT3");
		}


		//System.out.println("1-\t");
		Edge e1=allEdges.searchAndInsert(p1,p2);

		//System.out.println("2-\t");
		Edge e2=allEdges.searchAndInsert(p2,p3);

		//System.out.println("3-\t");
		Edge e3=allEdges.searchAndInsert(p3,p1);
	

		//Triangle
		Triangle t=new Triangle(p1,p2,p3,triID++,e1,e2,e3);
		index=getHashCode(triangle_coord[0]+triangle_coord[1]+triangle_coord[2]+triangle_coord[3]+triangle_coord[4]+triangle_coord[5]+triangle_coord[6]+triangle_coord[7]+triangle_coord[8]);
		allTriangles[index].add(t);
		tot_tri++;

		p1.addTriangle(t);
		p2.addTriangle(t);
		p3.addTriangle(t);
		

		e1.addTriangle(t);
		e2.addTriangle(t);
		e3.addTriangle(t);


		//CHECK MESHH
		//if (!IMPROPER_MESH){
			int s1=e1.getTriangles().size(),s2=e2.getTriangles().size(),s3=e3.getTriangles().size();
			if (s1>2||s2>2||s3>2)	IMPROPER_MESH=true;
		
				if (s1==1) 	SEMI_MESH++;
				if (s2==1)	SEMI_MESH++;
				if (s3==1)	SEMI_MESH++;
				if (s1==2)	SEMI_MESH--;
				if (s2==2)	SEMI_MESH--;
				if (s3==2)	SEMI_MESH--;
			

		//}

		added_new=true;
		added_new_c=true;

		return true;
	}

		ArrayList<ArrayListForTriangles> components;

	private void findComponents() {

		if (!added_new) return;

		components=new ArrayList<ArrayListForTriangles>();

		for (int h = 0; h <HASH_SIZE; h++){
			for (int i = 0; i < allTriangles[h].size(); i++){
				if (!allTriangles[h].get(i).visited){
					ArrayListForTriangles arr_=new ArrayListForTriangles(tot_tri);
					//arr_.add(allPoints[h].get(i));
					//p.visited=true;


					StackTriangles s=new StackTriangles(tot_tri);
					s.push(allTriangles[h].get(i));
					allTriangles[h].get(i).visited=true;

					while (!s.isEmpty()){
						//p.visited = true;
						Triangle p=s.pop();
						arr_.add(p);
						for (int j=0;j<3;j++){
							ArrayListForTriangles t=p.e_s[j].getTriangles();
							for (int k=0;k<t.size; k++){
								Triangle p_ = t.get(k);
								if (!p_.visited){
									p_.visited = true;
									s.push(p_);
								}
							}
						}

					}
					//goDeep(allTriangles[h].get(i),arr_);
					components.add(arr_);
				}
			}
		}

		for (int h = 0; h <HASH_SIZE; h++){
			for (int i = 0; i < allTriangles[h].size(); i++){
				allTriangles[h].get(i).visited=false;
			}
		}

		added_new=false;
	} 

	/*
	public void goDeep(Triangle p,ArrayListForTriangles arr_){
		p.visited = true;
		arr_.add(p);
		for (int j=0;j<3;j++){
			ArrayListForTriangles t=p.t[j].getTriangles();
			for (int i=0;i<t.size; i++){
				Triangle p_ = t.get(i);
				if (!p_.visited){
					goDeep(p_,arr_);
				}
			}
		}
		
	}
	*/



	public boolean IS_CONNECTED(float [] triangle_coord_1, float [] triangle_coord_2){
	 	findComponents();

	 	int ctr_found = 0;

	 	for (int i = 0; i <components.size; i++){
	 		for (int j = 0; j <components.get(i).size; j++){
	 			ArrayListForTriangles arr=components.get(i);
	 			if (arr.get(j).same(triangle_coord_1))
	 				ctr_found++;
	 			if (arr.get(j).same(triangle_coord_2))
	 				ctr_found++;
	 			if (ctr_found==2)
	 				return true;
	 		}
	 		if (ctr_found==1)
	 			return false;
	 	}

	 	return false;

	 	/*Point p1=null,p2=null;

	 	ArrayListForTriangles arr = allTriangles[getHashCode(triangle_coord_1[0]+triangle_coord_1[1]+triangle_coord_1[2]+triangle_coord_1[3]+triangle_coord_1[4]+triangle_coord_1[5]+triangle_coord_1[6]+triangle_coord_1[7]+triangle_coord_1[8])];
		int size=arr.size();
		for (int i=0;i<size;i++){
			if (arr.get(i).same(triangle_coord_1)){
				p1=arr.get(i).t[0];
				break;
			}
		}

		if (p1==null) return false;

		arr = allTriangles[getHashCode(triangle_coord_2[0]+triangle_coord_2[1]+triangle_coord_2[2]+triangle_coord_2[3]+triangle_coord_2[4]+triangle_coord_2[5]+triangle_coord_2[6]+triangle_coord_2[7]+triangle_coord_2[8])];
		size=arr.size();
		for (int i=0;i<size;i++){
			if (arr.get(i).same(triangle_coord_2)){
				p2=arr.get(i).t[0];
				break;
			}
		}

		if (p2==null) return false;

		if (p1==p2) return true;

		QueuePoints q=new QueuePoints(tot_points);
		p1.visited=true;
		q.enqueue(p1);

		while (!q.isEmpty()) {
			Point p=q.dequeue();

			ArrayListForEdges e=p.getEdges();
			//System.out.println(e.size());
			
			for (int i=0;i<e.size();i++){
				Point p_=e.get(i).other(p);

				if (p_==p2) {q.resetToUnvisited(); return true; }

				if (!p_.visited){
					p_.visited=true;
					q.enqueue(p_);
				}
			}


		}
		q.resetToUnvisited();
		return false;
		*/

	 }

	 /*
	 public void doAallDFSPoint(Point p__){
	 	StackPoints s=new StackPoints(tot_points);
	 	s.push(p__);
	 	p__.visited=true;
	 	while (!s.isEmpty()){
	 		Point p__=s.pop();
	 		//p__.visited = true;
	 		ArrayListForEdges e=p__.getEdges();
	 		//System.out.println(e.size());
	 		
	 		for (int j=e.size()-1;j>=0;j--){
	 			Point p_=e.get(j).other(p__);
	 			if (!p_.visited){
	 				p_.visited=true;
	 				//p.visited=true;
	 				//goDeep(p_);
	 				s.push(p_);
	 			}
	 		}

	 	}

	 }
	 */
	

	public int COUNT_CONNECTED_COMPONENTS(){
		findComponents();

		return components.size();
		/*
		//CHECK
		if (!added_new) return components.size();

		int count = 0;
		for (int h = 0; h <HASH_SIZE; h++){
			for (int i = 0; i < allPoints[h].size(); i++){
				if (!allPoints[h].get(i).visited){
					count++;
					StackPoints s=new StackPoints(tot_points);
					s.push(allPoints[h].get(i));
					allPoints[h].get(i).visited=true;
					while (!s.isEmpty()){
						Point p__=s.pop();
						//p__.visited = true;
						ArrayListForEdges e=p__.getEdges();
						//System.out.println(e.size());
						
						for (int j=e.size()-1;j>=0;j--){
							Point p_=e.get(j).other(p__);
							if (!p_.visited){
								p_.visited=true;
								//goDeep(p_);
								s.push(p_);
							}
						}

					}
					//p.visited=true;
					//goDeep(allPoints[h].get(i));
				}
			}
		}

		for (int h = 0; h <HASH_SIZE; h++){
			for (int i = 0; i < allPoints[h].size(); i++){
				allPoints[h].get(i).visited=false;
			}
		}
		return count;
		*/
	} 

	/*
	//Can be improved by shifting points visited
	public void goDeep(Point p){
		p.visited = true;
		ArrayListForEdges e=p.getEdges();
		//System.out.println(e.size());
		
		for (int i=0;i<e.size();i++){
			Point p_=e.get(i).other(p);
			if (!p_.visited){
				//p.visited=true;
				goDeep(p_);
			}
		}
		
	}
	*/


//centrod:

	private Point[] mergeSortPoints(Point[] p){
		int l,m,r,n=p.length;

		for (int s=1; s <= n-1; s*=2) {
			for (l = 0; l < n-1;l+=2*s){ 
				m = Math.min(l+s-1,n-1); r=Math.min(l+2*s-1,n-1); 

				int i,j,ni=m-l+1,nj=r-m;
				
				Point L[] = new Point[ni]; 
				Point R[] = new Point[nj]; 
				
				for (i=0;i<ni;i++)
					L[i] =p[l+i];

				for (j=0;j<nj;j++)
					R[j]=p[m+1+j];
				
				i = 0; j = 0; 

				while (i<ni && j< nj) 
				{ 
				    if (L[i].compareTo(R[j])<=0) {
				        p[l+i+j] = L[i];
				        i++; 
				    }
				    else{
				        p[l+i+j] = R[j]; 
				    	j++;
				    }
				} 
				
				while (i < ni) 
				{ 
				    p[l+i+j] = L[i]; 
				    i++; 
				}

				while (j < nj) 
				{ 
				    p[l+i+j] = R[j]; 
				    j++; 
				} 
            }
        }
        return p; 	
	}
	

	private int ctr_centroid;
	ArrayListForPoints centroids_store=null;

	public PointInterface [] CENTROID (){
		if (!added_new_c && centroids_store!=null) {return mergeSortPoints(centroids_store.trimmedArray());}
		ArrayListForPoints res=new ArrayListForPoints();

		findComponents();

		for (int i=0;i<components.size; i++){
			//for (j=0;j<components.get(i).size;j++){
				//ArrayListForTriangles arr=components.get(i);
				//if (!arr.get(j).visited){
					res.add(computeCentroid(components.get(i).get(0),i));
					for (int x=0;x<components.get(i).size;x++){
						Triangle t=components.get(i).get(x);
						//t.visited=false;
						/*for (int v_=0;v_<3;v_++){
							t.t[v_].visited=false;
						}*/

						t.t[0].visited=false;
						t.t[1].visited=false;
						t.t[2].visited=false;
					}
				//}
			//}
		}
		/*
		for (int h = 0; h <HASH_SIZE; h++){
			for (int i = 0; i < allPoints[h].size(); i++){
				if (!allPoints[h].get(i).visited){
					
					//REMOVE
					//System.out.println("["+c.p[0]+", "+c.p[1]+", "+c.p[2]+"]");

					res.add(computeCentroid(allPoints[h].get(i)));
				}
			}
		}
		*/

		/*
		for (int i_=0;i_<components.size; i_++){
			for (int j_=0;j_<components.get(i_).size;j_++){
				components.get(i_).get(j_).visited=false;				
			}
		}
		*/

		/*
		for (int h = 0; h <HASH_SIZE; h++){
			for (int i = 0; i < allPoints[h].size(); i++){
				allPoints[h].get(i).visited=false;
			}
		}
		*/
		centroids_store=res;
		added_new_c=false;
		return mergeSortPoints(res.trimmedArray());

	}

	private Point computeCentroid(Triangle _p_,int pos){
		ctr_centroid=0;
		Point c=new Point(0.0f,0.0f,0.0f);
		//goDeep(allPoints[h].get(i),c);

		ArrayListForTriangles arr =components.get(pos);
		for (int i=0;i<arr.size;i++){
			Triangle t=arr.get(i);
			for (int x=0;x<3;x++){
				if (!t.t[x].visited){
					ctr_centroid++;
					c.add(t.t[x]);
					t.t[x].visited=true;
				}
			}
		}


		/*
		

		StackTriangles s=new StackTriangles(tot_tri);
		s.push(_p_);
		_p_.visited=true;
		while (!s.isEmpty()){
			Triangle p = s.pop();
			//p.visited = true;
			for (int v_=0;v_<3;v_++){
				if (!p.t[v_].visited){
					ctr_centroid++;
					c.add(p.t[v_]);
					p.t[v_].visited=true;
					ArrayListForTriangles e=p.e_s[v_].getTriangles();
					for (int _v_=0;_v_<e.size();_v_++){
						Triangle p_=e.get(_v_);
						if (!p_.visited){
							p_.visited=true;
							s.push(p_);
						}
					}
				}
			}
			//ctr_centroid++;
			//c.add(p);
			//ArrayListForTriangles e=p.;
			//System.out.println(e.size());
			/*
			for (int j=0;j<e.size();j++){
				Point p_=e.get(j).other(p);
				if (!p_.visited){
					p_.visited=true;
					s.push(p_);
					//goDeep(p_,curr);
				}
			}
			*/
		
		//}

		c.avg(ctr_centroid);
		return c;
	}

	/*
	public void goDeep(Point p,Point curr){
		p.visited = true;
		ctr_centroid++;
		curr.add(p);
		ArrayListForEdges e=p.getEdges();
		//System.out.println(e.size());
		
		for (int i=0;i<e.size();i++){
			Point p_=e.get(i).other(p);
			if (!p_.visited){

				goDeep(p_,curr);
			}
		}
		
	}
	*/

	public PointInterface CENTROID_OF_COMPONENT (float [] point_coordinates){
		//ArrayListForPoints stack_points = new ArrayListForPoints(tot_points+1);
		

		Point res=null;
		findComponents();
		int pos=-1;
		for (int i=0;i<components.size; i++){
			for (int j=0;j<components.get(i).size; j++){

				ArrayListForTriangles arr =components.get(i);
				//System.out.println(arr.get(j).contains(point_coordinates));
				if (arr.get(j).contains(point_coordinates)){
					if(!added_new_c&&centroids_store!=null) return centroids_store.get(i);

					pos=i;
					res=computeCentroid(arr.get(j),i);

					break;
				}
			}
		}
		/*
		ArrayListForPoints arr=allPoints[getHashCode(point_coordinates[0]+point_coordinates[1]+point_coordinates[2])];
		int size=arr.size();
		
		for (int i = 0; i <size; i++){
			if (arr.get(i).same(point_coordinates)){
				
				/*ctr_centroid=0;
				Point c=new Point(0.0f,0.0f,0.0f);
				goDeep(arr.get(i),c);
				c.avg(ctr_centroid);
				res=c;
				*/
				/*
				res=computeCentroid(arr.get(i));
				break;
			}
		}


		//THIS CAN BE IMPROVEDDDDDD!!
		for (int h = 0; h <HASH_SIZE; h++){
			for (int i = 0; i < allPoints[h].size(); i++){
				allPoints[h].get(i).visited=false;
			}
		}
		*/
		if (pos>-1){
			for (int x=0;x<components.get(pos).size;x++){
				Triangle t=components.get(pos).get(x);
				//t.visited=false;
				/*for (int v_=0;v_<3;v_++){
					t.t[v_].visited=false;
				}*/

				t.t[0].visited=false;
				t.t[1].visited=false;
				t.t[2].visited=false;
			}
		}
		/*
		//REMOVE
		if (res==null){
			System.out.println("No points");
		}
		else{
			System.out.println("["+res.p[0]+", "+res.p[1]+", "+res.p[2]+"]");

		}
		*/

		return res;

	}











	public 	PointInterface [] CLOSEST_COMPONENTS(){
		findComponents();

		float min_d=-1;
		Point[] res=null;
		if (components.size()==0) return res;

		for (int i = 0; i <components.size; i++){
			for (int j =0; j<components.get(i).size;j++){
				Triangle t1=components.get(i).get(j);
				for (int x=0;x<3;x++){
					Point p1=t1.t[x];
					for (int k =i+1; k<components.size;k++){
						for (int l=0;l<components.get(k).size;l++){
							Triangle t2=components.get(k).get(l);
							for (int y=0;y<3;y++){
								Point p2=t2.t[y];
								float[] c1=p1.p,c2=p2.p;
								float dist=(float)Math.sqrt(Math.pow(c1[0]-c2[0],2)+Math.pow(c1[1]-c2[1],2)+Math.pow(c1[2]-c2[2],2));
								if (dist<min_d||min_d==-1){
									min_d=dist;
									res=new Point[2];
									res[0]=p1;
									res[1]=p2;
								}
							}
						}

					}

				}
				
			}
		}
		//System.err.println(res[0].p[0]+" "+res[0].p[1]+" "+res[0].p[2]+"\t"+res[1].p[0]+" "+res[1].p[1]+" "+res[1].p[2]);
		return res;
	}




	public int MAXIMUM_DIAMETER(){
		findComponents();

		
		if (components.size==0) return 0;

		int max=0,pos=0;
		for (int i=0;i<components.size;i++){
			if (components.get(i).size>max){
				max=components.get(i).size;
				pos=i;
			}
		}
		//System.out.println(max);

		if (max==1) return 0;

		ArrayListForTriangles arr=components.get(pos);
		int max_dia=0;

		for (int i=0;i<arr.size;i++){
			int count=-1;
			QueueTriangles q1;
			QueueTriangles q2=new QueueTriangles(max);
			Triangle t=arr.get(i);
			t.visited=true;
			//System.out.println(t);
			q2.enqueue(t);
			do{
				q1=q2;
				q2=new QueueTriangles(max);
				while (!q1.isEmpty()){
					Triangle t_=q1.dequeue();
					
					for (int j=0;j<3;j++){
						ArrayListForTriangles arr_=t_.e_s[j].getTriangles();
						for (int k=0;k<arr_.size;k++){
							if (!arr_.get(k).visited){
								//System.out.println(arr_.get(k));
								arr_.get(k).visited=true;
								q2.enqueue(arr_.get(k));
							}
						}
					}
				}
				count++;

			}while(!q2.isEmpty());
				
			for (int x=0;x<arr.size;x++){
				arr.get(x).visited=false;
			}
			//System.out.println("\nNew---");
			if (count>max_dia) max_dia=count;
			//ID #123 >>>
			if (max==arr.size-1) break;
		}

		
		
		//System.out.println(max_dia);
		return max_dia;
	}	













	 










	























	public int TYPE_MESH(){
		//System.out.println("\t\t"+SEMI_MESH);
		if (IMPROPER_MESH) return 3;
		if (SEMI_MESH>0) return 2;
		return 1;
	}

	public EdgeInterface [] BOUNDARY_EDGES(){
		//A proper mesh has no boundaryEdges
		if (SEMI_MESH==0) return null;
		//System.out.println("HERE");
		return allEdges.boundaryEdges(SEMI_MESH);
	}


	public TriangleInterface [] EXTENDED_NEIGHBOR_TRIANGLE(float [] triangle_coord){
		ArrayListForTriangles arr = allTriangles[getHashCode(triangle_coord[0]+triangle_coord[1]+triangle_coord[2]+triangle_coord[3]+triangle_coord[4]+triangle_coord[5]+triangle_coord[6]+triangle_coord[7]+triangle_coord[8])];
		int size=arr.size();
		TriangleInterface [] res;

		for (int i=0;i<size;i++){
			if (arr.get(i).same(triangle_coord)){
				Triangle t=arr.get(i);
				Point[] es=t.t;
				ArrayListForTriangles t1=es[0].getTriangles(),t2=es[1].getTriangles(),t3=es[2].getTriangles();
				int setSize=t1.size()+t2.size()+t3.size()-t.e_s[0].getTriangles().size()-t.e_s[1].getTriangles().size()-t.e_s[2].getTriangles().size();
				if (setSize<=0)
					return null;
				res=new TriangleInterface [setSize]; 
				int x=0,y=0,z=0,j=0;
				while (x<t1.size()&&y<t2.size&&z<t3.size()) {
					if (t1.get(x).compareTo(t2.get(y))!=-1 &&t1.get(x).compareTo(t3.get(z))!=-1 ){
						if (t1.get(x)!=t&&(j==0||t1.get(x)!=res[j-1]))
							res[j++]=t1.get(x);
						x++;
						//System.out.println("1.1");
					}
					else if (t2.get(y).compareTo(t3.get(z))!=-1 &&t2.get(y).compareTo(t1.get(x))!=-1){
						if (t2.get(y)!=t&&(j==0||t2.get(y)!=res[j-1]))
							res[j++]=t2.get(y);
						y++;
						//System.out.println("1.2");
					}
					else{
						if (t3.get(z)!=t&&(j==0||t3.get(z)!=res[j-1]))
							res[j++]=t3.get(z);
						z++;
						//System.out.println("1.3");
					}
				}

				while (x<t1.size()&&y<t2.size) {
					if (t1.get(x).compareTo(t2.get(y))!=-1){
						if (t1.get(x)!=t&&(j==0||t1.get(x)!=res[j-1]))
							res[j++]=t1.get(x);
						x++;
						//System.out.println("2.1");
					}
					else{
						if (t2.get(y)!=t&&(j==0||t2.get(y)!=res[j-1]))
							res[j++]=t2.get(y);
						y++;
						//System.out.println("2.2");
					}
				}

				while (y<t2.size&&z<t3.size()) {
					if (t2.get(y).compareTo(t3.get(z))!=-1){
						if (t2.get(y)!=t&&(j==0||t2.get(y)!=res[j-1]))
							res[j++]=t2.get(y);
						y++;
						//System.out.println("3.1");
					}
					else{
						if (t3.get(z)!=t&&(j==0||t3.get(z)!=res[j-1]))
							res[j++]=t3.get(z);
						z++;
						//System.out.println("3.2");
					}
				}

				while (x<t1.size()&&z<t3.size()) {
					if (t1.get(x).compareTo(t3.get(z))!=-1 ){
						if (t1.get(x)!=t&&(j==0||t3.get(z)!=res[j-1]))
							res[j++]=t1.get(x);
						x++;
						//System.out.println("4.1");
					}
					else{
						if (t3.get(z)!=t&&(j==0||t1.get(x)!=res[j-1]))
							res[j++]=t3.get(z);
						z++;
						//System.out.println("4.2");
					}
				}

				while (x<t1.size()) {
						if (t1.get(x)!=t&&(j==0||t1.get(x)!=res[j-1]))
							res[j++]=t1.get(x);
						x++;
						//System.out.println("5.1");
				}

				while (y<t2.size()) {
						if (t2.get(y)!=t&&(j==0||t2.get(y)!=res[j-1]))
							res[j++]=t2.get(y);
						y++;
						//System.out.println("6.1");
				}

				while (z<t3.size()) {
						if (t3.get(z)!=t&&(j==0||t3.get(z)!=res[j-1]))
							res[j++]=t3.get(z);
						z++;
						//System.out.println("7.1");
				}


				/*
				//REMOVE
				for (int i_=0; i_<res.length; i_++){
					//res[i]=t.get(i);
					for (x=0;x<3;x++){
						for (y=0;y<3;y++){
							System.out.print(res[i_].triangle_coord()[x].getXYZcoordinate()[y]+ " ");
						}
						System.out.print("\t");
					}
					System.out.println();

				}
				*/
				return res;
			}
		}
		return null;
	}
	

	public TriangleInterface [] INCIDENT_TRIANGLES(float [] point_coordinates){
		ArrayListForPoints arr=allPoints[getHashCode(point_coordinates[0]+point_coordinates[1]+point_coordinates[2])];
		int size=arr.size();
		
		for (int i = 0; i <size; i++){
			if (arr.get(i).same(point_coordinates)){
				
				/*
				Point p=arr.get(i);
				for (int z=0;z<p.getTriangles().size();z++){
					for (int x=0;x<3;x++){
						for (int y=0;y<3;y++){
							System.out.print(p.getTriangles().get(z).triangle_coord()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
				*/
				
				return arr.get(i).getTriangles().trimmedArray();
				}
			}

		return null;
	}
	public TriangleInterface [] FACE_NEIGHBORS_OF_POINT(float [] point_coordinates){
		ArrayListForPoints arr=allPoints[getHashCode(point_coordinates[0]+point_coordinates[1]+point_coordinates[2])];
		int size=arr.size();
		
		for (int i = 0; i <size; i++){
			if (arr.get(i).same(point_coordinates)){
				
				/*
				Point p=arr.get(i);
				for (int z=0;z<p.getTriangles().size();z++){
					for (int x=0;x<3;x++){
						for (int y=0;y<3;y++){
							System.out.print(p.getTriangles().get(z).triangle_coord()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
				*/
				
				return arr.get(i).getTriangles().trimmedArray();
				}
			}

		return null;
	}		


	public TriangleInterface [] NEIGHBORS_OF_TRIANGLE(float [] triangle_coord){
		//Dont return same tri
		ArrayListForTriangles arr = allTriangles[getHashCode(triangle_coord[0]+triangle_coord[1]+triangle_coord[2]+triangle_coord[3]+triangle_coord[4]+triangle_coord[5]+triangle_coord[6]+triangle_coord[7]+triangle_coord[8])];
		int size=arr.size();
		TriangleInterface [] res;

		for (int i=0;i<size;i++){
			if (arr.get(i).same(triangle_coord)){
				Triangle t=arr.get(i);
				Edge[] es=t.getEdges();
				ArrayListForTriangles t1=es[0].getTriangles(),t2=es[1].getTriangles(),t3=es[2].getTriangles();
				if (t1.size()+t2.size()+t3.size()<=3)
					return null;
				res=new TriangleInterface [t1.size()+t2.size()+t3.size()-3]; 
				int x=0,y=0,z=0,j=0;
				while (x<t1.size()&&y<t2.size&&z<t3.size()) {
					if (t1.get(x).compareTo(t2.get(y))!=-1 &&t1.get(x).compareTo(t3.get(z))!=-1 ){
						if (t1.get(x)!=t)
							res[j++]=t1.get(x);
						x++;
						//System.out.println("1.1");
					}
					else if (t2.get(y).compareTo(t3.get(z))!=-1 &&t2.get(y).compareTo(t1.get(x))!=-1){
						if (t2.get(y)!=t)
							res[j++]=t2.get(y);
						y++;
						//System.out.println("1.2");
					}
					else{
						if (t3.get(z)!=t)
							res[j++]=t3.get(z);
						z++;
						//System.out.println("1.3");
					}
				}

				while (x<t1.size()&&y<t2.size) {
					if (t1.get(x).compareTo(t2.get(y))!=-1){
						if (t1.get(x)!=t)
							res[j++]=t1.get(x);
						x++;
						//System.out.println("2.1");
					}
					else{
						if (t2.get(y)!=t)
							res[j++]=t2.get(y);
						y++;
						//System.out.println("2.2");
					}
				}

				while (y<t2.size&&z<t3.size()) {
					if (t2.get(y).compareTo(t3.get(z))!=-1){
						if (t2.get(y)!=t)
							res[j++]=t2.get(y);
						y++;
						//System.out.println("3.1");
					}
					else{
						if (t3.get(z)!=t)
							res[j++]=t3.get(z);
						z++;
						//System.out.println("3.2");
					}
				}

				while (x<t1.size()&&z<t3.size()) {
					if (t1.get(x).compareTo(t3.get(z))!=-1 ){
						if (t1.get(x)!=t)
							res[j++]=t1.get(x);
						x++;
						//System.out.println("4.1");
					}
					else{
						if (t3.get(z)!=t)
							res[j++]=t3.get(z);
						z++;
						//System.out.println("4.2");
					}
				}

				while (x<t1.size()) {
						if (t1.get(x)!=t)
							res[j++]=t1.get(x);
						x++;
						//System.out.println("5.1");
				}

				while (y<t2.size()) {
						if (t2.get(y)!=t)
							res[j++]=t2.get(y);
						y++;
						//System.out.println("6.1");
				}

				while (z<t3.size()) {
						if (t3.get(z)!=t)
							res[j++]=t3.get(z);
						z++;
						//System.out.println("7.1");
				}

				/*
				//REMOVE
				for (int i_=0; i_<res.length; i_++){
					//res[i]=t.get(i);
					for (x=0;x<3;x++){
						for (y=0;y<3;y++){
							System.out.print(res[i_].triangle_coord()[x].getXYZcoordinate()[y]+ " ");
						}
						System.out.print("\t");
					}
					System.out.println();

				}
				*/
				return res;
			}
		}
		return null;
	}


	public PointInterface [] NEIGHBORS_OF_POINT(float [] point_coordinates){
		ArrayListForPoints arr=allPoints[getHashCode(point_coordinates[0]+point_coordinates[1]+point_coordinates[2])];
		int size=arr.size();
		
		for (int i = 0; i <size; i++){
			if (arr.get(i).same(point_coordinates)){
				
				Edge[] edges = arr.get(i).getEdges().toArray();
				int tot=arr.get(i).getEdges().size();
				Point[] res=new Point[tot];
				for (int a=0; a<tot; a++){
					res[a] = edges[a].other(point_coordinates);
					
					//System.out.println(res[a].p[0]+" "+res[a].p[1]+" "+res[a].p[2]);
				
				}

				return res;
				}
			}

		return null;

	}


	public TriangleInterface [] TRIANGLE_NEIGHBOR_OF_EDGE(float [] edge_coordinates){
		Edge e=allEdges.search(edge_coordinates);
		if (e==null) return null;
		//int size=e.getTriangles().size();
		//ArrayListForTriangles t = e.getTriangles(); 
		//TriangleInterface[] res=new Triangle[size];
		//res=e.getTriangles().toArray();
		
		/*
		//REMOVE
		for (int i=0; i<size; i++){
			//res[i]=t.get(i);
			for (int x=0;x<3;x++){
				for (int y=0;y<3;y++){
					System.out.print(res[i].triangle_coord()[x].getXYZcoordinate()[y]+ " ");
				}
									System.out.print("\t");
			}
			System.out.println();

		}
		
		return res;
		*/
		return e.getTriangles().trimmedArray();
	}


	public EdgeInterface [] EDGE_NEIGHBORS_OF_POINT(float [] point_coordinates){
		ArrayListForPoints arr=allPoints[getHashCode(point_coordinates[0]+point_coordinates[1]+point_coordinates[2])];
		int size=arr.size();
		
		for (int i = 0; i <size; i++){
			if (arr.get(i).same(point_coordinates)){
				
				/*
				Point p=arr.get(i);
				for (int z=0;z<p.getEdges().size();z++){
					for (int x=0;x<2;x++){
						for (int y=0;y<3;y++){
							System.out.print(p.getEdges().get(z).edgeEndPoints()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
				*/

				/*EdgeInterface[] res=arr.get(i).getEdges().toArray();
				return res;
				*/
				return arr.get(i).getEdges().trimmedArray();
				}
			}

		return null;
	}


	public EdgeInterface [] EDGE_NEIGHBOR_TRIANGLE(float [] triangle_coord){
		ArrayListForTriangles arr = allTriangles[getHashCode(triangle_coord[0]+triangle_coord[1]+triangle_coord[2]+triangle_coord[3]+triangle_coord[4]+triangle_coord[5]+triangle_coord[6]+triangle_coord[7]+triangle_coord[8])];
		int size=arr.size();

		for (int i=0;i<size;i++){
			if (arr.get(i).same(triangle_coord)){
				/*
				for (int z=0;z<3;z++){
					for (int x=0;x<2;x++){
						for (int y=0;y<3;y++){
							System.out.print(arr.get(i).getEdges()[z].edgeEndPoints()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
				*/

				return arr.get(i).getEdges();
			}
		}

		return null;
	}

	public PointInterface [] VERTEX_NEIGHBOR_TRIANGLE(float [] triangle_coord){
		ArrayListForTriangles arr = allTriangles[getHashCode(triangle_coord[0]+triangle_coord[1]+triangle_coord[2]+triangle_coord[3]+triangle_coord[4]+triangle_coord[5]+triangle_coord[6]+triangle_coord[7]+triangle_coord[8])];
		int size=arr.size();

		for (int i=0;i<size;i++){
			if (arr.get(i).same(triangle_coord)){
				/*
				for (int z=0;z<3;z++){
					for (int x=0;x<1;x++){
						for (int y=0;y<3;y++){
							System.out.print(arr.get(i).triangle_coord()[z].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
				*/
				

				
				return arr.get(i).triangle_coord();
			}
		}

		return null;
	}
}











