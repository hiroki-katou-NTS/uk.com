package nts.uk.ctx.alarm.dom.conditionvalue;

import lombok.Value;

import java.util.Optional;

/**
 * アラームリスト条件値の式
 */
@Value
public class ConditionValueExpression {

    /** 比較条件 */
    ConditionValueComparison comparison;

    /** 条件値１ */
    double condition1;

    /** 条件値２（範囲チェック用）*/
    Optional<Double> condition2;

    /**
     * 条件に該当するか
     * @param targetValue 対象値
     * @return 対象値が該当すればtrue
     */
    public boolean matches(double targetValue) {
        return comparison.matches(targetValue, condition1, condition2.orElse(null));
    }

    /**
     * 抽出結果用に整形したテキストを返す
     * @return
     */
    public String toText() {
        return "not implemented";
    }
}
