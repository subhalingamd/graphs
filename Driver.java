import java.util.ArrayList;

import java.util.Arrays; 

import java.io.*;
class Driver {


  public static void main(String[] args) {
				long TOT_TIME=0;

	try{

       	    BufferedReader br = null;
            br = new BufferedReader(new FileReader(args[0]));
	    ShapeInterface shape_intr = new Shape();
            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
		//System.out.println("cmd is "+ Arrays.toString(cmd));	

                if (cmd.length == 0) {
                    System.err.println("Error parsing:1 ");
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

		ArrayList<Float> inp = new ArrayList<>();
		 int firstskip=0;
		for (String val:cmd) {

			if(0==firstskip){
			firstskip++;
			continue;
			}

			inp.add(Float.parseFloat(val.trim()));
			//System.out.print(val + " ");
		}
		
		//System.out.println("arguments " +Arrays.toString(input.toArray()));


		float[] input = new float[inp.size()]; 
		int k = 0;

		for (Float f : inp) {
		    input[k++] = f; 
		}
                switch (cmd[0]) {
			

		

                    case "ADD_TRIANGLE":
			//System.out.println("Add TriangleInterface ");	
			
			boolean vaild= shape_intr.ADD_TRIANGLE(input);
                       if (!vaild) System.out.println("Invalid Triangle:\t"+input[0]+" "+input[1]+" "+input[2]+"\t"+input[3]+" "+input[4]+ " "+input[5]+"\t"+input[6]+" "+input[7]+" "+input[8]); 
                        break;

                    case "TYPE_MESH":
			//System.out.println("Checking mesh type");
            			qstart_time = System.nanoTime();
            			int mesh_type = shape_intr.TYPE_MESH();            
                        qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);

			System.out.println("Mesh type: 	" + mesh_type);
                        break;
                    case "COUNT_CONNECTED_COMPONENTS":
			//System.out.println("Finding number of connected components");
			            			qstart_time = System.nanoTime();

			int count_connected = shape_intr.COUNT_CONNECTED_COMPONENTS();
			qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
			System.out.println("Number of connected components:	"+ count_connected);
                        break;
                    case "BOUNDARY_EDGES":		
			System.out.println("Getting boundary edges");	
            			qstart_time = System.nanoTime();

			 EdgeInterface [] boundary_edges= shape_intr.BOUNDARY_EDGES();
			 qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                        if (boundary_edges!=null)
			 for (int z=0;z<boundary_edges.length;z++){
					for (int x=0;x<2;x++){
						for (int y=0;y<3;y++){
							System.out.print(boundary_edges[z].edgeEndPoints()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}

                        break;
                    case "IS_CONNECTED":
			System.out.println("Checking IS_CONNECTED");	
			float [] triangle1 = new float[9]; 
			float [] triangle2 = new float[9]; 
			for (int i =0;i<9;i++)
			{
				triangle1[i]=input[i];
			}
			for (int i =9;i<18;i++)
			{
				triangle2[i-9]=input[i];
			}
				

			            			qstart_time = System.nanoTime();

			boolean is_con = shape_intr.IS_CONNECTED(triangle1, triangle2);		
			qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                        System.out.println("Is connected = " + is_con);
                        break;

                    case "NEIGHBORS_OF_POINT":
			System.out.println("Finding NEIGHBORS_OF_POINT" );
			 			             			qstart_time = System.nanoTime();

			 PointInterface [] nbrs_of_point = shape_intr.NEIGHBORS_OF_POINT(input);
			 qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
			  for (int z=0;z<nbrs_of_point.length;z++){
						for (int y=0;y<3;y++){
							System.out.print(nbrs_of_point[z].getXYZcoordinate()[y]+ " ");
						}
						System.out.println();
				}
			break;

                    case "NEIGHBORS_OF_TRIANGLE":
			System.out.println("Finding NEIGHBORS_OF_TRIANGLE" );
			            			qstart_time = System.nanoTime();
TriangleInterface[] triangle_neighbors= shape_intr.NEIGHBORS_OF_TRIANGLE(input);
			qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                        for (int z=0;z<triangle_neighbors.length;z++){
					for (int x=0;x<3;x++){
						for (int y=0;y<3;y++){
							System.out.print(triangle_neighbors[z].triangle_coord()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
			break;

                    case "INCIDENT_TRIANGLES":
			System.out.println("Finding INCIDENT_TRIANGLES " );
			            			qstart_time = System.nanoTime();
TriangleInterface[] triangle_neighbors_ = shape_intr.INCIDENT_TRIANGLES(input);
			qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                        for (int z=0;z<triangle_neighbors_.length;z++){
					for (int x=0;x<3;x++){
						for (int y=0;y<3;y++){
							System.out.print(triangle_neighbors_[z].triangle_coord()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
			break;

                    case "VERTEX_NEIGHBOR_TRIANGLE":
			System.out.println("Finding VERTEX_NEIGHBOR_TRIANGLE " );
			            			qstart_time = System.nanoTime();
PointInterface[] res= shape_intr.VERTEX_NEIGHBOR_TRIANGLE(input);
			qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                         for (int z=0;z<res.length;z++){
						for (int y=0;y<3;y++){
							System.out.print(res[z].getXYZcoordinate()[y]+ " ");
						}
					System.out.println();
				}
                       	 break;

                    case "EXTENDED_NEIGHBOR_TRIANGLE":
			System.out.println("Finding EXTENDED_NEIGHBOR_TRIANGLE " );

			            			qstart_time = System.nanoTime();
TriangleInterface [] extended_neighbor_triangle = shape_intr.EXTENDED_NEIGHBOR_TRIANGLE(input);
		qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                        	for (int z=0;z<extended_neighbor_triangle.length;z++){
					for (int x=0;x<3;x++){
						for (int y=0;y<3;y++){
							System.out.print(extended_neighbor_triangle[z].triangle_coord()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
			break;

	          case "MAXIMUM_DIAMETER":
			System.out.print("MAX DIAMETER:\t " );
			            			qstart_time = System.nanoTime();
int diameter = shape_intr.MAXIMUM_DIAMETER();
			qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                        System.out.println(diameter);

                        break;
                    case "EDGE_NEIGHBOR_TRIANGLE":
			System.out.println("Finding EDGE_NEIGHBOR_TRIANGLE ");
			             			qstart_time = System.nanoTime();
EdgeInterface [] edge_neighbors_of_triangle = shape_intr.EDGE_NEIGHBOR_TRIANGLE(input);
			 qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                         for (int z=0;z<edge_neighbors_of_triangle.length;z++){
					for (int x=0;x<2;x++){
						for (int y=0;y<3;y++){
							System.out.print(edge_neighbors_of_triangle[z].edgeEndPoints()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
                        break;

                   case "FACE_NEIGHBORS_OF_POINT":
			System.out.println("Finding FACE_NEIGHBORS_OF_POINT ");
			             			qstart_time = System.nanoTime();
TriangleInterface [] face_nbrs = shape_intr.FACE_NEIGHBORS_OF_POINT(input);
			qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                         for (int z=0;z<face_nbrs.length;z++){
					for (int x=0;x<3;x++){
						for (int y=0;y<3;y++){
							System.out.print(face_nbrs[z].triangle_coord()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
                        break;



                   case "EDGE_NEIGHBORS_OF_POINT":
			System.out.println("Finding EDGE_NEIGHBORS_OF_POINT ");
			             			qstart_time = System.nanoTime();
EdgeInterface [] edge_nbrs = shape_intr.EDGE_NEIGHBORS_OF_POINT(input);
			 qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                         for (int z=0;z<edge_nbrs.length;z++){
					for (int x=0;x<2;x++){
						for (int y=0;y<3;y++){
							System.out.print(edge_nbrs[z].edgeEndPoints()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
                        break;

                    case "TRIANGLE_NEIGHBOR_OF_EDGE":
			System.out.println("Finding TRIANGLE_NEIGHBOR_OF_EDGE ");
			             			qstart_time = System.nanoTime();
TriangleInterface [] _triangle_neighbors = shape_intr.TRIANGLE_NEIGHBOR_OF_EDGE(input);
			qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                         for (int z=0;z<_triangle_neighbors.length;z++){
					for (int x=0;x<3;x++){
						for (int y=0;y<3;y++){
							System.out.print(_triangle_neighbors[z].triangle_coord()[x].getXYZcoordinate()[y]+ " ");
						}
							System.out.print("\t");
					}
					System.out.println();
				}
			break;
		

	          case "CENTROID":
			System.out.println("Finding CENTROID " );
			            			qstart_time = System.nanoTime();
PointInterface [] centroid_array = shape_intr.CENTROID();
			qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                        for (int z=0;z<centroid_array.length;z++){
						for (int y=0;y<3;y++){
							System.out.print(centroid_array[z].getXYZcoordinate()[y]+ " ");
						}
						System.out.println();
				}

                        break;
                    case "CENTROID_OF_COMPONENT":
			System.out.println("Finding CENTROID_OF_COMPONENT ");
			             			qstart_time = System.nanoTime();
PointInterface centroid_of_component = shape_intr.CENTROID_OF_COMPONENT(input);
					qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                        	for (int y=0;y<3;y++){
							System.out.print(centroid_of_component.getXYZcoordinate()[y]+ " ");
						}
						System.out.println();
                        break;

                    case "CLOSEST_COMPONENTS":
			System.out.println("Finding CLOSEST_COMPONENTS ");
			             			qstart_time = System.nanoTime();
 PointInterface [] closest_vertices = shape_intr.CLOSEST_COMPONENTS();
			qend_time = System.nanoTime();
                        TOT_TIME+=(qend_time-qstart_time);
                        if (closest_vertices==null) System.out.println("NULL");
                        else{
                          for (int z=0;z<closest_vertices.length;z++){
                        
						for (int y=0;y<3;y++){
							System.out.print(closest_vertices[z].getXYZcoordinate()[y]+ " ");
						}
						System.out.println();
					}
				}
			break;
		


	//	default :System.out.println(cmd[0] +" not found");	
	//		break;
			
                }

            }
	}
	catch(Exception e)
	{	e.printStackTrace();
		//System.err.println("Error parsing: 2	 " +e);
	}
	finally{
		System.err.println("Time elapsed (ms): "+TOT_TIME/1000000f);
	}
	

}

}
