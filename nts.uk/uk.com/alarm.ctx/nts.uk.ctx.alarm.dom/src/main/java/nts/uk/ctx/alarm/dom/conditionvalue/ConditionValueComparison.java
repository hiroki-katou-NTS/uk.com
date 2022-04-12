package nts.uk.ctx.alarm.dom.conditionvalue;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConditionValueComparison {

    EQUAL(1, false),

    NOT_EQUAL(2, false),

    LESS_THAN(3, false),

    LESS_THAN_OR_EQUAL(4, false),

    GREATER_THAN(5, false),

    GREATER_THAN_OR_EQUAL(6, false),

    WITHIN_RANGE(7, true),

    WITHIN_RANGE_OR_EQUAL(8, true),

    OUT_OF_RANGE(9, true),

    OUT_OF_RANGE_OR_EQUAL(10, true),

    ;

    public final int value;

    /** 範囲チェックであるか */
    private final boolean isRange;

    /**
     * 条件式に該当するか
     * @param target チェック対象値（nullはダメ）
     * @param condition1 条件値1
     * @param condition2 条件値2（範囲チェック用）
     * @param <T>
     * @return 該当すればtrue
     */
    public <T extends Comparable<T>> boolean matches(T target, T condition1, T condition2) {

        if (target == null) {
            throw new NullPointerException("target is null");
        }

        int compare1 = target.compareTo(condition1);
        int compare2 = isRange ? target.compareTo(condition2) : 0; // 範囲チェックでのみ参照する

        switch (this) {
            case EQUAL:
                return target.equals(condition1);
            case NOT_EQUAL:
                return !target.equals(condition1);
            case LESS_THAN:
                return compare1 < 0;
            case LESS_THAN_OR_EQUAL:
                return compare1 <= 0;
            case GREATER_THAN:
                return compare1 > 0;
            case GREATER_THAN_OR_EQUAL:
                return compare1 >= 0;
            case WITHIN_RANGE:
                return compare1 > 0 && compare2 < 0;
            case WITHIN_RANGE_OR_EQUAL:
                return compare1 >= 0 && compare2 <= 0;
            case OUT_OF_RANGE:
                return compare1 < 0 && compare2 > 0;
            case OUT_OF_RANGE_OR_EQUAL:
                return compare1 <= 0 && compare2 >= 0;
            default:
                throw new RuntimeException("unknown: " + this);
        }
    }

}
