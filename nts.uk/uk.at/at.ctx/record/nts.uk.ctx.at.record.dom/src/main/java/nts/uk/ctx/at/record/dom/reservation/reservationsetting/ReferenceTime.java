package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 基準時間
 */

@TimeRange(min = "0:00", max = "23:59")
public class ReferenceTime extends TimeDurationPrimitiveValue<ReferenceTime> {

    public ReferenceTime(int timeAsMinutes) {
        super(timeAsMinutes);
    }
}