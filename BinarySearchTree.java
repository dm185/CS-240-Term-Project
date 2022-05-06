
public class BinarySearchTree {
	
	class Node{
		Address address;
		Node left, right;
		
		public Node(Address newAddress) {
			address = newAddress;
			left = right = null;
		}	
	}
	
	Node root;
	
	//root node constructor
	BinarySearchTree(){root=null;}

	void insertAddress(Address key) {
		root = insert_Recursive(root, key);
	}
	
	//recursive algorithm using toString from address with compareTo to lexagraphically sort the tree
	Node insert_Recursive(Node root, Address key) {
		if(root == null) {
			root = new Node(key);
			return root;
		}

		//key is lexagraphically smaller, returning -1 (smaller being earlier alphabetically)
		if (key.toString().compareTo(root.address.toString()) < 0) {
			root.left = insert_Recursive(root.left, key);
		}
		
		//key is lexagraphically bigger, returning 1 (bigger being later alphabetically)
		else if (key.toString().compareTo(root.address.toString()) > 0) {
			root.right = insert_Recursive(root.right, key);
		}
		return root;
	}
	
	
	void deleteAddress(Address key) {
		root = delete_Recursive(root, key);
	}
	
    Node delete_Recursive(Node root, Address key) {
    	if (root == null) return root;
    	
    	//key is lexagraphically smaller, returning -1 (smaller being earlier alphabetically)
    	if(key.toString().compareTo(root.address.toString()) < 0){
    		root.left = delete_Recursive(root.left, key);
    	}
    	//key is lexagraphically bigger, returning 1 (bigger being later alphabetically)
    	else if (key.toString().compareTo(root.address.toString()) > 0){
    		root.right = delete_Recursive(root.right, key);
    	} 
    	else {
    		if(root.left == null) {
    			return root.right;
    		}
    		if(root.right == null) {
    			return root.left;
    		}
    		
    		root.address = minValue(root.right);
    		root.right = delete_Recursive(root.right, root.address);
    	}
    	return root;
    }
    
    Address minValue(Node root) {
    	Address minval = root.address;
    	while (root.left != null) {
    		minval = root.left.address;
    		root = root.left;
    	}
    	return minval;
    }
	
    //Prints the elements alphabetically
    void inorderPrint() {
    	inorder_Recursive(root);
    	System.out.println();//move to next line at end of the method call
    }
    
    void inorder_Recursive(Node root) {
    	if (root != null) {
    		inorder_Recursive(root.left);
    		System.out.print(root.address.toString() + " ");
    		inorder_Recursive(root.right);
    	}
    }
    
    boolean search(Address key) {
    	root = search_Recursive(root, key);
    	if(root != null) return true;
    	else return false;
    }
    
    Node search_Recursive(Node root, Address key) {
    	if (root == null || key.toString().compareTo(root.address.toString()) == 0) return root;
    	if (key.toString().compareTo(root.address.toString()) < 0) {
    		return search_Recursive(root.left, key);
    	}
    	return search_Recursive(root.right, key);
    }
	
}
