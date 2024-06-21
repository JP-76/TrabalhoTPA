package lib;

import java.util.Comparator;

public class AVLTreeImpl <T> extends BinaryTreeImpl<T>{

    public AVLTreeImpl(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    protected Node<T> adicionarRec(Node<T> currentNode, Node<T> newNode){  
        currentNode = super.adicionarRec(currentNode, newNode);
        return balanceNode(currentNode);
    }

    @Override
    protected Node<T> removerRec(Node<T> currentNode, T valor) {

        if (currentNode == null) return null;

        int comparisonResult = this.comparator.compare(currentNode.getValor(), valor);

        // Dig into left subtree, the value we're looking
        // for is smaller than the current value.
        if (comparisonResult > 0) {
        currentNode.setFilhoEsquerda(removerRec(currentNode.getFilhoEsquerda(), valor));

        // Dig into right subtree, the value we're looking
        // for is greater than the current value.
        } else if (comparisonResult < 0) {
        currentNode.setFilhoDireita(removerRec(currentNode.getFilhoDireita(), valor));

        // Found the node we wish to remove.
        } else {

            // This is the case with only a right subtree or no subtree at all.
            // In this situation just swap the node we wish to remove
            // with its right child.
            if (currentNode.getFilhoEsquerda() == null) {
                return currentNode.getFilhoDireita();

                // This is the case with only a left subtree or
                // no subtree at all. In this situation just
                // swap the node we wish to remove with its left child.
            } else if (currentNode.getFilhoDireita() == null) {
                return currentNode.getFilhoEsquerda();

                // When removing a node from a binary tree with two links the
                // successor of the node being removed can either be the largest
                // value in the left subtree or the smallest value in the right
                // subtree. As a heuristic, I will remove from the subtree with
                // the greatest hieght in hopes that this may help with balancing.
            } else {

                // Choose to remove from left subtree
                if (this.getNodeHeight(currentNode.getFilhoEsquerda()) > this.getNodeHeight(currentNode.getFilhoDireita())) {

                // Swap the value of the successor into the node.
                T successorValue = findMax(currentNode.getFilhoEsquerda()).getValor();
                currentNode.setValor(successorValue);

                // Find the largest node in the left subtree.
                currentNode.setFilhoEsquerda(removerRec(currentNode.getFilhoEsquerda(), successorValue));

                } else {

                // Swap the value of the successor into the node.
                T successorValue = findMin(currentNode.getFilhoDireita()).getValor();
                currentNode.setValor(successorValue);

                // Go into the right subtree and remove the leftmost node we
                // found and swapped data with. This prevents us from having
                // two nodes in our tree with the same value.
                currentNode.setFilhoDireita(removerRec(currentNode.getFilhoDireita(), successorValue));
                }
            }
        }
        return balanceNode(currentNode);
    }

    public Node<T> rightRotate(Node<T> node){
        Node<T> childNode = node.getFilhoEsquerda();   
        node.setFilhoEsquerda(childNode.getFilhoDireita());  
        childNode.setFilhoDireita(node);
        return childNode;
    }

    public Node<T> leftRotate(Node<T> node){
        Node<T> childNode = node.getFilhoDireita();
        node.setFilhoDireita(childNode.getFilhoEsquerda());
        childNode.setFilhoEsquerda(node);
        return childNode;
    }

    public Node<T> rightLeftRotate(Node<T> node){
        node.setFilhoDireita(this.rightRotate(node.getFilhoDireita()));
        return this.leftRotate(node);
    }
                
    public Node<T> leftRightRotate(Node<T> node){
        node.setFilhoEsquerda(this.leftRotate(node.getFilhoEsquerda()));
        return this.rightRotate(node);
    }

    private int getNodeHeight(Node<T> node){
        if(node == null){
            return -1;
        } else {
            int rightHeight = this.getNodeHeight(node.getFilhoDireita());
            int leftHeight = this.getNodeHeight(node.getFilhoEsquerda());
            return rightHeight > leftHeight ? rightHeight + 1 : leftHeight + 1;
        }
    }
    
    private int getBalanceFactor(Node<T> node){
        return getNodeHeight(node.getFilhoDireita()) - getNodeHeight(node.getFilhoEsquerda());
    }

    private Node<T> balanceNode(Node<T> node) {
        int balanceFactor = getBalanceFactor(node);
        if(balanceFactor > 1) {
            if(getBalanceFactor(node.getFilhoDireita()) < 0){
                node = rightLeftRotate(node);
            } else {
                node = leftRotate(node);
            }
        } else if(balanceFactor < -1){
            if(getBalanceFactor(node.getFilhoEsquerda()) > 0){
                node = leftRightRotate(node);
            } else {
                node = rightRotate(node);
            }
        }
        return node;
    }

}
