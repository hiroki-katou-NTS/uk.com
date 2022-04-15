/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.monthly;

import java.util.Optional;
import lombok.AllArgsConstructor;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.Threshold;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;

/**
 *　月次上限の調整ルール
 * @author raiki_asada
 */
@AllArgsConstructor
public class AdjustRuleByMonthlyLimit {
    private final Optional<AgreementOneMonthTime> adjustTimeByLimit;
    private final Optional<AgreementOneMonthTime> adjustTimeByError;
    private final Optional<AgreementOneMonthTime> adjustTimeByAlarm;

    public Threshold<AttendanceTimeMonth> adjust(OneMonthTime threshold) {
        return (targetTime) -> this.adjustOriginal(threshold).check(targetTime);
    }
    
    private OneMonthTime adjustOriginal(OneMonthTime threshold) {
        AgreementOneMonthTime adustedByLimit = this.adjustTimeByLimit.map(adjust -> {
            return threshold.getUpperLimit().minusMinutes(adjust.v());
        }).orElse(threshold.getUpperLimit());
        
        AgreementOneMonthTime adustedByError = this.adjustTimeByError.map(adjust -> {
            return threshold.getErAlTime().getError().minusMinutes(adjust.v());
        }).orElse(threshold.getErAlTime().getError());
        
        AgreementOneMonthTime adustedByAlarm = adjustTimeByAlarm.map(adjust -> {
            return threshold.getErAlTime().getAlarm().minusMinutes(adjust.v());
        }).orElse(threshold.getErAlTime().getAlarm());
        
        
        return OneMonthTime.createWithCheck(OneMonthErrorAlarmTime.of(adustedByError, adustedByAlarm), adustedByLimit);
    }
}
