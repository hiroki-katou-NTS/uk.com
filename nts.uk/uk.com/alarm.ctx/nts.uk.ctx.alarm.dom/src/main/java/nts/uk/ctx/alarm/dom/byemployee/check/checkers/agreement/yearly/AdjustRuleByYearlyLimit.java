/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.yearly;

import java.util.Optional;
import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;

/**
 * 年次上限の調整ルール
 * @author raiki_asada
 */
@AllArgsConstructor
public class AdjustRuleByYearlyLimit {
    private final Optional<AgreementOneYearTime> adjustTimeByLimit;
    private final Optional<AgreementOneYearTime> adjustTimeByError;
    private final Optional<AgreementOneYearTime> adjustTimeByAlarm;
    
    public OneYearTime adjust(OneYearTime threshold) {
        AgreementOneYearTime adustedByLimit = this.adjustTimeByLimit.map(adjust -> {
            return threshold.getUpperLimit().minusMinutes(adjust.v());
        }).orElse(threshold.getUpperLimit());
        
        AgreementOneYearTime adustedByError = this.adjustTimeByError.map(adjust -> {
            return threshold.getErAlTime().getError().minusMinutes(adjust.v());
        }).orElse(threshold.getErAlTime().getError());
        
        AgreementOneYearTime adustedByAlarm = adjustTimeByAlarm.map(adjust -> {
            return threshold.getErAlTime().getAlarm().minusMinutes(adjust.v());
        }).orElse(threshold.getErAlTime().getAlarm());
        
        return OneYearTime.createWithCheck(OneYearErrorAlarmTime.of(adustedByError, adustedByAlarm), adustedByLimit);
    }
}
