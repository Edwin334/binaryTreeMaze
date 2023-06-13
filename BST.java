package project5;
import java.util.*; 

public class BST < T extends Comparable <T> > {

    protected BSTNode root;   //reference to the root node of the tree 
    private Comparator<T> comparator;   //comparator object to overwrite the 
                               //natural ordering of the elements 
    	
	private boolean found;  //helper variable used by the remove methods
    private boolean added ; //helper variable used by the add method 

    private int size; 
    /**
	 * Constructs a new, empty tree, sorted according to the natural ordering of its elements.
	 */
    public BST () {
        root = null; 
        comparator = null; 
    }

    /**
	 * Constructs a new, empty tree, sorted according to the specified comparator.
	 */
    public BST(Comparator<T> comparator) {
        this.root = null; 
        this.comparator = comparator;
    }

	/**
	 * Adds the specified element to this tree if it is not already present. 
	 * If this tree already contains the element, the call leaves the 
     * tree unchanged and returns false.
	 * @param data element to be added to this tree 
     * @return true if this tree did not already contain the specified element 
     * @throws NullPointerException if the specified element is null  
	 */
    public boolean add ( T data ) { 
         added = false; 
         if (data == null) throw new NullPointerException ("null value found"); 
         //replace root with the reference to the tree after the new 
         //value is added
         root = add (data, root);
         if (added) size++; 
         return added; 
    }
    /*
	 * Actual recursive implementation of add. 
     *
     * This function returns a reference to the subtree in which 
     * the new value was added. 
	 *
     * @param data element to be added to this tree 
     * @param node node at which the recursive call is made 
	 */
    private BSTNode add (T data, BSTNode node ) {
        if (node == null) {
            added = true; 
            return new BSTNode(data); 
        }
        //decide how comparisons should be done 
        int comp = 0 ;
        if (comparator == null ) //use natural ordering of the elements 
            comp = data.compareTo(node.data); 
        else                     //use the comparator 
            comp = comparator.compare(data, node.data ) ;


        //find the location to add the new value  
        if (comp < 0 ) { //add to the left subtree 
            node.left = add(data, node.left); 
        }
        else if (comp > 0 ) { //add to the right subtree
            node.right = add(data, node.right); 
        }
        else { //duplicate found, do not add 
            added = false; 
            //return node; 
        }
        return node; 
    }

    /**
	 * Removes the specified element from this tree if it is present. 
	 * Returns true if this tree contained the element (or equivalently, 
     * if this tree changed as a result of the call). 
     * (This tree will not contain the element once the call returns.)
	 * @param target object to be removed from this tree, if present
     * @return true if this set contained the specified element 
     * @throws NullPointerException if the specified element is null  
	 */
	public boolean remove(T target)
	{
        //replace root with a reference to the tree after target was removed 
		root = recRemove(target, root);
        if (found) size--; 
		return found;
	}


	/*
	 * Actual recursive implementation of remove method: find the node to remove.
     *
	 * This function recursively finds and eventually removes the node with the target element 
     * and returns the reference to the modified tree to the caller. 
     * 
	 * @param target object to be removed from this tree, if present
     * @param node node at which the recursive call is made 
	 */
	private BSTNode recRemove(T target, BSTNode node)
	{
		if (node == null)  { //value not found 
			found = false;
            return node; 
        }
        
        //decide how comparisons should be done 
        int comp = 0 ;
        if (comparator == null ) //use natural ordering of the elements 
            comp = target.compareTo(node.data); 
        else                     //use the comparator 
            comp = comparator.compare(target, node.data ) ;

        
		if (comp < 0)       // target might be in a left subtree 
			node.left = recRemove(target, node.left);
		else if (comp > 0)  // target might be in a right subtree 
			node.right = recRemove(target, node.right );
		else {          // target found, now remove it 
			node = removeNode(node);
			found = true;
		}


		return node;
	}

