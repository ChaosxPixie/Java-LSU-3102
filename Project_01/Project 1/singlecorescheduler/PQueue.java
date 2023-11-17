package singlecorescheduler;


import java.util.*;

/**
 * This class models a priority min-queue that uses an array-list-based min binary heap
 * that implements the PQueueAPI interface. The array holds objects that implement the
 * parameterized Comparable interface.
 * @author Duncan, Malana Fuentes
 * @param <E> the priority queue element type.
 * @author William Duncan
 * <pre>
 * Date: 9/21/22
 * Course: csc 3102
 * Programming Project # 1
 * Instructor: Dr. Duncan
 * </pre>
 */
public class PQueue<E extends Comparable<E>> implements PQueueAPI<E>
{
    /**
     * A complete tree stored in an array list representing the
     * binary heap
     */
    private ArrayList<E> tree;
    /**
     * A comparator lambda function that compares two elements of this
     * heap when rebuilding it; cmp.compare(x,y) gives 1. negative when x less than y
     * 2. positive when x greater than y 3. 0 when x equal y
     */
    private Comparator<? super E> cmp;

    /**
     * Constructs an empty PQueue using the compareTo method of its data type as the
     * comparator
     */
    public PQueue()
    {
        //implement this method

        //initialize ArrayList & compare objects
        tree = new ArrayList<>();
        cmp = (obj1, obj2) -> obj1.compareTo(obj2);
    }

    /**
     * A parameterized constructor that uses an externally defined comparator
     * @param fn - a trichotomous integer value comparator function
     */
    public PQueue(Comparator<? super E> fn)
    {
        // implement this method
        tree = new ArrayList<>();
        fn = cmp;
    }

    public boolean isEmpty()
    {
        // implement this method
        // return false;
        return tree.isEmpty();
    }

    public void insert(E obj)
    {
        //implement this method
        tree.add(obj);
        int index = tree.size()-1;
        while (index > 0 && cmp.compare(tree.get(index), tree.get(parent(index))) >0 ){
                swap(index, parent (index));
                index = parent (index);
        }
    }

    public E remove() throws PQueueException {
        //implement this method
        if (tree.isEmpty()) {
            throw new PQueueException("Priority Queue is Empty");
        }
            E obj = tree.get(0);
            int lastIndex = tree.size() - 1;
            tree.set(0, tree.get(lastIndex));
            tree.remove(lastIndex);
            rebuild(0, tree.size());
            return obj;
    }

    public E peek() throws PQueueException
    {
        //implement this method
        if (tree.isEmpty()){
            throw new PQueueException("Priority Queue is Empty");
        }
        return tree.get(0);
        //return null;
    }

    public int size()
    {
        //implement this method
        return tree.size();
        //return 0;
    }

    /**
     * Swaps a parent and child elements of this heap at the specified indices
     * @param place an index of the child element on this heap
     * @param parent an index of the parent element on this heap
     */
    private void swap(int place, int parent)
    {
        //implement this method
        E temp = tree.get(parent);
        tree.set(parent, tree.get(place));
        tree.set(place, temp);
    }

    /**
     * Rebuilds the heap to ensure that the heap property of the tree is preserved.
     * @param root the root index of the subtree to be rebuilt
     * @param eSize the size of this tree
     */
    private void rebuild(int root, int eSize) {
        //implement this method
        int left = left(root);
        int right = right(root);
        int smallest = root;
        if (left <= eSize - 1 && cmp.compare(tree.get(left), tree.get(root)) > 0) {
            smallest = left;
        }
        if (right <= eSize - 1 && cmp.compare(tree.get(right), tree.get(smallest)) > 0) {
            smallest = right;
        }
        if (smallest != root) {
            swap(root, smallest);
            rebuild(smallest, eSize);
        }
    }
        private int parent (int index){
            return (index - 1) /2;
        }
        private int left (int index) {
            return 2 * index + 1;
        }
        private int right (int index) {
            return 2 * index + 2;
        }
    }

