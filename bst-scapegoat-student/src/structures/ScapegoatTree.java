package structures;

import java.util.Iterator;

public class ScapegoatTree<T extends Comparable<T>> extends
		BinarySearchTree<T> {
	private int upperBound;


	
	@Override
	public void add(T t) {
		// TODO
		if(isEmpty()) upperBound =0;
		super.add(t);
		upperBound++;
		double hc =  Math.log(upperBound) / Math.log(1.5);
		BSTNode<T> n = root;
		BSTNode<T> child;
		BSTNode<T> parent = n;
		double compare;
		if (hc > height()) {
			while(!n.getData().equals(t)) {
				int c = t.compareTo(n.getData());
				child = (c>0)? n.getRight() : n.getLeft();
				compare = subtreeSize(child) / subtreeSize(n);
				if(compare <= 2/3) break;
				parent = n;
				n = child;
			}
			
			BinarySearchTree<T> bst = new BinarySearchTree<T>();
			bst.root = parent;
		
		}
	}
	private BSTNode<T> getParent(BSTNode<T> n){
		BSTNode<T> temp = root;
		while(temp.getLeft() != n || temp.getRight() != n) {
			int compare = n.getData().compareTo(temp.getData());
			if(compare > 0) temp = temp.getRight();
			else temp = temp.getLeft();
		}
		return temp;
	}

	@Override
	public boolean remove(T t) {
		if (t == null)throw new NullPointerException();
		if (!contains(t)) return false;
		
		root = removeFromSubtree(root, t);
		double temp = (double)upperBound;
		if( temp/2 > size() || size() > temp) {
			balance();
			upperBound = size();
		}
		return true;
	}

	
}
