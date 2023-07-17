package studio.istart.algorithm.expression;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 操作符号
 */
@Data
@AllArgsConstructor
public class OperatorSymbol {
    private Character element;
    private int priority;
    private Expression.AssociativityEnum associativity;

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
