package main;

/**
 * The following is a union find data structure implementation.
 * 
 * @author syed.umer.ahmed.code@gmail.com
 *
 */
public class UnionFind {

	// The number of elements in this union find
	private int size;

	// Used to track the sizes of each of the component.
	private int[] sz;

	// id[i] points to parent of id, if [id]=i then i is the root node
	private int[] id;

	// Tracks the number of components in the union find
	private int numberOfComponents;

	public UnionFind(int size) {

		throwErrorIfSizeInputIsLessThanOne(size);

		// Initial setup where number of elements in union find and the number of
		// components in the union find are equal
		this.size = numberOfComponents = size;
		// Size of the array is equal to given size
		sz = new int[size];
		// Size of the indexes to maintain is equal to given size
		id = new int[size];

		for (int i = 0; i < size; i++) {
			// Link to itself(self node)
			id[i] = i;
			// Each component is originally of size 1
			sz[i] = 1;
		}
	}

	/**
	 * Find which component/set 'p' belongs to, takes amortized constant time.
	 * 
	 * @param inputElement
	 * @return
	 */
	public int find(int inputElement) {

		int root = findRootOfInputElement(inputElement);

		compressPathTillRoot(inputElement, root);
		return root;
	}

	/**
	 * Return whether or not the elements are in the same components/set. In other
	 * words, it checks if they have the same root.
	 * 
	 * @param firstElement
	 * @param secondElement
	 * @return
	 */
	public boolean connected(int firstElement, int secondElement) {
		return find(firstElement) == find(secondElement);
	}

	/**
	 * Returns the size of the component/set, the input element belongs to.
	 * 
	 * @param elemenntToCheck
	 * @return
	 */
	public int componentSize(int elemenntToCheck) {
		return sz[find(elemenntToCheck)];
	}

	/**
	 * Returns the number of elements in this Union find/disjoint set.
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns the number of remaining components.
	 * 
	 * @return
	 */
	public int numberOfComponents() {
		return numberOfComponents;
	}

	public void unify(int firstElement, int secondElement) {

		int firstRoot = find(firstElement);
		int secondRoot = find(secondElement);

		if (firstRoot == secondRoot) {
			// These elements are already in the same group.
			return;
		}

		mergeSmallerComponentIntoLargerComponent(firstRoot, secondRoot);

		// Since the root are different and an alignment has taken place, we decrease
		// the number of components by 1
		numberOfComponents--;
	}

	private void throwErrorIfSizeInputIsLessThanOne(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("size<=0 is not allowed.");
		}
	}

	/**
	 * Merge the two components/sets together. Merge smaller component/set into the
	 * larger one
	 * 
	 * @param firstRoot
	 * @param secondRoot
	 */
	private void mergeSmallerComponentIntoLargerComponent(int firstRoot, int secondRoot) {
		if (sz[firstRoot] < sz[secondRoot]) {
			sz[secondRoot] += sz[firstRoot];
			id[firstRoot] = secondRoot;
		} else {
			sz[firstRoot] += sz[secondRoot];
			id[secondRoot] = firstRoot;
		}
	}

	private int findRootOfInputElement(int elementToFind) {
		// Find the root of the component
		int root = elementToFind;
		// loop through till we have not reached the root
		while (root != id[root]) {
			root = id[root];
		}
		return root;
	}

	/**
	 * Compress the path leading to the root. This is called 'path compression' This
	 * operation gives amortized constant time complexity
	 * 
	 * @param elementToFind
	 * @param root
	 */
	private void compressPathTillRoot(int elementToFind, int root) {
		while (elementToFind != root) {
			int parent = id[elementToFind];
			id[elementToFind] = root;
			elementToFind = parent;

		}
	}

}
