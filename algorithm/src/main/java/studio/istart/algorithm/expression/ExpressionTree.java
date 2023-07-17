package studio.istart.algorithm.expression;

import studio.istart.algorithm.tree.BinaryNode;

import java.util.Stack;

/**
 * 表达式树
 */
public class ExpressionTree {

    private Stack<BinaryNode<Character>> stack = new Stack<>();
    private BinaryNode<Character> expressionTree;

    public ExpressionTree(ReversePolishNotationExpression reversePolishNotationExpression) {
        reversePolishNotationExpression.getOutput().iterator().forEachRemaining(element -> {
            if (Expression.isOperatorSymbol(element)) {
                BinaryNode<Character> right = stack.pop();
                BinaryNode<Character> left = stack.pop();
                stack.add(new BinaryNode<>(element, left, right));
            } else {
                stack.add(new BinaryNode<>(element));
            }
        });
        this.expressionTree = stack.pop();
    }

    public ExpressionTree(String RPN) {
        char[] chars = RPN.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Character element = new Character(chars[i]);
            if (Expression.isOperatorSymbol(element)) {
                BinaryNode<Character> right = stack.pop();
                BinaryNode<Character> left = stack.pop();
                stack.add(new BinaryNode<>(element, left, right));
            } else {
                stack.add(new BinaryNode<>(element));
            }
        }
        this.expressionTree = stack.pop();
    }

    public BinaryNode<Character> getExpressionTree() {
        return expressionTree;
    }

    /**
     * 遍历
     *
     * @return
     */
    public String traversal(TraversalEnum traversalEnum) {
        StringBuilder stringBuilder = new StringBuilder();
        traversal(expressionTree, stringBuilder, traversalEnum);
        return stringBuilder.toString();
    }

    private void traversal(BinaryNode<Character> binaryNode, final StringBuilder stringBuilder, TraversalEnum traversalEnum) {
        if (binaryNode == null) {
            return;
        }
        if (traversalEnum == TraversalEnum.DLR) {
            stringBuilder.append(binaryNode.getKey());
        }
        traversal(binaryNode.getLeft(), stringBuilder, traversalEnum);
        if (traversalEnum == TraversalEnum.LDR) {
            stringBuilder.append(binaryNode.getKey());
        }
        traversal(binaryNode.getRight(), stringBuilder, traversalEnum);
        if (traversalEnum == TraversalEnum.LRD) {
            stringBuilder.append(binaryNode.getKey());
        }
    }
}
