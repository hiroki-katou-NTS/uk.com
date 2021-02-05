package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 外出時間帯
 */
@NoArgsConstructor
@Getter
public class OutingTimeZoneExport {
    // 外出理由
    private GoingOutReason goingOutReason;

    // 時間帯
    private TimeSpanForCalc timeZone;

    public OutingTimeZoneExport(int goingOutReason, Integer start, Integer end) {
        this.goingOutReason = EnumAdaptor.valueOf(goingOutReason, GoingOutReason.class);
        this.timeZone = new TimeSpanForCalc(
                start == null ? null : new TimeWithDayAttr(start),
                end == null? null : new TimeWithDayAttr(end))
        ;
    }
}
