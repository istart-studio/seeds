package studio.istart.algorithm.expression;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 中缀表达式
 *
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
public class Expression {
    /**
     * 已知的操作符号
     */
    public static final List<OperatorSymbol> SYMBOLS = initSymbols();
    /**
     * 中缀表达式
     */
    @Getter
    @Setter
    private String expression;
    private ReversePolishNotationExpression reversePolishNotationExpression;
    private ExpressionTree expressionTree;

    private Expression() {
    }

    /**
     * 构建表达式
     *
     * @param expressionCharts 中缀表达式
     * @return Expression
     */
    public static Expression builder(String expressionCharts) {
        Expression expression = new Expression();
        expression.expression = expressionCharts;
        return expression;
    }

    /**
     * 是否为操作符号
     *
     * @return T:是 F:不是
     */
    public static boolean isOperatorSymbol(char element) {
        Optional<OperatorSymbol> operatorSymbolOptional = SYMBOLS.stream().filter(operatorSymbol -> operatorSymbol.getElement().charValue() == element).findAny();
        return operatorSymbolOptional.isPresent();
    }

    /**
     * 设定已知的操作符号的属性
     *
     * @return 已知的操作符号集合
     */
    public static List<OperatorSymbol> initSymbols() {
        List<OperatorSymbol> operatorSymbols = new ArrayList();
        operatorSymbols.add(new OperatorSymbol('+', 1, AssociativityEnum.LEFT));
        operatorSymbols.add(new OperatorSymbol('-', 1, AssociativityEnum.LEFT));
        operatorSymbols.add(new OperatorSymbol('*', 2, AssociativityEnum.LEFT));
        operatorSymbols.add(new OperatorSymbol('/', 2, AssociativityEnum.LEFT));
        operatorSymbols.add(new OperatorSymbol('^', 3, AssociativityEnum.RIGHT));
        operatorSymbols.add(new OperatorSymbol(')', -1, AssociativityEnum.NONE));
        operatorSymbols.add(new OperatorSymbol('(', -1, AssociativityEnum.NONE));
        return operatorSymbols;
    }

    /**
     * 构建 RPN，反向波兰表示法
     *
     * @return RPN，反向波兰表示法
     */
    public ReversePolishNotationExpression buildReversePolishNotationExpression() {
        if (reversePolishNotationExpression == null) {
            reversePolishNotationExpression = new ReversePolishNotationExpression(this.expression);
        }
        return reversePolishNotationExpression;
    }

    /**
     * 构建表达式树
     */
    public ExpressionTree buildExpressionTree() {
        if (expressionTree == null) {
            expressionTree = new ExpressionTree(buildReversePolishNotationExpression());
        }
        return expressionTree;
    }

    /**
     * 结合性
     */
    enum AssociativityEnum {
        /**
         * 右结合性
         */
        RIGHT,
        /**
         * 左结合性
         */
        LEFT,
        NONE
    }
}
