package nts.uk.ctx.alarm.dom.conditionvalue;

import lombok.RequiredArgsConstructor;

/**
 * アラームリストの条件値の比較方法
 */
@RequiredArgsConstructor
public enum ConditionValueComparison {

    /** 等しい */
    EQUAL(1, false),

    /** 異なる */
    NOT_EQUAL(2, false),

    /** より小さい */
    LESS_THAN(3, false),

    /** 以下 */
    LESS_THAN_OR_EQUAL(4, false),

    /** より大きい */
    GREATER_THAN(5, false),

    /** 以上 */
    GREATER_THAN_OR_EQUAL(6, false),

    /** 範囲内（境界値を含まない） */
    WITHIN_RANGE(7, true),

    /** 範囲内（境界値を含む） */
    WITHIN_RANGE_OR_EQUAL(8, true),

    /** 範囲外（境界値を含まない） */
    OUT_OF_RANGE(9, true),

    /** 範囲外（境界値を含む） */
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
                return compare1 < 0 || compare2 > 0;
            case OUT_OF_RANGE_OR_EQUAL:
                return compare1 <= 0 || compare2 >= 0;
            default:
                throw new RuntimeException("unknown: " + this);
        }
    }

}
