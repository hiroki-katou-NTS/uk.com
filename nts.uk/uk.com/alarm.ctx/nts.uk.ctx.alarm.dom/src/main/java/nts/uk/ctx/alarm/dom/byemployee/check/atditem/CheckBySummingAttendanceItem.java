package nts.uk.ctx.alarm.dom.byemployee.check.atditem;

import nts.gul.util.Either;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriod;

/**
 * 勤怠項目の加減算によるチェック
 */
public class CheckBySummingAttendanceItem {

    public Either<AlarmRecordByEmployee, Void> check(Require require, String employeeId, CheckingPeriod checkingPeriod) {
        throw new RuntimeException("not implemented");
    }

    public interface Require {

    }
}
