package studio.istart.test.algorithm.stuck;

import org.junit.Assert;
import org.junit.Test;
import studio.istart.algorithm.stack.Expression;

/**
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
public class ExpressionTests {

    @Test
    public void toRPN() {
        String RPN = Expression.build("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3").toRPN();
        Assert.assertEquals("3 4 2 * 1 5 - 2 3 ^ ^ / +".replace(" ", ""), RPN);
    }
}
