/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.yearly;

import java.util.Optional;
import java.util.Set;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.ExcessStateChecker;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.MessageForAlarm;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.TargetOfAlarm;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.Threshold;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;

/**
 * 年次のやつ
 * @author raiki_asada
 */
public class YearlyAlarmChecker extends ExcessStateChecker<AgreementOneYearTime>{

    public YearlyAlarmChecker(Set<ExcessState> targets, MessageForAlarm message) {
        super(targets, message);
    }

    @Override
    protected ResultOfChecker createResult(Threshold<AgreementOneYearTime> threhsold, AgreementOneYearTime targetTimes, ExcessState state) {
        return (employeeId, date, item) -> {
            return Optional.of(new AlarmRecordByEmployee(
                    employeeId,
                    date,
                    AlarmListCategoryByEmployee.AGREE36_YEARLY,
                    item.toString(),
                    this.getCondition(item, threhsold, targetTimes, state),
                    super.message.getMessage(state)
            ));
        };
    }
    
    public String getCondition(TargetOfAlarm item, Threshold<AgreementOneYearTime> threhsold, AgreementOneYearTime targetTimes, ExcessState state) {
        return "アラーム値　" + targetTimes.toString();
    }
}
