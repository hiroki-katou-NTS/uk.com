package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 計算結果
 */
@Data
@NoArgsConstructor
public class CalculationResultDto {

    //出勤前時間1
    private Integer timeBeforeWork1;

    //退勤後時間1
    private Integer timeAfterWork1;

    //出勤前時間2
    private Integer timeBeforeWork2;

    //退勤後時間2
    private Integer timeAfterWork2;

    //私用外出時間
    private Integer privateOutingTime;

    //組合外出時間
    private Integer unionOutingTime;

}
