package nts.uk.screen.at.app.kaf021.query.c_d;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.UpperLimitBeforeRaising;

@Getter
public class UpperLimitBeforeRaisingDto {

    public UpperLimitBeforeRaisingDto(UpperLimitBeforeRaising upperContents) {
        this.oneMonthLimit = new ErrorAlarmTimeDto(upperContents.getOneMonthLimit());
        this.oneYearLimit = new ErrorAlarmTimeDto(upperContents.getOneYearLimit());
        this.averageTimeLimit = upperContents.getAverageTimeLimit().v();
    }

    /**
     * 1ヶ月の上限
     */
    private ErrorAlarmTimeDto oneMonthLimit;
    /**
     * 1年間の上限
     */
    private ErrorAlarmTimeDto oneYearLimit;
    /**
     * 平均限度時間
     */
    private int averageTimeLimit;
}
