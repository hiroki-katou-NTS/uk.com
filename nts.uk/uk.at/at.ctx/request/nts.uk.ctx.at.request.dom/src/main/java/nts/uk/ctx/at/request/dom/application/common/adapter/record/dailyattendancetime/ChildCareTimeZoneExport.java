package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 育児時間帯
 */
@NoArgsConstructor
@Getter
public class ChildCareTimeZoneExport {
    // 育児介護区分
    private ChildCareAttribute childCareAtr;

    // 時間帯
    private TimeSpanForCalc timeZone;

    public ChildCareTimeZoneExport(int childCareAtr, Integer start, Integer end) {
        this.childCareAtr = EnumAdaptor.valueOf(childCareAtr, ChildCareAttribute.class);
        this.timeZone = new TimeSpanForCalc(
                start == null ? null : new TimeWithDayAttr(start),
                end == null? null : new TimeWithDayAttr(end))
        ;
    }
}
