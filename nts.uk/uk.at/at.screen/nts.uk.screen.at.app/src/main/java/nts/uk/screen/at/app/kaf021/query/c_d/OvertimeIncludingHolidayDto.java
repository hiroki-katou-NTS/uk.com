package nts.uk.screen.at.app.kaf021.query.c_d;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.OvertimeIncludingHoliday;

@Getter
public class OvertimeIncludingHolidayDto {
    public OvertimeIncludingHolidayDto(OvertimeIncludingHoliday overtimeIncludingHoliday) {
        this.overtimeHoursTargetMonth = overtimeIncludingHoliday.getOvertimeHoursTargetMonth().v();
        this.monthAverage2Str = overtimeIncludingHoliday.getOvertimeTwoMonthAverage().v();
        this.monthAverage3Str = overtimeIncludingHoliday.getOvertimeThreeMonthAverage().v();
        this.monthAverage4Str = overtimeIncludingHoliday.getOvertimeFourMonthAverage().v();
        this.monthAverage5Str = overtimeIncludingHoliday.getOvertimeFiveMonthAverage().v();
        this.monthAverage6Str = overtimeIncludingHoliday.getOvertimeSixMonthAverage().v();
    }

    /** 対象月度の時間外時間*/
    private int overtimeHoursTargetMonth;

    /**
     * 2ヶ月平均の時間外時間
     */
    private int monthAverage2Str;
    /**
     * 3ヶ月平均の時間外時間
     */
    private int monthAverage3Str;
    /**
     * 4ヶ月平均の時間外時間
     */
    private int monthAverage4Str;
    /**
     * 5ヶ月平均の時間外時間
     */
    private int monthAverage5Str;
    /**
     * 6ヶ月平均の時間外時間
     */
    private int monthAverage6Str;
}
