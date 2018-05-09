package studio.istart.algorithm.stack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 中缀表达式
 * Reverse Polish notation，RPN，或逆波兰记法
 *
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
public class Expression {
    @Getter
    @Setter
    private String expression;

    private Expression() {
    }

    /**
     * 构建表达式
     * @param expressionCharts 中缀表达式
     * @return  Expression
     */
    public static Expression build(String expressionCharts) {
        Expression expression = new Expression();
        expression.expression = expressionCharts;
        return expression;
    }

    /**
     * 输出队列
     */
    private Queue<Character> output = new ConcurrentLinkedQueue<>();
    /**
     * 操作符栈
     */
    private Stack<OperatorSymbol> operatorStack = new Stack<>();

    /**
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
     * @return 后缀表达式
     */
    public String toRPN() {
        for (char element : this.expression.toCharArray()) {
            if (element != ' ') {
                System.out.println("chart:" + element);
                if (isOperatorSymbol(element)) {
                    setOperatorSymbol(element);
                } else if (Character.isDigit(element)) {
                    output.add(new Character(element));
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (!operatorStack.empty()) {
            output.add(operatorStack.pop().element.charValue());
        }
        for (Character character : output) {
            stringBuilder.append(character.charValue());
        }
        return stringBuilder.toString();
    }

    /**
     * 是否为操作符号
     *
     * @return T:是 F:不是
     */
    private boolean isOperatorSymbol(char element) {
        Optional<OperatorSymbol> operatorSymbolOptional = SYMBOLS.stream().filter(operatorSymbol -> operatorSymbol.element.charValue() == element).findAny();
        return operatorSymbolOptional.isPresent();
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
     * @param operatorSymbol    当前操作符
     * @param stackSymbol 栈中第一个元素
     * @return T:需要 F:不需要
     */
    private boolean getStackTop(OperatorSymbol operatorSymbol, OperatorSymbol stackSymbol) {
        return (operatorSymbol.associativity == AssociativityEnum.LEFT && operatorSymbol.priority <= stackSymbol.priority)
            || (operatorSymbol.associativity == AssociativityEnum.RIGHT && operatorSymbol.priority < stackSymbol.priority);
    }

    /**
     * 已知的操作符号
     */
    private static final List<OperatorSymbol> SYMBOLS = initSymbols();

    /**
     * 设定已知的操作符号的属性
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
}
