package studio.istart.algorithm.expression;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import studio.istart.algorithm.tree.BinaryNode;

/**
 * 表达式测试
 *
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
@Slf4j(topic = "表达式测试")
public class ExpressionTests {

    @Test
    @DisplayName("根据反向波兰表示法(RPN)进行表达输出")
    public void toRPN() {
        String RPN = Expression.builder("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3")
                .buildReversePolishNotationExpression()
                .toString();
        Assertions.assertEquals("3 4 2 * 1 5 - 2 3 ^ ^ / +".replace(" ", ""), RPN);
    }

    @Test
    @DisplayName("根据表达式生成二叉树")
    public void toExpressionTree() {
        ExpressionTree expressionTree = new ExpressionTree("ab+cde+**");
        BinaryNode<Character> binaryNode = expressionTree.getExpressionTree();
        log.info("生成的二叉树:{}", binaryNode);
    }

    @Test
    @DisplayName("根据表达式进行遍历")
    public void expressionTreeTraversal() {
        ExpressionTree expressionTree = new ExpressionTree("ab+cde+**");
        log.info("先(根)序:{}", expressionTree.traversal(TraversalEnum.DLR));
        log.info("中序遍历:{}", expressionTree.traversal(TraversalEnum.LDR));
        log.info("后(根)序:{}", expressionTree.traversal(TraversalEnum.LRD));
    }
}
