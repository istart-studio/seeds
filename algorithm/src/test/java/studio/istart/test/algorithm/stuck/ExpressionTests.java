package studio.istart.test.algorithm.stuck;

import org.junit.Assert;
import org.junit.Test;
import studio.istart.algorithm.example.expression.Expression;
import studio.istart.algorithm.tree.BinaryNode;

/**
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
public class ExpressionTests {

    @Test
    public void toRPN() {
        String RPN = Expression.builder("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3")
            .buildReversePolishNotationExpression()
            .toString();
        Assert.assertEquals("3 4 2 * 1 5 - 2 3 ^ ^ / +".replace(" ", ""), RPN);
    }

    @Test
    public void toExpressionTree() {
        Expression.ExpressionTree expressionTree = new Expression.ExpressionTree("ab+cde+**");
        BinaryNode<Character> binaryNode = expressionTree.getExpressionTree();
        System.out.println(binaryNode);
    }

    @Test
    public void expressionTreeTraversal() {
        Expression.ExpressionTree expressionTree = new Expression.ExpressionTree("ab+cde+**");
        System.out.println(expressionTree.traversal(Expression.ExpressionTree.TraversalEnum.DLR));
        System.out.println(expressionTree.traversal(Expression.ExpressionTree.TraversalEnum.LDR));
        System.out.println(expressionTree.traversal(Expression.ExpressionTree.TraversalEnum.LRD));
    }
}
