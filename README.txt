
COL106 - Data Structures & Algorithms
Assignment 6 

+===============================================+

STATUS: All Working | ISSUES: Nil 
(Last Updated: 2019-11-09 23:51:06)

+-----------------------------------------------+
| README.txt									                  |
+-----------------------------------------------+

> class Point
  Contains vertex coordinates, neighbouring edges and triangles. 'visited' is used to mark during DFS or BFS traversals. The methods addEdges() and addTriangles() adds the neighbouring edges and triangles and getEdges() and getTriangles() returns them. Two points are same if they have the same x,y,z coordinates. add() adds the (x,y,z) coordinates to the exisitng point and avg() divides each coordinate by a value (average). A order is maintained for the points as follows: given two points (x1,y1,z1) and (x2,y2,z2), (x1,y1,z1) comes first if x1<x2 or x1=x2 and y1<y2 or x1=x2 and y1=y2 and z1<z2; no priority if all the coordinates are the same; after otherwise.

> class Edge
  A edge is made up of two points and order does not matter. It stores the neighbouring triangles also-addTriangles() and getTriangles() are used for that. Two edges are same if both of them have the same points. Given a point, it is possible to find the other point that belongs to the edge. A order is maintained in edges using the standard Euclidean Distance notion. 

> class Triangle
  A triangle is made up of three points and edges connecting them. A visited marker is used to mark the triangle during DFS and BFS traversals. getEdges() returns all the edges of the triangle. Two triangles are same if they have the same set of points (in any order). A point is contained in a triangle if one of the vertex of the triangle is the point itself. A total order is maintained in triangles by assigning it a unique ID at the time of insertion.



>>>>> begin Some useful data structures used
> class ArrayList<T>
  Stores a array for type T (type Object in implementation) whose size can grow if it has got full. A element can be added to the ArrayList if the limit of number of elements in it is crossed, a new array is made with double the size and the exisitng elements are copied to it. So insertion is O(1) except at the resizing step (O(n)) which happens sometimes. It is ammortized O(1) though. toArray() returns the array form and size() returns numebr of elements present in the array.

> class ArrayListFor(Point/Edge/Triangle)s
  An extension of ArrayList specific to these datastructures made to return the array directly (without typecasting). trimmedArray() returns an array of length equal to the number of elements in it.


> class QueueFor(Point/Triangle)s
  A typical Queue implementation which has proeprty of FIFO. Front refers to the element who is waiting to get dequeued and rear to the place where an element must get enqueued. A queue is empty if front is rear. resetToUnvisited() resets the visted state of all the elements insert (feature supported by these two data structure only).

> class StackFor(Point/Triangle)s
  A typical Stack implementation which has proeprty of LIFO. Elements are added at top and removed from the topmost element.


> class RedBlackNode<T,E>
  A node for Red-Black Tree. Stores a key and a list of values, left and right child, parent and colour (R-Red by default).  
    It has the following (trivial) functions:
    • addData() adds data at the key
    • setColor() sets the specified colour
    • chnageParent() changes the parent
    • makeLeftChild() makes a left child
    • makeRightChild() makes a right child
    • getKey() gets the key
    • getParent() gets the parent
    • getLeftChild() gets the left child
    • getRightChild() gets the right child
    • getColor() gets the color
    • clearValue() clears the data in the node
    • getValue() gets the first value from the list of values • getValues() gets the list of values present

> class RBTreeForEdges
  This is a RedBlack Tree especially for storing Edges. It contains a root (type RedBlackNode<Float,Edge>) which has children, thereby making a tree. The Euclidean distance between the edges is used as the key.

  - searchAndInsert(Point p1,p2)                                              O(logn)
    If root is null, insert directly, recolour to black, add endpoints as neighbours of point and return. Find if key already exists by traversing down. Go left if key < curr.key; right if key > curr.key. If equal, insert it to the list and return. If key is not found, Add the key. If we don't find a key, we insert them and update the list of neighbours of the end points. 

    To preserve the property of RB Tree, we recolour the nodes if required. IF Uncle and Parent, both, are Red, we recolour both Black, make Grand Parent Red and recurse this process at Grandparent. If Parent is Black and Uncle is Red, four cases arise-
    ( Let X be the child who is creating problems, P be his parent and G his grandparent)
    (Left and Right Rotation explained in Appendix 1)
    • P is Left child of G, X is Left child of P (Left-Left): Right Rotation at G.
    • P is Left child of G, X is Right child of P (Left-Right): Left Rotation at P , then Right Rotation at G. 
    • P is Right child of G, X is Left child of P (Right-Left): Left Rotation at P , then Right Rotation at G. 
    • P is Right child of G, X is Right child of P (Right-Right): Left Rotation at G.   
    This restructuring is done once. 
    There is no problem if Parent is Black!
    Make root Black (just in case it was changed in the process of Recoloring).
    Return the edge inserted.

  - search()                                                                      O(logn)
    Given the coordinates, search for the edge by traversing down. Go left if key < curr.key; right if key > curr.key until the required key is found. If found, return all the values contained in it. Else return null.

  - boundaryEdges(int size)                                                     O(n)
    Returns the array of boundaries of given size by doing a in-order traversal and adding the boundary edges alone. It is self sorted.

  -


