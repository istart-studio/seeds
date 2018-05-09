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

//        RPN = Expression.builder("(a+b)*c*(d+e)")
//                .buildReversePolishNotationExpression()
//                .toString();
//        Assert.assertEquals("ab+cde+**".replace(" ", ""), RPN);
    }

    @Test
    public void toExpressionTree() {
        Expression.ExpressionTree expressionTree = new Expression.ExpressionTree("ab+cde+**");
        BinaryNode<Character> binaryNode = expressionTree.getExpressionTree();
        System.out.println(binaryNode);
    }
}
