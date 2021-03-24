package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 職場日次チェック条件値時間
 *
 */
@TimeRange(min = "-9999:99", max = "9999:99")
public class WorkplaceDailyCheckConditionTime extends TimeDurationPrimitiveValue<WorkplaceDailyCheckConditionTime> {
	private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new amount value.
     *
     * @param rawValue the raw value
     */
    public WorkplaceDailyCheckConditionTime(Integer rawValue) {
        super(rawValue);
    }
}
