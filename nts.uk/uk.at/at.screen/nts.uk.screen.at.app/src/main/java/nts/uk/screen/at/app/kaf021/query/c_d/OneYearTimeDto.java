package nts.uk.screen.at.app.kaf021.query.c_d;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.OneYearTime;

@Getter
public class OneYearTimeDto {
    public OneYearTimeDto(OneYearTime time) {
        this.errorTime = new ErrorAlarmTimeDto(time.getErrorTimeInYear());
        this.year = time.getYear().v();
    }

    /**
     * 1年間時間
     */
    private ErrorAlarmTimeDto errorTime;
    /**
     * 年度
     */
    private int year;
}
