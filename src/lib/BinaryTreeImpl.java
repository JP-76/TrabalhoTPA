package lib;

import java.util.Comparator;
import java.util.ArrayList;

@SuppressWarnings("rawtypes") // Anotação para suprimir avisos de compilação
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
        if (currentNode == null) {
            return null;
        }

        if (customComparator.compare(valor, currentNode.getValor()) == 0) {
            return currentNode.getValor();
        } else if (customComparator.compare(valor, currentNode.getValor()) < 0) {
            return pesquisarRec(currentNode.getFilhoEsquerda(), valor, customComparator);
        } else {
            return pesquisarRec(currentNode.getFilhoDireita(), valor, customComparator);
        }
    }

    @Override
    public T remover(T valor) {
        if (root == null) {
            throw new IllegalArgumentException("Value not found in tree.");
        }
        return removerRec(root, valor);
    }

    private T removerRec(Node<T> currentNode, T valor) {
        if (currentNode == null) {
            return null;
        }

        int comparisonResult = comparator.compare(valor, currentNode.getValor());

        if (comparisonResult < 0) {
            currentNode.setFilhoEsquerda(new Node<>(removerRec(currentNode.getFilhoEsquerda(), valor)));
        } else if (comparisonResult > 0) {
            currentNode.setFilhoDireita(new Node<>(removerRec(currentNode.getFilhoDireita(), valor)));
        } else {
            // Node to be removed found
            T removedValue = currentNode.getValor();

            // Case 1: Node has no children
            if (currentNode.getFilhoEsquerda() == null && currentNode.getFilhoDireita() == null) {
                return removedValue;
            }

            // Case 2: Node has one child
            if (currentNode.getFilhoEsquerda() == null) {
                return currentNode.getFilhoDireita().getValor();
            }
            if (currentNode.getFilhoDireita() == null) {
                return currentNode.getFilhoEsquerda().getValor();
            }

            // Case 3: Node has two children
            Node<T> successor = findSuccessor(currentNode.getFilhoDireita());
            currentNode.setValor(successor.getValor());
            currentNode.setFilhoDireita(removeSuccessor(currentNode.getFilhoDireita()));

            return removedValue;
        }

        return currentNode.getValor();
    }

    private Node<T> findSuccessor(Node<T> node) {
        while (node.getFilhoEsquerda() != null) {
            node = node.getFilhoEsquerda();
        }
        return node;
    }

    private Node<T> removeSuccessor(Node<T> node) {
        if (node.getFilhoEsquerda() == null) {
            return node.getFilhoDireita();
        }
        node.setFilhoEsquerda(removeSuccessor(node.getFilhoEsquerda()));
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

        Node<T> currentNode = root;
        String buffer = "";

        if (currentNode != null) {
            ArrayList<Node<T>> queue = new ArrayList<>();
            queue.add(currentNode);
            while (queue.size() > 0) {
                currentNode = queue.get(0);
                buffer += currentNode.getValor().toString();
                if (currentNode.getFilhoEsquerda() != null) {
                    queue.add(currentNode.getFilhoEsquerda());
                }
                if (currentNode.getFilhoDireita() != null) {
                    queue.add(currentNode.getFilhoDireita());
                }
                queue.remove(0);
            }
        }

        return buffer;
    }

    @Override
    public String caminharEmOrdem() {
        Node<T> currentNode = root;
        return caminarEmOrdemRec(currentNode, "");
    }

    private String caminarEmOrdemRec(Node<T> node, String buffer) {
        if (node != null) {
            buffer = caminarEmOrdemRec(node.getFilhoEsquerda(), buffer);
            buffer += node.getValor().toString();
            buffer = caminarEmOrdemRec(node.getFilhoDireita(), buffer);
        }
        return buffer;
    }
}
