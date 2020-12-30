package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 計算結果
 */
@Data
@NoArgsConstructor
public class CalculationResult {

    //出勤前時間1
    private AttendanceTime timeBeforeWork1;

    //出勤前時間2
    private AttendanceTime timeBeforeWork2;

    //私用外出時間
    private AttendanceTime privateOutingTime;

    //組合外出時間
    private AttendanceTime unionOutingTime;

    //退勤後時間1
    private AttendanceTime timeAfterWork1;

    //退勤後時間2
    private AttendanceTime timeAfterWork2;


}
