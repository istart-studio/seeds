package studio.istart.algorithm.example.expression;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import studio.istart.algorithm.tree.BinaryNode;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 中缀表达式
 *
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
public class Expression {
    /**
     * 中缀表达式
     */
    @Getter
    @Setter
    private String expression;

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
     * Reverse Polish notation，RPN，或逆波兰记法
     */
    public class ReversePolishNotationExpression {

        /**
         * 后缀表达式：输出队列
         */
        private Queue<Character> output = new ConcurrentLinkedQueue<>();

        public Queue<Character> getOutput() {
            return output;
        }

        /**
         * 后缀表达式：操作符栈
         */
        private Stack<OperatorSymbol> operatorStack = new Stack<>();

        /**
         * Reverse Polish notation，RPN，或逆波兰记法
         * 中缀转后缀
         * todo:现在只支持正整数，支持实数
         * <p>
         * 调度场算法
         * 1.运算符具有优先级
         * 2.如果输入是运算符，则压入操作符堆栈
         * 3.如果输入运算符的优先级低于或等于运算符栈顶的操作符优先级，则栈内元素进入输出队列
         * 0.如果这个记号表示一个函数参数的分隔符（例如，一个半角逗号 , ）：
         * 0.>从栈当中不断地弹出操作符并且放入输出队列中去，直到栈顶部的元素为一个左括号为止。
         * 0.>如果一直没有遇到左括号，那么要么是分隔符放错了位置，要么是括号不匹配。
         * 1.如果这个记号表示一个操作符，记做o1，那么：
         * 1.>只要存在另一个记为o2的操作符位于栈的顶端，并且
         * 1.>如果o1是左结合性的并且它的运算符优先级要小于或者等于o2的优先级，或者
         * 1.>如果o1是右结合性的并且它的运算符优先级比o2的优先级，
         * 1.>>那么,将o2从栈的顶端弹出并且放入输出队列中（循环直至以上条件不满足为止）；
         * 1.>最后，将o1压入栈的顶端。
         *
         * @param expression 中缀表达式
         * @return 后缀表达式
         */
        public ReversePolishNotationExpression(final String expression) {
            String exp = expression.replace(" ", "");
            for (char element : exp.toCharArray()) {
                if (isOperatorSymbol(element)) {
                    setOperatorSymbol(element);
//                } else if (Character.isDigit(element)) {
                } else {
                    output.add(new Character(element));
                }
            }
            while (!operatorStack.empty()) {
                output.add(operatorStack.pop().element.charValue());
            }
        }

        /**
         * 操作符处理
         *
         * @param symbolChar 要处理的操作符
         */
        private void setOperatorSymbol(char symbolChar) {
            Optional<OperatorSymbol> operatorSymbolOptional = SYMBOLS.stream().filter(operatorSymbol -> operatorSymbol.element.charValue() == symbolChar).findAny();
            if (!operatorSymbolOptional.isPresent()) {
                throw new IllegalStateException("unknown symbol :" + symbolChar);
            }
            OperatorSymbol operatorSymbol = operatorSymbolOptional.get();
            //取出所有的的符号至输出队列，取到（ 符号为止
            if (operatorSymbol.element == ')') {
                boolean finish = true;
                while (finish) {
                    OperatorSymbol queueSymbol = operatorStack.pop();
                    if (queueSymbol.element == '(') {
                        finish = false;
                    } else {
                        output.offer(queueSymbol.element);
                    }
                }
                return;
            }
            if (operatorSymbol.element == '(') {
                operatorStack.push(operatorSymbol);
                return;
            }
            OperatorSymbol stackSymbol = operatorStack.empty() ? null : operatorStack.peek();
            while (stackSymbol != null && getStackTop(operatorSymbol, stackSymbol)) {
                output.add(operatorStack.pop().element);
                stackSymbol = operatorStack.empty() ? null : operatorStack.peek();
            }
            operatorStack.push(operatorSymbol);
        }

        /**
         * 是否需要从操作符栈中弹出
         *
         * @param operatorSymbol 当前操作符
         * @param stackSymbol    栈中第一个元素
         * @return T:需要 F:不需要
         */
        private boolean getStackTop(OperatorSymbol operatorSymbol, OperatorSymbol stackSymbol) {
            return (operatorSymbol.associativity == AssociativityEnum.LEFT && operatorSymbol.priority <= stackSymbol.priority)
                    || (operatorSymbol.associativity == AssociativityEnum.RIGHT && operatorSymbol.priority < stackSymbol.priority);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            output.iterator().forEachRemaining(character -> {
                stringBuilder.append(character.charValue());
            });
            return stringBuilder.toString();
        }
    }

    private ReversePolishNotationExpression reversePolishNotationExpression;

    public ReversePolishNotationExpression buildReversePolishNotationExpression() {
        if (reversePolishNotationExpression == null) {
            reversePolishNotationExpression = new ReversePolishNotationExpression(this.expression);
        }
        return reversePolishNotationExpression;
    }

    /**
     * 是否为操作符号
     *
     * @return T:是 F:不是
     */
    private static boolean isOperatorSymbol(char element) {
        Optional<OperatorSymbol> operatorSymbolOptional = SYMBOLS.stream().filter(operatorSymbol -> operatorSymbol.element.charValue() == element).findAny();
        return operatorSymbolOptional.isPresent();
    }


    /**
     * 已知的操作符号
     */
    private static final List<OperatorSymbol> SYMBOLS = initSymbols();

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
     * 操作符号
     */
    @Data
    @AllArgsConstructor
    public static class OperatorSymbol {
        private Character element;
        private int priority;
        private AssociativityEnum associativity;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof OperatorSymbol)) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }

            OperatorSymbol that = (OperatorSymbol) o;

            return getElement().equals(that.getElement());
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + getElement().hashCode();
            return result;
        }
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

    /**
     * 表达式树
     */
    public static class ExpressionTree {

        private Stack<BinaryNode<Character>> stack = new Stack<>();
        private BinaryNode<Character> expressionTree;

        public ExpressionTree(ReversePolishNotationExpression reversePolishNotationExpression) {
            reversePolishNotationExpression.getOutput().iterator().forEachRemaining(element -> {
                if (isOperatorSymbol(element)) {
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
                if (isOperatorSymbol(element)) {
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
    }

    private ExpressionTree expressionTree;

    /**
     * 构建表达式树
     */
    public ExpressionTree buildExpressionTree() {
        if (expressionTree == null) {
            expressionTree = new ExpressionTree(buildReversePolishNotationExpression());
        }
        return expressionTree;
    }
}
