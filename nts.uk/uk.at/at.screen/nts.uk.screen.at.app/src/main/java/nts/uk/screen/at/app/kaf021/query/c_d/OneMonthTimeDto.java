package nts.uk.screen.at.app.kaf021.query.c_d;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.OneMonthTime;

@Getter
public class OneMonthTimeDto {
    public OneMonthTimeDto(OneMonthTime time) {
        this.errorTime = new ErrorAlarmTimeDto(time.getErrorTimeInMonth());
        this.yearMonth = time.getYearMonth().v();
    }

    /**
     * 1ヶ月時間
     */
    private ErrorAlarmTimeDto errorTime;
    /**
     * 年月度
     */
    private int yearMonth;
}
