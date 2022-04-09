package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.errorcount;

import lombok.Value;

/**
 * エラーアラーム発生カウントのアラーム条件
 */
@Value
public class ErrorAlarmCounterCondition {

    /** 比較条件 */
    ErrorAlarmCounterComparison comparison;

    /** 閾値 */
    ErrorAlarmCounterThreshold threshold;

    /**
     * 該当するか
     * @param targetValue 対象値
     * @return
     */
    public boolean matches(int targetValue) {
        return comparison.matches(targetValue, threshold);
    }

    public String getConditionText() {
        return comparison.format(threshold);
    }

}
