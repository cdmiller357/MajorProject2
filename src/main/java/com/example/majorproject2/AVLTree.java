package com.example.majorproject2;

import java.util.ArrayList;
import java.util.Comparator;

/** Main AVLTree algorithm from book.
 *  Changed to use one class without inheritance/interfaces.
 *  Added a getAVLTreeNode Method which returns a AVLTreeNode<E>.
 *  Methods not included: search, add, delete, inorder, preorder, postorder, getSize, getRoot,containsAll, addAll, removeAll, retainAll, toArray.
 *  Iterator not implemented(yet?).
 */

public class AVLTree<E> {
    protected AVLTreeNode<E> root;
    protected Comparator<E> c;

    /** Create an empty AVL tree using a natural comparator*/
    public AVLTree() {
        this.c = (e1, e2) -> ((Comparable<E>)e1).compareTo(e2);
    }


    /** Create a AVL tree with a specified comparator */
    public AVLTree(Comparator<E> c) {
        this.c = c;
    }


    /** Create an AVL tree from an array of objects */
    public AVLTree(E[] objects) {
        this.c = (e1, e2) -> ((Comparable<E>)e1).compareTo(e2);
        for (int i = 0; i < objects.length; i++)
            insert(objects[i]);
    }
    /** Not implemented(yet?) - Create an AVL tree from an array of objects with a specified comparator*/


    //Create an AVLTreeNode
    protected AVLTreeNode<E> createNewNode(E e) {
        return new AVLTreeNode<E>(e);
    }


    public AVLTreeNode<E>  getAVLTreeNode(E e) {
        AVLTreeNode<E> current = root; // Start from the root

        while (current != null) {
            if (c.compare(e, current.element) < 0) {
                current = current.left;
            }
            else if (c.compare(e, current.element) > 0) {
                current = current.right;
            }
            else // element matches current.element
                return (new AVLTreeNode<E>(current.element)); // Element is found
        }
        return new AVLTreeNode(null); //returns null node if not found
    }


    //@Override /** Insert element e into the binary tree
    /* Return true if the element is inserted successfully */
    public boolean insert(E e) {
        if (root == null)
            root = createNewNode(e); // Create a new root
        else {
            // Locate the parent node
            AVLTreeNode<E> parent = null;
            AVLTreeNode<E> current = root;
            while (current != null)
                if (c.compare(e, current.element) < 0) {
                    parent = current;
                    current = current.left;
                }
                else if (c.compare(e, current.element) > 0) {
                    parent = current;
                    current = current.right;
                }
                else
                    return false; // Duplicate node not inserted

            // Create the new node and attach it to the parent node
            if (c.compare(e, parent.element) < 0)
                parent.left = createNewNode(e);
            else
                parent.right = createNewNode(e);
        }
        balancePath(e); // Balance from e to the root if necessary
        return true; // Element inserted successfully
    }


    /** Update the height of a specified node */
    //Q26.5 ADDED UPDATING SIZE IN THE UPDATEHEIGHT() METHOD
    private void updateHeight(AVLTreeNode<E> node) {
        if (node.left == null && node.right == null){ // node is a leaf
            node.height = 0;
        }
        else if (node.left == null){ // node has no left subtree
            node.height = 1 + ((AVLTreeNode<E>)(node.right)).height;
        }
        else if (node.right == null) {// node has no right subtree
            node.height = 1 + ((AVLTreeNode<E>)(node.left)).height;
        }
        else {
            node.height = 1 +  Math.max(((AVLTreeNode<E>)(node.right)).height, ((AVLTreeNode<E>)(node.left)).height);
        }
    }


    /** Balance the nodes in the path from the specified
     * node to the root if necessary
     */
    private void balancePath(E e) {
        ArrayList<AVLTreeNode<E>> path = path(e);
        for (int i = path.size() - 1; i >= 0; i--) {
            AVLTreeNode<E> A = (AVLTreeNode<E>)(path.get(i));
            updateHeight(A);
            AVLTreeNode<E> parentOfA = (A == root) ? null : (AVLTreeNode<E>)(path.get(i - 1));

            switch (balanceFactor(A)) {
                case -2:
                    if (balanceFactor((AVLTreeNode<E>)A.left) <= 0) {
                        balanceLL(A, parentOfA); // Perform LL rotation
                    }
                    else {
                        balanceLR(A, parentOfA); // Perform LR rotation
                    }
                    break;
                case +2:
                    if (balanceFactor((AVLTreeNode<E>)A.right) >= 0) {
                        balanceRR(A, parentOfA); // Perform RR rotation
                    }
                    else {
                        balanceRL(A, parentOfA); // Perform RL rotation
                    }
            }
        }
    }


