package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;
	
	private BSTNode<T> temp;
	
	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	protected int subtreeSize(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + subtreeSize(node.getLeft())
					+ subtreeSize(node.getRight());
		}
	}

	public boolean contains(T t) {
		// TODO
		if(t == null)throw new NullPointerException();
		BSTNode<T> node = root;
		int val;
		while (node != null) {
			val = t.compareTo(node.getData());
			if(val == 0)return true;
			if(val > 0)node = node.getRight();
			else node = node.getLeft();
		}
		return false;
	}
	

	public boolean remove(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
		}
		return result;
	}

	protected BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				node.setData(predecessorValue);
				return node;
			}
		}
	}

	protected T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	protected BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			return node;
		}
	}

	public T get(T t) {
		// TODO
		if(t == null) throw new NullPointerException();
		if(!contains(t)) return null;
		return t;

	}

	public void add(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		root = addToSubtree(root, new BSTNode<T>(t, null, null));
	}

	protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
		if (node == null) {
			return toAdd;
		}
		int result = toAdd.getData().compareTo(node.getData());
		if (result <= 0) {
			node.setLeft(addToSubtree(node.getLeft(), toAdd));
		} else {
			node.setRight(addToSubtree(node.getRight(), toAdd));
		}
		return node;
	}

	@Override
	public T getMinimum() {
		// TODO
		if(isEmpty()) return null;
		temp = root;
		while(temp.getLeft() != null) temp = temp.getLeft();
		return temp.getData();
	}
	
	

	@Override
	public T getMaximum() {
		// TODO
		if(isEmpty()) return null;
		temp = root;
		while(temp.getRight() != null) temp = temp.getRight();
		return temp.getData();
		
	}


	@Override
	public int height() {
		// TODO
		if(isEmpty()) return -1;
		return height(root);
	}
	private int height(BSTNode<T> node){
		if(node == null) return -1;
		int right = height(node.getRight());
		int left = height(node.getLeft());
		return (right > left)? right + 1 : left + 1;
	}

	public Iterator<T> preorderIterator() {
		// TODO
		Queue<T> q = new LinkedList<T>();
		preorderTraverse(q, root);
		return q.iterator();
	}
	private void preorderTraverse(Queue<T> q, BSTNode<T> n) {
		if (n != null) {
			q.add(n.getData());
			preorderTraverse(q, n.getLeft());
			preorderTraverse(q, n.getRight());
			
		}
	}


	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}


	private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> postorderIterator() {
		// TODO
		Queue<T> q = new LinkedList<T>();
		postorderTraverse(q, root);
		return q.iterator();
	}
	private void postorderTraverse(Queue<T> q, BSTNode<T> n) {
		if(n != null) {
			postorderTraverse(q, n.getLeft());
			postorderTraverse(q, n.getRight());
			q.add(n.getData());
		}
		
	}


	@Override
	public boolean equals(BSTInterface<T> other) {
		// TODO
		if(other == null) throw new NullPointerException();
		return equals(other, root, other.getRoot());
	}
	private boolean equals(BSTInterface<T> other, BSTNode<T> n1, BSTNode<T> n2) {
		if(n1 == null && n2 == null) return true;
		if(n1 == null || n2 == null || n1.getData() == null || n2.getData() == null) return false;
		if(!n1.getData().equals(n2.getData()))return false;
		return equals(other, n1.getLeft(), n2.getLeft()) && equals(other, n1.getRight(), n2.getRight());
	}

	@Override
	public boolean sameValues(BSTInterface<T> other) {
		// TODO
		for(Iterator<T> i = inorderIterator(); i.hasNext();) {
			if(!other.contains(i.next()))return false;
		}
		if(other.size() != size()) return false;
		return true;
	}

	@Override
	public boolean isBalanced() {
		// TODO
		return Math.pow(2, height()) <= size() && size() < Math.pow(2, height() + 1);
	}
	@Override
    @SuppressWarnings("unchecked")

	public void balance() {
		// TODO
		T[] arr = inorderArray();
		root = sortedArray2BST(0, size()-1, arr);
		
	}
	protected BSTNode<T> sortedArray2BST(int lower, int upper, T[] array) { 
		if (lower > upper)return null;
		int mid = (lower + upper) / 2; 
		BSTNode<T> node = new BSTNode<T>(array[mid], null, null);
		node.setLeft(sortedArray2BST(lower, mid - 1, array));
    	node.setRight(sortedArray2BST(mid + 1, upper, array));
    	return node;
}
	@SuppressWarnings("unchecked")
	protected T[] inorderArray() {
		T[] arr = (T[]) new Comparable[size()];
		int index = 0;
		for(Iterator<T> i= inorderIterator(); i.hasNext();) {
			arr[index++] = i.next();
		}
		return arr;
	}

	@Override
	public BSTNode<T> getRoot() {
        // DO NOT MODIFY
		return root;
	}
	

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}

	public static void main(String[] args) {
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			BSTInterface<String> tree = new BinarySearchTree<String>();
			for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
				tree.add(s);
			}
			Iterator<String> iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.preorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.postorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();

			System.out.println(tree.remove(r));

			iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
		}

		BSTInterface<String> tree = new BinarySearchTree<String>();
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			tree.add(r);
		}
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
		tree.balance();
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
	}
}