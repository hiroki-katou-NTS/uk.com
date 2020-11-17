package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 平均時間
 *
 * @author Thanh.LNP
 */
@TimeRange(min = "-999:99", max = "999:99")
public class AverageTime extends TimeDurationPrimitiveValue<AverageTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new amount value.
     *
     * @param rawValue the raw value
     */
    public AverageTime(Integer rawValue) {
        super(rawValue);
    }
}
