package nts.uk.screen.at.app.kaf021.query.c_d;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.Overtime;

@Getter
public class OvertimeDto {
    public OvertimeDto(Overtime overtime) {
        this.overtimeHoursOfMonth = overtime.getOvertimeHoursOfMonth().v();
        this.overtimeHoursOfYear = overtime.getOvertimeHoursOfYear().v();
    }

    /**
     * 対象月度の時間外時間
     */
    private int overtimeHoursOfMonth;
    /**
     * 対象年度の時間外時間
     */
    private int overtimeHoursOfYear;
}
