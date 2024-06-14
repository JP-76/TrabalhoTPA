package lib;

import java.util.Comparator;
import java.util.ArrayList;

public class BinaryTreeImpl<T> implements IArvoreBinaria<T> {

    protected Node<T> root = null;
    protected Comparator<T> comparator;

    public BinaryTreeImpl(Comparator<T> comp) {
        comparator = comp;
    }

    @Override
    public void adicionar(T newValue) {
        Node<T> newNode = new Node<T>(newValue);
        if (root == null) {
            root = newNode;
        } else {
            Node<T> currentNode = root;
            Node<T> parentNode;
            while (true) {
                parentNode = currentNode;
                if (this.comparator.compare(newValue, currentNode.getValor()) < 0) {
                    currentNode = currentNode.getFilhoEsquerda();
                    if (currentNode == null) {
                        parentNode.setFilhoEsquerda(newNode);
                        return;
                    }
                } else {
                    currentNode = currentNode.getFilhoDireita();
                    if (currentNode == null) {
                        parentNode.setFilhoDireita(newNode);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public T pesquisar(T valor) {

        Node<T> currentNode = root;

        while (currentNode != null) {
            if (this.comparator.compare(valor, currentNode.getValor()) == 0) {
                return currentNode.getValor();
            } else if (this.comparator.compare(valor, currentNode.getValor()) < 0) {
                currentNode = currentNode.getFilhoEsquerda();
            } else {
                currentNode = currentNode.getFilhoDireita();
            }
        }
        return null;
    }

    @Override
    public T pesquisar(T valor, Comparator customComparator) {
        return pesquisarRec(root, valor, customComparator);
    }

    @SuppressWarnings("unchecked")
    private T pesquisarRec(Node<T> currentNode, T valor, Comparator customComparator) {

        T result = null;

        if (currentNode == null) {
            return null;
        }

        if (customComparator != this.comparator) {
            if (currentNode.getFilhoEsquerda() != null && result == null) {
                result = pesquisarRec(currentNode.getFilhoEsquerda(), valor, customComparator);
            } 
            if (currentNode.getFilhoDireita() != null && result == null) {
                result = pesquisarRec(currentNode.getFilhoDireita(), valor, customComparator);
            }
        } else {
            result = pesquisar(valor);
        }

        if (customComparator.compare(valor, currentNode.getValor()) == 0){
            return currentNode.getValor();
        }
        
        return result;
    }

    @Override
    public T remover(T valor) {
        Node<T> node = removerRec(this.root, valor);
        if (node != null) {
            this.root = node; // Update the root in case it has changed
        }
        return node == null ? null : node.getValor();
    }

    private Node<T> removerRec(Node<T> currentNode, T valor) {
        if (currentNode == null) {
            return null;
        }

        if (comparator.compare(valor, currentNode.getValor()) < 0) {
            currentNode.setFilhoEsquerda(removerRec(currentNode.getFilhoEsquerda(), valor));
        } else if (comparator.compare(valor, currentNode.getValor()) > 0) {
            currentNode.setFilhoDireita(removerRec(currentNode.getFilhoDireita(), valor));
        } else {
            // Node to be deleted found
            if (currentNode.getFilhoEsquerda() == null) {
                return currentNode.getFilhoDireita();
            } else if (currentNode.getFilhoDireita() == null) {
                return currentNode.getFilhoEsquerda();
            }

            // Node with two children: Get the in-order predecessor (max in the left subtree)
            Node<T> predecessor = findMax(currentNode.getFilhoEsquerda());
            currentNode.setValor(predecessor.getValor());
            currentNode.setFilhoEsquerda(removerRec(currentNode.getFilhoEsquerda(), predecessor.getValor()));
        }

        return currentNode;
    }

    private Node<T> findMax(Node<T> node) {
        while (node.getFilhoDireita() != null) {
            node = node.getFilhoDireita();
        }
        return node;
    }

    @Override
    public int altura() {
        Node<T> currentNode = root;
        int height = -1;
        while (currentNode != null) {
            height++;
            if (currentNode.getFilhoEsquerda() != null) {
                currentNode = currentNode.getFilhoEsquerda();
            } else if (currentNode.getFilhoDireita() != null) {
                currentNode = currentNode.getFilhoDireita();
            } else {
                currentNode = null;
            }
        }
        return height;
    }

    @Override
    public int quantidadeNos() {
        return quantidadeNosRec(root);
    }

    private int quantidadeNosRec(Node<T> currentNode) {
        if (currentNode == null)
            return 0;
        return 1 + quantidadeNosRec(currentNode.getFilhoEsquerda()) + quantidadeNosRec(currentNode.getFilhoDireita());
    }

    @Override
    public String caminharEmNivel() {

        Node<T> currentNode = this.root;
        String buffer = "[";
        if (currentNode != null) {
            ArrayList<Node<T>> queue = new ArrayList<Node<T>>();
            queue.add(currentNode);
            while (!queue.isEmpty()) {
                currentNode = queue.get(0);
                buffer += currentNode.getValor().toString() + " \n ";
                if (currentNode.getFilhoEsquerda() != null) {
                    queue.add(currentNode.getFilhoEsquerda());
                }
                if (currentNode.getFilhoDireita() != null) {
                    queue.add(currentNode.getFilhoDireita());
                }
                queue.remove(0);
            }
        }

        return buffer.substring(0, buffer.length() - 3) + "]";
    }

    @Override
    public String caminharEmOrdem() {
        Node<T> currentNode = root;
        String buffer = caminarEmOrdemRec(currentNode, "[");
        return buffer.substring(0, buffer.length() - 3) + "]";
    }

    private String caminarEmOrdemRec(Node<T> node, String buffer) {
        if (node != null) {
            buffer = caminarEmOrdemRec(node.getFilhoEsquerda(), buffer);
            buffer += node.getValor().toString() + " \n ";
            buffer = caminarEmOrdemRec(node.getFilhoDireita(), buffer);
        }
        return buffer;
    }
}
