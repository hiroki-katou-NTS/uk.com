package nts.uk.screen.at.app.kaf021.query.c_d;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;

@Getter
public class ErrorAlarmTimeDto {
    public ErrorAlarmTimeDto(OneMonthErrorAlarmTime timeMonth) {
        this.error = timeMonth.getError().v();
        this.alarm = timeMonth.getAlarm().v();
    }

    public ErrorAlarmTimeDto(OneYearErrorAlarmTime timeYear) {
        this.error = timeYear.getError().v();
        this.alarm = timeYear.getAlarm().v();
    }

    /**
     * エラー時間
     */
    private int error;
    /**
     * アラーム時間
     */
    private int alarm;
}
