/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.monthly;

import java.util.Optional;
import java.util.Set;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.ExcessStateChecker;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.MessageForAlarm;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.TargetOfAlarm;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.Threshold;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;

/**
 * 36協定月次用のアラームチェック条件
 */
public class MonthlyAlarmChecker extends ExcessStateChecker<AttendanceTimeMonth>{

    public MonthlyAlarmChecker(Set<ExcessState> targets, MessageForAlarm message) {
        super(targets, message);
    }
    
    @Override
    protected ResultOfChecker createResult(Threshold<AttendanceTimeMonth> threhsold, AttendanceTimeMonth targetTimes, ExcessState state) {
        return (employeeId, date, item) -> {
            return Optional.of(new AlarmRecordByEmployee(
                    employeeId,
                    date,
                    AlarmListCategoryByEmployee.AGREE36_MONTHLY,
                    item.toString(),
                    this.getCondition(item, threhsold, targetTimes, state),
                    super.message.getMessage(state)
            ));
        };
    }

    private String getCondition(TargetOfAlarm item, Threshold<AttendanceTimeMonth> threhsold, AttendanceTimeMonth targetTimes, ExcessState state) {
        return "アラームに出す内容";
    }
}