    /** Return the balance factor of the node */
    private int balanceFactor(AVLTreeNode<E> node) {
        if (node.right == null) // node has no right subtree
            return -node.height;
        else if (node.left == null) // node has no left subtree
            return +node.height;
        else
            return ((AVLTreeNode<E>)node.right).height - ((AVLTreeNode<E>)node.left).height;
    }


    /** Balance LL (see Figure 26.3) */
    private void balanceLL(AVLTreeNode<E> A,AVLTreeNode<E> parentOfA) {
        AVLTreeNode<E> B = A.left; // A is left-heavy and B is left-heavy

        if (A == root) {
            root = B;
        }
        else {
            if (parentOfA.left == A) {
                parentOfA.left = B;
            }
            else {
                parentOfA.right = B;
            }
        }

        A.left = B.right; // Make T2 the left subtree of A
        B.right = A; // Make A the left child of B
        updateHeight((AVLTreeNode<E>)A);
        updateHeight((AVLTreeNode<E>)B);
    }


    /** Balance LR (see Figure 26.5) */
    private void balanceLR(AVLTreeNode<E> A,AVLTreeNode<E> parentOfA) {
        AVLTreeNode<E> B = A.left; // A is left-heavy
        AVLTreeNode<E> C = B.right; // B is right-heavy

        if (A == root) {
            root = C;
        }
        else {
            if (parentOfA.left == A) {
                parentOfA.left = C;
            }
            else {
                parentOfA.right = C;
            }
        }

        A.left = C.right; // Make T3 the left subtree of A
        B.right = C.left; // Make T2 the right subtree of B
        C.left = B;
        C.right = A;

        // Adjust heights
        updateHeight((AVLTreeNode<E>)A);
        updateHeight((AVLTreeNode<E>)B);
        updateHeight((AVLTreeNode<E>)C);
    }


    /** Balance RR (see Figure 26.4) */
    private void balanceRR(AVLTreeNode<E> A,AVLTreeNode<E> parentOfA) {
        AVLTreeNode<E> B = A.right; // A is right-heavy and B is right-heavy

        if (A == root) {
            root = B;
        }
        else {
            if (parentOfA.left == A) {
                parentOfA.left = B;
            }
            else {
                parentOfA.right = B;
            }
        }

        A.right = B.left; // Make T2 the right subtree of A
        B.left = A;
        updateHeight((AVLTreeNode<E>)A);
        updateHeight((AVLTreeNode<E>)B);
    }


    /** Balance RL (see Figure 26.6) */
    private void balanceRL(AVLTreeNode<E> A,AVLTreeNode<E> parentOfA) {
        AVLTreeNode<E> B = A.right; // A is right-heavy
        AVLTreeNode<E> C = B.left; // B is left-heavy

        if (A == root) {
            root = C;
        }
        else {
            if (parentOfA.left == A) {
                parentOfA.left = C;
            }
            else {
                parentOfA.right = C;
            }
        }

        A.right = C.left; // Make T2 the right subtree of A
        B.left = C.right; // Make T3 the left subtree of B
        C.left = A;
        C.right = B;

        // Adjust heights
        updateHeight((AVLTreeNode<E>)A);
        updateHeight((AVLTreeNode<E>)B);
        updateHeight((AVLTreeNode<E>)C);
    }


    /** Returns a path from the root leading to the specified element */
    public ArrayList<AVLTreeNode<E> > path(E e) {
        ArrayList<AVLTreeNode<E> > list =  new ArrayList<>();
        AVLTreeNode<E>  current = root; // Start from the root

        while (current != null) {
            list.add(current); // Add the node to the list
            if (c.compare(e, current.element) < 0) {
                current = current.left;
            }
            else if (c.compare(e, current.element) > 0) {
                current = current.right;
            }
            else
                break;
        }
        return list; // Return an array list of nodes
    }


    /** AVLTreeNode is TreeNode plus height */
    protected static class AVLTreeNode<E>  {
        protected E element;
        protected AVLTreeNode<E> left;
        protected AVLTreeNode<E> right;
        protected int height = 0; // New data field

        public AVLTreeNode(E e) {
            element = e;
        }

        public int compareTo(Object o) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
