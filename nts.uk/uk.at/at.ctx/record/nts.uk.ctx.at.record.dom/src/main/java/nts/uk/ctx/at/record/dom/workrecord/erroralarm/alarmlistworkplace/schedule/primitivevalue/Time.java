package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 職場日次チェック条件値時間
 *
 * @author Thanh.LNP
 */
@TimeRange(min = "-9999:99", max = "9999:99")
public class Time extends TimeDurationPrimitiveValue<Time> {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new amount value.
     *
     * @param rawValue the raw value
     */
    public Time(Integer rawValue) {
        super(rawValue);
    }
}
