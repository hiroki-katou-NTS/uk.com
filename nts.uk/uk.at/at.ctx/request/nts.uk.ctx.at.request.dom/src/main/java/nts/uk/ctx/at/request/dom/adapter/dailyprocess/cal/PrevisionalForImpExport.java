package nts.uk.ctx.at.request.dom.adapter.dailyprocess.cal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class PrevisionalForImpExport {
    //社員ID
    String employeeId;
    //対象年月日
    GeneralDate targetDate;
    //出退勤時刻
    Map<Integer, TimeZone> timeSheets;
    //勤種コード
    WorkTypeCode workTypeCode;
    //就時コード
    WorkTimeCode workTimeCode;
    //休憩時間帯
    List<BreakTimeSheet> breakTimeSheets;
    //外出時間帯
    List<OutingTimeSheet> outingTimeSheets;
    //短時間勤務時間帯
    List<ShortWorkingTimeSheet> shortWorkingTimeSheets;
}