>>>>>>> Main class
> class Shape
  (Let V=number of vertices, E=number of edges, T=number of triangles, L_v-load factor for a vertex, L_t-load factor for a triangle, log is base 2)
  
  - getHashCode(hash) 
    Given hash, generate the hash value by <int(abs(hash*1000)) mod HASH_SIZE> where HASH_SIZE is the size of hash table used.

  - Use a ID generator for triangles to store it's order of insertion.
  - Maintain a array of arraylist (array used in hashing) of Points and Triangles and RBTree of Edges.
  - Set IMPROPER_MESH =false, number of edges contributing to SEMI_MESH=0, total points and triangles to 0 and added_new to true

  - ADD_TRIANGLE()                                                                O(V_t+log(E))
    Given coordinates of triangle, find if its a valid triangle by finding its area. If it is good enough, proceed. Check if points are already added, else add them. Similarly, search for edges and add them. Add the triangle to list. Update the neighbours of Points and edges accordingly. Set added_new to true. Find the number of triangle neighbours to each edge involved. If 1-SEMI_MESH++ for each, 2-SEMI_MESH-- for each (it was increased previously). If >2-IMPROPER_MESH=true. 

  - TYPE_MESH()                                                                           O(1)
    if IMPROPER_MESH=true return 3. If SEMI_MESH==0, return 2. Else return 1.

  - ArrayList of ArrayListForTriangles stored a list different sets of connected components.

  - findComponents()                                                                     O(T+E)
    If no new triangle added (added_new=false), no changes to be done. so return.
    Make a new ArrayList components to store the connected components.
    Go through each triangle. If it is not visited, make a temp ArrayListForTriangles to store these connected components. Add the triangle to a stack and mark visited. Till the stack is not empty, pop a triangle. add the popped triangle to temp list. Get edges of the triangle and get the triangle neighbours of each of the edge. If any of them is not visited, mark them visited, push it to the stack. After stack is empty, we have got a set of connected components. Append this to components. Continue traversing and repeat the process if required. At the end, mark all the triangles visited=false, added_new=false and list of components is ready!

  * In the subsequent methods where findComponents() is used, we ignore the time complexity of findComponents() as a new triangle need not be added all the time in which case its time complexity is O(1). It can be added wherever required!
  - IS_CONNECTED()                                                               Worst:  O(T)*
    Prepare the components by calling findComponents()
    search for the given triangle coordinates. If both of them belonged to the same set of component, return true. If we have found one in a component and not the other, its not connected, so return false. Return false if loop terminated.

  - COUNT_CONNECTED_COMPONENTS()                                                        O(1)*
    findComponents() and return size of components.

  - mergeSortPoints()                                     (n is size of array)      O(nlogn)   
    Given a array of points, merge sort them iteratively. Merging is done based on the order of points defined above (in Point)-lowest first.

  - centroid_store stores the list of centroid for using in future if required

  - computeCentroid(index)                                                WORST:   O(T+V)
    make a counter of number of points seen (set to 0) and centroid to (0,0,0). the index is the index-th connected components in the components. Start from the first triangle in the component. Get the vertices. If unvisited, add the coordinate value (individually) to the centroid and increase the counter for number of points seen and make visited. After computing all the triangles in that index, divide each coordinate by the total number of vertices.

  - CENTROID()                                                                     O(T+V)*
    If added_new_flag_for_centroid (made true when a new triangle is added) = false && centroid_store!=null, return mergeSort(centroid_store)
    findComponents()
    For each component, ComputeCentroid(i-th set of component) and remark each vertices visited=false. Append centroid returned to a list. Make this centroid_store and added_new_flag_for_centroid=false and return mergeSort(centroidStore)

  - CENTROID_OF_COMPONENT((x,y,z))                                O(# tri+vert in component)*
    findComponent()
    find to which set of components this triangle belongs to. Note down this index. if added_new_flag_for_centroid (made true when a new triangle is added) = false && centroid_store!=null, return centroid_store[index to which it belongs to]. If a position is found, computeCentroid(index found). Mark points visited=false. Return centroid.

  - CLOSEST_COMPONENTS()                                            WORST: O(P^2)*
    findComponents()
    For each set of component, iterate through the set of components next to it till the end. Get a point from a triangle in a component. For each point of triangle in the other component, if their distance is less than the previously stored value, update the point.
    Return the point stored.

  - MAXIMUM_DIAMETER()                                                          O(T^2)*
    findComponents()
    Get the component with maximum number of triangles.
    If there is only one triangle return 0
    Do a BFS starting from each triangle. Use two queues-q1,q2. Add a triangle to q2 and mark it visited. until q2 is not empty, q1=q2, q2=new Queue, count=-1, while (q1 is not empty): dequeue each triangle from q2, get the adjacent triangles sharing the common edge and if it is not visited, enqueue it to q2 and marking it visited. if count>max till now, update. make all triangles visited=false. repeat with next triangle in the component. If max==no of triangles - 1, thats the maximum possible hops possible, so we will be done. return max_dia obtained. 

  - BOUNDARY_EDGES()                                                              O(E)
    boundaryEdges() in RBTree does a in-order traversal and returns those edges having one triangle only in sorted manner.

  - EXTENDED_NEIGHBOR_TRIANGLE()                                             O(L_t+size(result))
    Get all the different list of neighbouring triangles from the vertices after finding the required triangle. Merge them on the basis of ID and while merging, ignore duplicate by checking if the currently added triangle is same as the previous one and the given triangle.

  - INCIDENT_TRIANGLES()                                                    O(L_v+size(result))
    Given a point, find it in the list of points available. get the neighbouring triangales stored in them and return of appropriate size.

  - FACE_NEIGHBORS_OF_POINT()
    Duplicate of INCIDENT_TRIANGLES()

  - NEIGHBORS_OF_TRIANGLE()                                                 O(L_t+size(res))
    Given a triangle, find it in the list of triangles. Get the list of neighbouring triangles of each edge. Merge them such that first ID triangle comes first (and same triangle not iincluded). 

  - NEIGHBORS_OF_POINT()                                                    O(L_t+size(res))
    Given a point, get the corresponding object. For each neighbouring edge stored, add the other endpoint and return the list.

  - TRIANGLE_NEIGHBOR_OF_EDGE()                                            O(log(E)+size(res))
    Find the corresponding edge object from RBTree. Return the triangle neighbour stored in it.

  - EDGE_NEIGHBORS_OF_POINT()                                             O(L_v+size(res))
    Given a point, find its correspodning object and return the neighbouring edges stored in it.

  - EDGE_NEIGHBOR_TRIANGLE()                                                O(L_t)
    Given a triangle, find its corresponding object and return the edges present in it.

  - VERTEX_NEIGHBOR_TRIANGLE                                                  O(L_t)
    Given a triangle, find its corresponding object and return the points (vertices) present in it.




+-----------------------------------------------+
| INTERESTING FINDINGS                          |
+-----------------------------------------------+
- Using RBTree not only made searching O(logn) but sorting (by just traversing) O(n)
- Too late, but just realised that members of public class are accessible directly within the same package!



+-----------------------------------------------+
| Appendix                      |
+-----------------------------------------------+
1:-------->
1.1 Left Rotation
Let G be a node, P it’s right child, X it’s right-right grandchild (assuming everyone exists or can be obtained by a similar approach for other cases). By Left Rotation at G, we make the left child of P (if it exists) as the right child of G, G as the left child of P and G’s ex-parent as the parent of P . This is the same result as seen by visualisation.

1.2 Right Rotation
Let G be a node, P it’s left child, X it’s left-left grandchild (assuming everyone exists or can be obtained by a similar approach for other cases). By Right Rotation at G, we make the right child of P (if it exists) as the left child of G, G as the right child of P and G’s ex-parent as the parent of P . This is the same result as seen by visualisation.

+===============================================+
