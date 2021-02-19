package nts.uk.screen.at.app.kaf021.query.c_d;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ScreenDisplayInfo;

@Getter
public class ScreenDisplayInfoDto {
    public ScreenDisplayInfoDto(ScreenDisplayInfo screenDisplayInfo) {
        this.overtime = new OvertimeDto(screenDisplayInfo.getOvertime());
        this.overtimeIncludingHoliday = new OvertimeIncludingHolidayDto(screenDisplayInfo.getOvertimeIncludingHoliday());
        this.exceededMonth = screenDisplayInfo.getExceededMonth();
        this.upperContents = new UpperLimitBeforeRaisingDto(screenDisplayInfo.getUpperContents());
    }

    /**
     * 時間外時間
     */
    private OvertimeDto overtime;
    /**
     * 時間外時間（法定休出を含む）
     */
    private OvertimeIncludingHolidayDto overtimeIncludingHoliday;
    /**
     * 超過月数
     */
    private int exceededMonth;
    /**
     * 上限マスタ内容
     */
    private UpperLimitBeforeRaisingDto upperContents;
}
