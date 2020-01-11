
COL106 - Data Structures & Algorithms
Assignment 3 

+===============================================+

STATUS: All Working | ISSUES: Nil 
(Last Updated: 2019-09-12 22:51:06)

+-----------------------------------------------+
| README.txt									                  |
+-----------------------------------------------+

> class Student (implements Student_)
  - Contains the details of Students-first name, last name, hostel, department and CGPA.
  - Various functions are used for returning these values.
  - toString() method is overrided to return the string in [<First Name>" "<Last Name>" "<Hostel>" "<Department>" "<CGPA>] format



> class KeyAndObj<K,T>
  - Stores the Key (type K) and Object (type T) as a single unit.
  - Various functions are used for returning its field attributes.



> class Pair<T1,T2> (implements Comparable<Pair<T1,T2>>)
  - Stores a pair of objects of type T1 and T2.
  - toString() is overriden to return concat(T1Obj.toString(),T2Obj.toString())
  - compareTo(T P) compares (given object's) this.T1Obj.toString() to P.T1Obj.toString() and returns the int returned from this.T1Obj.toString().compareTo(P.T1Obj.toString()),i.e., <0 integer value if this' T1Obj comes lexicographically first, 0 if equal or >0 integer if P's T1Obj comes first.



> class Hash
  - class that has methods for computing the hashvalue from the hash functions.
  - djb2() and sdbm() are two hash functions used in the assignment and it's implementation is taken from the course webpage directly.




> class MyHashTableForDH<K, T> (implements MyHashTable_<K, T>)
  - Implements Hash Table for Double Hashing.

  - We say an index i (0<=i<capacity) is 'null' iff it had no previous history of any object in it. If there was an object in the index before and if it was removed, we use the term 'pseudo-null'.

  > int insert(K key, T obj)
    - Used to insert the given object with the given key in the hash Table
    - 0<=i<capacity: hash=(djb2(key)+i*sdbm(key)) mod capacity; i=i+1;
        if Arr[hash] is null, insert the object && return i (No of steps taken); 
        if Arr[hash] is pseudo-null, insert the object && return i;
        if Arr[hash] has same key as key, return -1 (> "E")
      if i==capacity (loop terminated) => table is full => return -1 (> "E");

  > int update(K key, T obj)
    - Used to update the given object for the given key in the hash Table
    - 0<=i<capacity: hash=(djb2(key)+i*sdbm(key)) mod capacity; i=i+1;
        if Arr[hash] is null, there is no possibility of the key being present after this (as this index has no history of any key being inserted earlier) => return -1 (> "E"); 
        if Arr[hash] is not pseudo-null:
          if Arr[hash].key==key (has same key as key), update Arr[hash] according to new obj && return i;
      if i==capacity (loop terminated) => table is full but no match for key found => return -1 (> "E");

  > int delete(K key)
    - Used to delete the object with the given key in the hash Table
    - 0<=i<capacity: hash=(djb2(key)+i*sdbm(key)) mod capacity; i=i+1;
        if Arr[hash] is null, there is no possibility of the key being present after this (as this index has no history of any key being inserted earlier) => return -1 (> "E"); 
        if Arr[hash] is not pseudo-null:
          if Arr[hash].key==key (has same key as key), make Arr[hash] pseudo-null && return i;
      if i==capacity (loop terminated) => table is full but no match for key found => return -1 (> "E");

  > boolean contains(K key)
    - Used to check if the given key in the hash Table
    - 0<=i<capacity: hash=(djb2(key)+i*sdbm(key)) mod capacity; i=i+1;
        if Arr[hash] is null, there is no possibility of the key being present after this (as this index has no history of any key being inserted earlier) => return false; 
        if Arr[hash] is not pseudo-null:
          if Arr[hash].key==key (has same key as key), Arr[hash] has the required object => return true;
      if i==capacity (loop terminated) => table is full but no match for key found => return false;

  > T get(K key)
    - Used to get the object with the given key in the hash Table
    - 0<=i<capacity: hash=(djb2(key)+i*sdbm(key)) mod capacity; i=i+1;
        if Arr[hash] is null, there is no possibility of the key being present after this (as this index has no history of any key being inserted earlier) => throw NotFoundException; 
        if Arr[hash] is not pseudo-null:
          if Arr[hash].key==key (has same key as key), return the object in Arr[hash];
      if i==capacity (loop terminated) => table is full but no match for key found => return NotFoundException;

  > String address(K key)
    - Used to get the address (index) of the object with the given key in the hash Table
    - 0<=i<capacity: hash=(djb2(key)+i*sdbm(key)) mod capacity; i=i+1;
        if Arr[hash] is null, there is no possibility of the key being present after this (as this index has no history of any key being inserted earlier) => return NotFoundException; 
        if Arr[hash] is not pseudo-null:
          if Arr[hash].key==key (has same key as key), make return the hash (index where this object is stored) as String;
      if i==capacity (loop terminated) => table is full but no match for key found => return NotFoundException;



> class MyHashTableForSC<K, T> (implements MyHashTable_<K, T>)

  > private class KeyAndObjNode<K,T>
    The Node for BST. data stores KeyAndObj while left and right contain reference to left and right child. A constructor is used to instantiate an object of this class with data passed and left and right to null.
    (NOTATION: For simplicity, .data is ignored in method descriptions below. Also description is given in simplified manner for easy understanding, ignoring the .data, .getKey(), etc.)

  - Implements Hash Table for Seperate Chaining using BSTs.
  - (NOTE: root stores the current node that's being iterated and parent it's parent)
  - (NOTE: the key for BST is only the first name, as described in assignment)
  - (NOTATION: If I say key1 < (or >=) key2, only the first object's String in Pair(-here) (that's what compareTo() of Pair does) is compared. However concatenation of both object's string is used when I say key1 == key2)
  - (Successor gets shifted when deleting a node & Duplicate goes to/as Right child)

  > int insert(K key, T obj)
    hash=djb2(key);
    Set int i=1;
    if Arr[hash]==null (means there is no root element in this location), then the inserting object becomes the root && return i;
    root=Arr[hash] && parent=null; (parent is the parent for root from i=2)
    while (root is not null):
      parent:=root
      if key==root.key => duplicate : return -1 (> "E");
      if key<root.key: root=root.left (We have to move to the left as given key value < current key);
      else : root=root.right (We have to move to the right as given key value >= current key);
      i:=i+1;
    Loop terminates when we have reached the last node and its time to insert
    if (key<parent.key) : parent.left=new Object (insert here);
    else : parent.right=new Object (insert here)
    return i;

    

  > int update(K key, T obj)
    hash=djb2(key);
    if Arr[hash]==null (means empty in this location), return -1 (> "E");
    Set int i=1;
    root=Arr[hash] && parent=null; (parent is the parent for root from i=2)
    if Arr[hash].key==key : update Arr[hash] with new data && return i(=1);
    while (root is not null):
      if key==root.key => found the node to be updated:
        if (key<parent.key) : parent.left to be updated;
        else : parent.right to be updated;
        && return i;
      parent:=root
      if key<root.key: root=root.left (We have to move to the left as given key value < current key);
      else : root=root.right (We have to move to the right as given key value >= current key);
      i:=i+1;
    Loop terminates when we have reached the last node
    if this line is reached => required key not found in BST => return -1 (=> 'E');
    

  > int delete(K key)
    hash=djb2(key);
    if Arr[hash]==null (means empty in this location : no BST), return -1 (> "E");
    set bool found = false, int i=1;
    while (root is not null):
      if key==root.key => found the node to be deleted:
        found:=true && break;
      parent:=root
      if key<root.key: root=root.left (We have to move to the left as given key value < current key);
      else : root=root.right (We have to move to the right as given key value >= current key);
      i++;

    Loop terminates when we have reached the last node (found=false) or required element is found (found=true);

    if (found==false) : return -1 (> "E");

    if (parent==null): => the actual root to be deleted
      if root has no left or right child, i.e., leaf node: Arr[hash] (root) = null;
      if root has either left/right child: Arr[hash]:=Arr[hash].right/Arr[hash].left;
      else (root has both childs):
        We first find the immediate successor of the deleting node.
        Set succ=root.right and succpar (the parent of successor)=root && i:=i+1;
        Upon careful observation, it is easy to show that the for successor we have to go Right (R) and go Left (L) until there is no further L node. Such a leaf node will be the successor.
        while (succ.left is not null):
          succpar:=succ;
          succ:=succ.left;
          i:=i+1;
        Store succ.data as another object, say in succ_data;
        if (succpar==root): there was no L, we have moved only R once
          if (succ.right is not null): successor has a right child
            succpar.right:=succ.right && i:=i+1;
          else: successor has no right node (leaf)
            succpar.right:=null;
          (Note: the successor can be a leaf node (no children) or can have Right child only. If it had an left child, then we should have gone further Left)
        else if (succ.right is not null): This is when there is at least on Left child after the Right child of the deleting node
          succpar.left:=succ.right && i:=i+1;
        else : succ.right is null
          succpar.left:=null;
        Replacing the deleting node's data with the successor's data, 
        Arr[hash].data:=succ_data;

    else (i.e., parent is not null): => the node to be deleted is not the root. In this case root represents the curr node as mentioned before!
      if root has no left or right child, i.e., leaf node: 
        if key<parent.key: parent.left:=null (We have to delete the left child as given key value < parent key and hence deleting node is in left);
        else : parent.right:=null (We have to delete the right child as given key value >= parent key and hence deleting node is in right);

      if root has either left/right child: 
        if key<parent.key: parent.left:=root.right/left (We have to delete the left child of parent as given key value < parent key and hence deleting node is in left);
        else : parent.right:=root.right/left (We have to delete the right child of parent as given key value >= parent key and hence deleting node is in right);
        i:=i+1;

      else (root has both childs):
        We first find the immediate successor of the deleting node.
        Set succ (desired successor) = root.right and succpar (the parent of successor) = root && i:=i+1;
        Upon careful observation, it is easy to show that the for successor we have to go Right (R) and go Left (L) until there is no further L node. Such a leaf node will be the successor.
        while (succ.left is not null):
          succpar:=succ;
          succ:=succ.left;
          i:=i+1;
        Store succ.data as another object, say in succ_data;
        if (succpar==root): there was no L, we have moved only R once
          if (succ.right is not null): successor has a right child
            succpar.right:=succ.right && i:=i+1;
          else: successor has no right node (leaf)
            succpar.right:=null;
          (Note: the successor can be a leaf node (no children) or can have Right child only. If it had an left child, then we should have gone further Left)
        else if (succ.right is not null): This is when there is at least on Left child after the Right child of the deleting node
          succpar.left:=succ.right && i:=i+1;
        else : succ.right is null
          succpar.left:=null;
        Replacing the deleting node's data with the successor's data, 
        if key<parent.key: parent.left.data:=succ_data; #Just copying the data, not changing left/right
        else : parent.right.data:=succ_data;

    return i;
    

  > boolean contains(K key)
    hash=djb2(key);
    Set root=Arr[hash];
    while (root is not null):
      if key==root.key => given key found in BST:
        return true;
      if key<root.key: root=root.left (We have to move to the left as given key value < current key);
      else : root=root.right (We have to move to the right as given key value >= current key);
    Loop terminates when we have reached the last node
    if this line is reached => required key not found in BST => return false;
    

  > T get(K key)
    hash=djb2(key);
    Set root=Arr[hash];
    while (root is not null):
      if key==root.key => required key found in BST:
        return required object in root;
      if key<root.key: root=root.left (We have to move to the left as given key value < current key);
      else : root=root.right (We have to move to the right as given key value >= current key);
    Loop terminates when we have reached the last node
    if this line is reached => required key not found in BST => throw NotFoundException;


  > String address(K key)
    hash=djb2(key);
    Set root=Arr[hash];
    String Address=String(hash)+'-'; (the index is added first)
    while (root is not null):
      if key==root.key => required key found in BST:
        return Address;
      if key<root.key: 
        root=root.left (We have to move to the left as given key value < current key) && append 'L' to Address;
      else : 
        root=root.right (We have to move to the right as given key value >= current key) && append 'R' to Address ;
    Loop terminates when we have reached the last node
    if this line is reached => required key not found in BST => throw NotFoundException;
    



> public class assignment3
  The capacity, hashing method (DH/SCBST) and input file location is taken as space seperated command-line arguments. The input file is first opened from arg[2]. 
  If hashing method (arg[1]) is DH, an object of MyHashTableForDH (named Table) with the table size capacity (arg[0]). Each line in input file is read. Each line is splitted at whitespaces using the split() function. If the command is insert, then Table.insert() is called with the key and object made using the suceeding words in the input and key as arguments. The number of steps taken is returned by the function as is printed as it is if it is not -1 or "E" if -1. Similarly, if the command is update, Table.update() is called accordingly. delete, contains, address and get commands also work in a similar way calling corresponding methods. In case of contains, if the object is found, "T" gets printed else "F" if not found. In case of address, the index is printed if found or "E" if NotFoundException is thrown. For get, the Student object's returned toString() value is printed if the object exists or "E" if NotFoundException is thrown.
  If hashing method (arg[1]) is SCBST, then object of MyHashTableForSC (named Table is made) with the table size capacity (arg[0]), instead of MyHashTableForDH. Other commands have similar effect, i.e., similar functions are called. However, the insert, update, delete command gives the number of Nodes touched while address prints output in <index-bstseq> format (as described in the assignment)
  "E" is printed in case of wrong commands or wrong hashing method in command-line argument or any other exception(s) (like IOException, FileNotFoundException, etc.) is/are thrown.


+-----------------------------------------------+
| TIME COMPLEXITY ANALYSIS                      |
+-----------------------------------------------+
> Double hashing
  { n is the HashTable size; 
    Define: load factor 'a' = min((total no. of objects added)/(size of hash table),1); }

  +===============================================+
  |           |  Best  |   Average   |    Worst   |
  +===============================================+
  | insert    |  O(1)  |  O(1/(1-a)) |    O(n)    |
  +-----------------------------------------------+
  | update    |  O(1)  |  O(1/(1-a)) |    O(n)    |
  +-----------------------------------------------+
  | delete    |  O(1)  |  O(1/(1-a)) |    O(n)    |
  +-----------------------------------------------+
  | contains  |  O(1)  |  O(1/(1-a)) |    O(n)    |
  +-----------------------------------------------+
  | get       |  O(1)  |  O(1/(1-a)) |    O(n)    |
  +-----------------------------------------------+
  | address   |  O(1)  |  O(1/(1-a)) |    O(n)    |
  +===============================================+

  EXPLANATION:
   -  Best case time complexity is when each object can be inserted after the computation of first hash function itself (i.e., there is no need of second hash function to be computed as there is no collision) or in other terms, the hash calculated doesn't result in collision at the first time itself. In such a sitiuation we expect the time complexity to be constant,i.e., O(1)
   -  In an average case, if a is the load factor, which says the probability that there will be a collision, then 1/(1-a) loosely gives the time taken for doing an operation to complete (taking into account the probability of unsuccesful operations due to collision into account).
   -  Worst case is when the hash functions are so inefficient that it looks as if we have to go through every index (not a small constant anymore to be ignored; also looks as if it depends linearly on n (table size)) to resolve collision before completing the operation. So it is O(n).  



> Seperate Chaining (using BSTs)
  { n is total number of objects added; 
    h is height of a BST;
    m is total number of nodes in a given BST;
    Define: load factor 'a' = n/(size of hash table); }
  
  +===============================================+
  |           |    Best    |  Average  |   Worst  |
  +===============================================+
  | insert    | O(log_2 a) |   O(h)    |   O(n)   |
  +-----------------------------------------------+
  | update    | O(log_2 a) |   O(h)    |   O(n)   |
  +-----------------------------------------------+
  | delete    | O(log_2 a) |   O(h)    |   O(n)   |
  +-----------------------------------------------+
  | contains  | O(log_2 a) |   O(h)    |   O(n)   |
  +-----------------------------------------------+
  | get       | O(log_2 a) |   O(h)    |   O(n)   |
  +-----------------------------------------------+
  | address   | O(log_2 a) |   O(h)    |   O(n)   |
  +===============================================+  
  
   ** Usually, expected h = log_2 m. We can also say h = log_2 a on an average.

  EXPLANATION:
   -  In all the cases & operations, the computation of hash is O(1) (constant) and can be ignored as it has no effect when combined with other operations, here.
   -  Best case time complexity is when each slot (index) forms a balanced BST, which means n inserted objects are equally divided among each slot and each such slot possesses a balanced BST. So O(log_2 a).
   -  On an average case, we expect the operations to depend on height of the tree.
   -  Worst case is when everything is dumped into a single slot (single BST) and everyone has only one child (everyone gets arranged in a single straight chain without branches). So it is obviously O(n).
   -  In case of deleting, for finding successor, it is still O(h)--average (or accordingly), and hence its still O(h),i.e., finding successor doesn't change the class to which this method belongs to.


+-----------------------------------------------+
| INTERESTING FINDINGS                          |
+-----------------------------------------------+
- In Double Hashing, only fixed number of elements could be added while in Seperate Chaining using BSTs there are no such restrictions. So more space has to be allocated in Double Hashing (so even if null; that space is reserved), while in Seperate chaining, a hash table of small size will also do and space allocated insitially can be preserved.
- Double Hashing becomes inefficient if size isn't prime (sometimes you won't be able to insert an object even if table has empty indices as it will be looping through the same indices again and again) or useless if the second hash function returns 0 (looped through the same index infinitely (until terminated)). So it must be made sure that the second hash function never returns zero. A single hash function is sufficient for Seperate chaining which reduces the number of computations, in loose terms.
- Seperate chaining becomes inefficient when all the objects are added to a single 'slot'. The load factor is too high that it's equivalent to maintain just a BST (the purpose of hashing is defeated). While Double Hashing has a relatively better distribution, in terms of load in a slot but yeah-it's either yes or no!
- In case of Balanced BST, queries in SCBST is usually faster than DH as the time taken to reach the last node added (n) would be O(height) (height=logn-balanced BSTs) and O(n) respectively. (Hope what I'm trying to say is clear)
- deleting is the most difficult part of implementing a BST!!

Both have their advantages and disadvantages For ex, The tester can try to manipulate and load all the objects in one slot of SCBST. However, in my opinion, SCBST has a upper hand over DH as the number of elements that could be added is not fixed.


+===============================================+
