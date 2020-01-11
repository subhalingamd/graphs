//REPLACE <0 with <=0
public class RBTreeForEdges  {
	RedBlackNode<Float,Edge> root=null;
    int ct=0;

    private void rotateLeft(RedBlackNode<Float,Edge> pivot){
        RedBlackNode<Float,Edge> temp= pivot.getParent();
        pivot=pivot.getRightChild();

        if (pivot.getLeftChild()!=null)
            pivot.getLeftChild().changeParent(pivot.getParent());   // Changing P's LC parent GF
        pivot.getParent().makeRightChild(pivot.getLeftChild());  // GF RC is changed
        pivot.getParent().changeParent(pivot);                   // GF's parent is parent
        pivot.makeLeftChild(pivot.getParent());
        pivot.changeParent(temp);   
        if (temp==null)
            root=pivot;
        else{
            if (pivot.getKey().compareTo(pivot.getParent().getKey())<0)
                pivot.getParent().makeLeftChild(pivot);
            else
                pivot.getParent().makeRightChild(pivot);

        }                           // P's parent is ex-GF's parent
        
    }

    private void rotateRight(RedBlackNode<Float,Edge> pivot){
        RedBlackNode<Float,Edge> temp= pivot.getParent();
        pivot=pivot.getLeftChild();
        

        if (pivot.getRightChild()!=null)
            pivot.getRightChild().changeParent(pivot.getParent());   // Changing P's RC parent GF
        pivot.getParent().makeLeftChild(pivot.getRightChild());  // GF LC is changed
        pivot.getParent().changeParent(pivot);  
        pivot.makeRightChild(pivot.getParent());                 // GF's parent is parent
        pivot.changeParent(temp);
        if (temp==null)
            root=pivot;  
        else{
            if (pivot.getKey().compareTo(pivot.getParent().getKey())<0)
                pivot.getParent().makeLeftChild(pivot);
            else
                pivot.getParent().makeRightChild(pivot);

        }                              // P's parent is ex-GF's parent
        
    }



    public Edge searchAndInsert(Point p1,Point p2) {
        Edge e=new Edge(p1,p2);
        float[] c1=p1.getXYZcoordinate(),c2=p2.getXYZcoordinate();
        float dt=(float)Math.sqrt(Math.pow(c1[0]-c2[0],2)+Math.pow(c1[1]-c2[1],2)+Math.pow(c1[2]-c2[2],2));
        Float key=Float.valueOf(dt);

        if (root==null){
    		root=new RedBlackNode<Float,Edge>(key,e,null);
            p1.addEdge(e);
            p2.addEdge(e);
    		root.setColor('B');
            ct++;
    		return e;
    	}
    	RedBlackNode<Float,Edge> parent=null,curr=root,uncle;
        while (curr!=null){
            if (dt==curr.getKey().floatValue()){
                ArrayList<Edge> data_edges=curr.getValues();
                for (int i=0;i<data_edges.size();i++){
                     float[] a=data_edges.get(i).edgeEndPoints()[0].getXYZcoordinate();
                     float[] b=data_edges.get(i).edgeEndPoints()[1].getXYZcoordinate();
                     //System.out.println("["+a[0]+a[1]+a[2]+"]"+"["+b[0]+b[1]+b[2]+"]");
                     if ((c1[0]==a[0] && c1[1]==a[1] && c1[2]==a[2] && c2[0]==b[0] && c2[1]==b[1] && c2[2]==b[2]) || (c1[0]==b[0] && c1[1]==b[1] && c1[2]==b[2] && c2[0]==a[0] && c2[1]==a[1] && c2[2] == a[2])){
                         //REMOVE
                         //System.out.println("DUP");
                         return data_edges.get(i);
                     }       
                }
                curr.addData(e);
                p1.addEdge(e);
                p2.addEdge(e);
                //ct++;
                return e;
            }
    		
            parent=curr;
            
    		if (dt<curr.getKey().floatValue())
    			curr=curr.getLeftChild();
    		else
    			curr=curr.getRightChild();
    	}

    	if (key.compareTo(parent.getKey())<0){
    	  parent.makeLeftChild(new RedBlackNode<Float,Edge>(key,e,parent));
          curr=parent.getLeftChild();
        }
    	else{
    	  parent.makeRightChild(new RedBlackNode<Float,Edge>(key,e,parent));
          curr=parent.getRightChild();
        }

        //IMP
        p1.addEdge(e);
        p2.addEdge(e);
        ct++;


        while (curr!=root && parent!=root){

            if (parent.getKey().compareTo(parent.getParent().getKey())<=0)
                uncle=parent.getParent().getRightChild();
            else 
                uncle=parent.getParent().getLeftChild();
    
            if (parent.getColor()=='R' && (uncle==null || uncle.getColor()=='B')){
                if (curr==parent.getLeftChild()){       //*L
                    if (parent==parent.getParent().getLeftChild()){     //LL                        
                        rotateRight(parent.getParent());
                        curr.getParent().setColor('B');
                        curr.getParent().getRightChild().setColor('R');
                    }
                    else{                                               //RL
                        rotateRight(parent);     //Child becomes parent
                        rotateLeft(curr.getParent());
                        curr.setColor('B');
                        curr.getLeftChild().setColor('R');
                    }
                }

                else{                                                   //*R
                    if (parent==parent.getParent().getLeftChild()){     //LR
                        rotateLeft(parent);     //Child becomes parent
                        rotateRight(curr.getParent());
                        
                        curr.setColor('B');
                        curr.getRightChild().setColor('R');
                    }
                    else{       
                        rotateLeft(parent.getParent());
                        curr.getParent().setColor('B');
                        curr.getParent().getLeftChild().setColor('R');
                    }                                                 
                }
                break; //We are done
            }

            else if (parent.getColor()=='R' && uncle.getColor()=='R'){
                    curr=curr.getParent().getParent();
                    //System.out.println(curr);
                    //System.out.println(curr.getLeftChild());
                    curr.getLeftChild().setColor('B');
                    curr.getRightChild().setColor('B');
                    curr.setColor('R');
                    parent=curr.getParent();
            }

            else //No work
                break;
                
        }
    	root.setColor('B');
        return e;
    }


