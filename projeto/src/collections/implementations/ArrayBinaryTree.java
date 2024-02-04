package collections.implementations;


import collections.exceptions.ElementNotFoundException;
import collections.exceptions.EmptyCollectionException;
import collections.interfaces.BinaryTreeADT;
import java.util.Iterator;


public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {

    protected int count;
    protected T[] tree;
    private final int CAPACITY = 50;

    /**
     * Creates an empty binary tree.
     */
    public ArrayBinaryTree() {
        count = 0;
        tree = (T[]) new Object[CAPACITY];
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element which will become the root of the new tree
     */
    public ArrayBinaryTree(T element) {
        count = 1;
        tree = (T[]) new Object[CAPACITY];
        tree[0] = element;
    }

    @Override
    public T getRoot() {
        return tree[0];
    }
    
    protected T getNode(int index) {
        return tree[index];
    }


    @Override
    public boolean isEmpty() {
        return (count == 0);
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean contains(T targetElement) {
        try {
            find(targetElement);
        } catch (ElementNotFoundException | EmptyCollectionException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree.Throws a NoSuchElementException if the specified target
     * element is not found in the binary tree.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the element is in the tree
     * @throws ElementNotFoundException if an element not found exception occurs
     * @throws EmptyCollectionException
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException, EmptyCollectionException {
        if (targetElement == null) {
            throw new ElementNotFoundException("elemento null");
        }
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty");
        }
        T temp = null;
        boolean found = false;

        for (int ct = 0; ct < count && !found; ct++) {
            if (targetElement.equals(tree[ct])) {
                found = true;
                temp = tree[ct];
            }
        }
        if (!found) {
            throw new ElementNotFoundException("binary tree");
        }
        return temp;
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an iterator over the binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        inorder(0, tempList);
        return tempList.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node used in the traversal
     * @param tempList the temporary list used in the traversal
     */
    protected void inorder(int node, ArrayUnorderedList<T> tempList) {
        if (node < tree.length) {
            if (tree[node] != null) {
                inorder(node * 2 + 1, tempList);
                tempList.addToRear(tree[node]);
                inorder((node + 1) * 2, tempList);
            }
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        preorder(0, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive preorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list used in the traversal
     *
     */
    protected void preorder(int node,
            ArrayUnorderedList<T> tempList) {
        if (node < tree.length) {
            if (tree[node] != null) {
                tempList.addToRear(tree[node]);
                preorder(node * 2 + 1, tempList);
                preorder((node + 1) * 2, tempList);
            }
        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        postorder(0, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive preorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void postorder(int node,
            ArrayUnorderedList<T> tempList) {
        if (node < tree.length) {
            if (tree[node] != null) {
                postorder(node * 2 + 1, tempList);
                postorder((node + 1) * 2, tempList);
                tempList.addToRear(tree[node]);
            }
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        int i = 0;
        int j = 0;
        //array to store the elements in correct order of iteration
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        while (i < count) {
            if (tree[j] != null) {
                tempList.addToRear(tree[j]);
                i++;
            }
            j++;
        }
        return tempList.iterator();
    }

//    @Override
//    public Iterator<T> iteratorLevelOrder() {
//        //array to store the elements in correct order of iteration
//        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
//        //queue of the indexes of the elements of the tree to control the ordering by levels
//        LinkedQueue<Integer> queue = new LinkedQueue<>();
//
//        //if the root element is not null
//        if (tree[0] != null) {
//            //add the index of the element to queue
//            queue.enqueue(0);
//            //while the queue is not empty
//            while (!queue.isEmpty()) {
//                //get  the node index at the head of the queue
//                try {
//                    int node = queue.dequeue();
//                    // visit this node in the tree using dequeued index
//                    tempList.addToRear(tree[node]);
//
//                    if ((node + 1) * 2 < tree.length) {
//                        //if this node has children enqueue them in left to right order
//                        //left child index is found using tge node index times 2 plus 1
//                        if (tree[node * 2 + 1] != null) {
//                            queue.enqueue(node * 2 + 1);
//                        }
//
//                        //right child index is found using the node index plus 1, times 2 
//                        if (tree[(node + 1) * 2] != null) {
//                            queue.enqueue((node + 1) * 2);
//                        }
//                    }
//                } catch (EmptyCollectionException ex) {
//                }
//            }
//        }
//        return tempList.iterator();
//    }
}
