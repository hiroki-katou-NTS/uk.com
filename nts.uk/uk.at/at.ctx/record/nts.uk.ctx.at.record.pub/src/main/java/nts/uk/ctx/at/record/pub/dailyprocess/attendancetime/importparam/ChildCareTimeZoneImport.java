package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.importparam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;

/**
 * 育児時間帯
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChildCareTimeZoneImport {
    // 育児介護区分
    private ChildCareAttribute goingOutReason;

    // 時間帯
    private TimeSpanForCalc timeZone;
}
