package nts.uk.ctx.alarm.dom.check.atditem;

import nts.gul.util.Either;
import nts.uk.ctx.alarm.dom.check.AlarmRecord;
import nts.uk.ctx.alarm.dom.check.context.period.CheckingPeriod;

/**
 * 勤怠項目の加減算によるチェック
 */
public class CheckBySummingAttendanceItem {

    public Either<AlarmRecord, Void> check(Require require, String employeeId, CheckingPeriod checkingPeriod) {
        throw new RuntimeException("not implemented");
    }

    public interface Require {

    }
}
