package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.errorcount;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * エラーアラーム発生カウントの閾値
 */
@IntegerRange(min = 0, max = 9999)
public class ErrorAlarmCounterThreshold extends IntegerPrimitiveValue<ErrorAlarmCounterThreshold> {
    /**
     * Constructs.
     *
     * @param rawValue raw value
     */
    public ErrorAlarmCounterThreshold(Integer rawValue) {
        super(rawValue);
    }
}
