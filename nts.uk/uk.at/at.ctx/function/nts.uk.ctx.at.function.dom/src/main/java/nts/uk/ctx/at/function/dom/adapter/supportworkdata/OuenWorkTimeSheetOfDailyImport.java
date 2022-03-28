package nts.uk.ctx.at.function.dom.adapter.supportworkdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

import java.util.List;

@AllArgsConstructor
@Getter
public class OuenWorkTimeSheetOfDailyImport {
    /** 社員ID: 社員ID */
    private String empId;

    /** 年月日: 年月日 */
    private GeneralDate ymd;

    /** 応援時間帯: 日別勤怠の応援作業時間帯 */
    private List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet;
}
