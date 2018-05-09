package studio.istart.algorithm.tree;

/**
 * 二叉树
 *
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
public class BinaryNode<T> {
    private T key;
    private BinaryNode<T> left;
    private BinaryNode<T> right;

    public BinaryNode(T key) {
        this.key = key;
    }

    public BinaryNode(T key, BinaryNode<T> left, BinaryNode<T> right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    public T getKey() {
        return key;
    }

    public BinaryNode<T> getLeft() {
        return left;
    }

    public BinaryNode<T> getRight() {
        return right;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public void setLeft(BinaryNode<T> left) {
        this.left = left;
    }

    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }
}