    ///Needs modification for duplicate keys
    public Edge search(float[] coordinates) {
        float[] c1=new float[3];
        float[] c2=new float[3];
        c1[0]=coordinates[0];
        c1[1]=coordinates[1];
        c1[2]=coordinates[2];
        c2[0]=coordinates[3];
        c2[1]=coordinates[4];
        c2[2]=coordinates[5];
        float dt=(float)Math.sqrt(Math.pow(c1[0]-c2[0],2)+Math.pow(c1[1]-c2[1],2)+Math.pow(c1[2]-c2[2],2));

    	RedBlackNode<Float,Edge> curr=root;
    	while (curr!=null){
    		if (dt==curr.getKey().floatValue()){
                ArrayList<Edge> data_edges=curr.getValues();
                for (int i=0;i<data_edges.size();i++){
                     float[] a=data_edges.get(i).edgeEndPoints()[0].getXYZcoordinate();
                     float[] b=data_edges.get(i).edgeEndPoints()[1].getXYZcoordinate();
                     //System.out.println("["+a[0]+a[1]+a[2]+"]"+"["+b[0]+b[1]+b[2]+"]");
                     if ((c1[0]==a[0] && c1[1]==a[1] && c1[2]==a[2] && c2[0]==b[0] && c2[1]==b[1] && c2[2]==b[2]) || (c1[0]==b[0] && c1[1]==b[1] && c1[2]==b[2] && c2[0]==a[0] && c2[1]==a[1] && c2[2] == a[2])){
                         //REMOVE
                         //System.out.println("DUP");
                         return data_edges.get(i);
                     }       
                }
                return null;
            }
    		if (dt<curr.getKey().floatValue())
    			curr=curr.getLeftChild();
    		else
    			curr=curr.getRightChild();
    	}
        return null;
    }

    public Edge[] boundaryEdges(int size){
        ArrayListForEdges e=new ArrayListForEdges(size);

        if (root != null){
            StackNodes s = new StackNodes(ct+10); 
            RedBlackNode<Float,Edge> curr = root; 
      
            while (curr != null || !s.isEmpty()) 
            { 
                while (curr!=null) 
                {
                    s.push(curr); 
                    curr = curr.left; 
                } 

                curr = s.pop();

                ArrayList<Edge> list=curr.getValues();
                for (int i=0;i<list.size();i++){
                    if (list.get(i).getTriangles().size()==1){
                        //System.out.println(list.get(i).e[0].p[0]+" "+list.get(i).e[0].p[1]+" "+list.get(i).e[0].p[2]+" \t "+list.get(i).e[1].p[0]+" "+list.get(i).e[1].p[1]+" "+list.get(i).e[1].p[2]);
                        e.add(list.get(i));
                    }
                }

                curr =curr.right; 
            } 
        }





        //traverse(root, e);

        //System.out.println("TEST: "+e.toArray().length);
        //NOT REQ
        //if (e.size()==0) return null;
        return e.toArray();

    }

    /*
    public void traverse(RedBlackNode<Float,Edge> node,ArrayListForEdges e){
        if (node==null) return;
        traverse(node.getLeftChild(),e);
        ArrayList<Edge> list=node.getValues();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getTriangles().size()==1){
                //System.out.println(list.get(i).e[0].p[0]+" "+list.get(i).e[0].p[1]+" "+list.get(i).e[0].p[2]+" \t "+list.get(i).e[1].p[0]+" "+list.get(i).e[1].p[1]+" "+list.get(i).e[1].p[2]);
                e.add(list.get(i));
            }
        }
        traverse(node.getRightChild(),e);

    }
    */


    /*Not using this
    public E popValue(T key){
        RedBlackNode<T, E> curr=root;
        while (curr!=null){
            if (curr.getKey().compareTo(key)==0)
                return curr.removeData();

            if (key.compareTo(curr.getKey())<=0)
                curr=curr.getLeftChild();
            else
                curr=curr.getRightChild();
        }
        return null;
    }
    */
    


}