	/*
	 * Actual recursive implementation of remove method: perform the removal.
	 *
	 * @param target the item to be removed from this tree
	 * @return a reference to the node itself, or to the modified subtree
	 */
	private BSTNode removeNode(BSTNode node)
	{
		T data;
		if (node.left == null)   //handle the leaf and one child node with right subtree 
			return node.right ; 
		else if (node.right  == null)  //handle one child node with left subtree 
			return node.left;
		else {                   //handle nodes with two children 
			data = getPredecessor(node.left);
			node.data = data;

			node.left = recRemove(data, node.left);
            
			return node;
		}
	}

	/*
	 * Returns the information held in the rightmost node of subtree
	 *
	 * @param subtree root of the subtree within which to search for the rightmost node
	 * @return returns data stored in the rightmost node of subtree
	 */
	private T getPredecessor(BSTNode subtree)
	{
		if (subtree==null) //this should not happen 
            throw new NullPointerException("getPredecessor called with an empty subtree");
		BSTNode temp = subtree;
		while (temp.right  != null)
			temp = temp.right ;
		return temp.data;
	}


	/**
	 * Returns the number of elements in this tree.
	 * @return the number of elements in this tree
	 */
	public int size() {
        

		return size; 
    }


    public String toStringTree( ) {
        StringBuffer sb = new StringBuffer(); 
        toStringTree(sb, root, 0);
        return sb.toString();
    }

    //uses preorder traversal to display the tree 
    //WARNING: will not work if the data.toString returns more than one line 
    private void toStringTree( StringBuffer sb, BSTNode node, int level ) {
        //display the node 
        if (level > 0 ) {
            for (int i = 0; i < level-1; i++) {
                sb.append("   ");
            }
            sb.append("|--");
        }
        if (node == null) {
            sb.append( "->\n"); 
            return;
        }
        else {
            sb.append( node.data + "\n"); 
        }
        
        //display the left subtree 
        toStringTree(sb, node.left, level+1); 
        //display the right subtree 
        toStringTree(sb, node.right, level+1); 
    }

    public ArrayList<T> toArray () {
        if (root == null ) return null;
        ArrayList<T> list = new ArrayList<T> ();
        inorder (list, root);
        return list; 
    }

    private void inorder( ArrayList<T> list, BSTNode node ) {
        if (node == null ) return; 
        //go left 
        inorder(list, node.left); 
        //process the node 
        list.add( node.data ) ; 
        //go right 
        inorder(list, node.right ) ; 

    }


    public boolean equals (Object obj ) {
        // verify if obj is null 
        if (obj == null ) return false; 
        // check if obj is equal to this 
        if (obj == this ) return true; 
        //check if obj is a BST 
        if (! (obj instanceof BST) ) return false ; 
        //cast obj to BST type 
        BST <?> bst = (BST <?>) obj; 
        //iterate through both trees and compare elements 
        //one by one 
        //check the size 
        if (this.size() != bst.size() ) return false; 
        //get lists for both trees 
        ArrayList<T> thisList = this.toArray();
        ArrayList<?> bstList = bst.toArray();
        for ( int i = 0; i < thisList.size(); i++ ) {
            if ( !thisList.get(i).equals(bstList.get(i))  ) {
                return false;
            }
        }

        return true; 
    }


    public Iterator<T> iterator () {
        return new MyIterator(); 
    }

    private class MyIterator implements Iterator<T> {
        ArrayList<T> list; 
        int current; 
        MyIterator () {
            list = BST.this.toArray();
            current = 0;  
        }

        public boolean hasNext(){
            return current < list.size();
        }

        public T next() {
            return list.get(current++); 
        }
    }


    /* 
     * Node class for this BST 
     */ 
    class BSTNode implements Comparable < BSTNode > {

        T data;
        BSTNode  left;
        BSTNode  right;

        public BSTNode ( T data ) {
            this.data = data;
        }

        public BSTNode (T data, BSTNode left, BSTNode right ) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public int compareTo ( BSTNode other ) {
            if (BST.this.comparator == null )
                return this.data.compareTo ( other.data );
            else 
                return comparator.compare(this.data, other.data); 
        }
    }

    
}

