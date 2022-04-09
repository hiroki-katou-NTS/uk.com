package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.errorcount;

import lombok.RequiredArgsConstructor;

import java.util.function.BiPredicate;

/**
 * エラーアラーム発生カウントの比較条件
 */
@RequiredArgsConstructor
public enum ErrorAlarmCounterComparison {

    EQUAL(1, (a, b) -> a == b, "%dと等しい"),

    NOT_EQUAL(2, (a, b) -> a != b, "%dと異なる"),

    LESS_THAN(3, (a, b) -> a < b, "%dより小さい"),

    LESS_THAN_OR_EQUAL(4, (a, b) -> a <= b, "%d以下"),

    GREATER_THAN(5, (a, b) -> a > b, "%dより大きい"),

    GREATER_THAN_OR_EQUAL(6, (a, b) -> a >= b, "%d以上"),

    ;

    public final int value;

    private final BiPredicate<Integer, Integer> predicate;

    private final String text;

    /**
     * 該当するか
     * @param target 対象値
     * @param threshold 閾値
     * @return
     */
    public boolean matches(int target, ErrorAlarmCounterThreshold threshold) {
        return predicate.test(target, threshold.v());
    }

    public String format(ErrorAlarmCounterThreshold threshold) {
        return String.format(text, threshold.v());
    }
}
