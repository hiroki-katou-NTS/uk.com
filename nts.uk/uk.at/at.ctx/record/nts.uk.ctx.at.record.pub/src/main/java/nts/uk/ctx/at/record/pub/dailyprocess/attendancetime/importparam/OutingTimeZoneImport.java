package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.importparam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * 外出時間帯
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OutingTimeZoneImport {
    // 外出理由
    private GoingOutReason goingOutReason;

    // 時間帯
    private TimeSpanForCalc timeZone;
}
