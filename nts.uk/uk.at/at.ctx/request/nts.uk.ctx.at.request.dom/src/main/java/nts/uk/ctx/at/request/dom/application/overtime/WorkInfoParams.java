package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

import java.util.List;

/**
 * 勤務情報パラメータ
 */
@Value
public class WorkInfoParams {
    //社員ID
    String employeeId;
    //年月日
    GeneralDate date;
    //勤種コード
    WorkTypeCode workTypeCode;
    //就時コード
    WorkTimeCode workTimeCode;
    //出退勤時刻
    List<TimeZoneWithWorkNo> workTimes;
    //外出時間帯
    List<OutingTimeSheet> outTimes;
    //短時間勤務時間帯
    List<ShortWorkingTimeSheet> shortWorkTimes;
}
