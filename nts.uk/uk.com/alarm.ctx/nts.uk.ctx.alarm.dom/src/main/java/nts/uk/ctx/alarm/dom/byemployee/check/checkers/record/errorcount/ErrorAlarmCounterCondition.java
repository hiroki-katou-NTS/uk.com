package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.errorcount;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.function.BiPredicate;

/**
 * エラーアラーム発生カウントのアラーム条件
 */
@Value
public class ErrorAlarmCounterCondition {

    /** 比較条件 */
    Comparison comparison;

    /** 閾値 */
    int threshold;

    public boolean matches(int value) {
        return comparison.matches(value, threshold);
    }

    public String getConditionText() {
        return comparison.format(threshold);
    }

    @RequiredArgsConstructor
    public enum Comparison {

        EQUAL((a, b) -> a == b, "%dと等しい"),

        NOT_EQUAL((a, b) -> a != b, "%dと異なる"),

        LESS_THAN((a, b) -> a < b, "%dより小さい"),

        LESS_THAN_OR_EQUAL((a, b) -> a <= b, "%d以下"),

        GREATER_THAN((a, b) -> a > b, "%dより大きい"),

        GREATER_THAN_OR_EQUAL((a, b) -> a >= b, "%d以上"),

        ;

        private final BiPredicate<Integer, Integer> predicate;

        private final String text;

        public boolean matches(int a, int b) {
            return predicate.test(a, b);
        }

        public String format(int value) {
            return String.format(text, value);
        }
    }
}
