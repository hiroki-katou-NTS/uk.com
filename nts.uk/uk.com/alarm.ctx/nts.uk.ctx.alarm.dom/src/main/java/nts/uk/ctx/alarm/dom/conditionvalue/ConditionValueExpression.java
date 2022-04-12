package nts.uk.ctx.alarm.dom.conditionvalue;

import lombok.Value;

/**
 * アラームリストの条件値の式
 */
@Value
public class ConditionValueExpression {

    /** 条件値１ */
    Double condition1;

    /** 条件値２（範囲チェック用）*/
    Double condition2;

    /** 比較条件 */
    ConditionValueComparison comparison;

    /**
     * 対象値が条件に該当するか
     * @param targetValue 対象値
     * @return 該当すればtrue
     */
    public boolean matches(Double targetValue) {

        if (targetValue == null) {
            return false;
        }

        return comparison.matches(targetValue, condition1, condition2);
    }

    /**
     * 抽出結果用に整形したテキストを返す
     * @return
     */
    public String toText() {
        return "not implemented";
    }
}
