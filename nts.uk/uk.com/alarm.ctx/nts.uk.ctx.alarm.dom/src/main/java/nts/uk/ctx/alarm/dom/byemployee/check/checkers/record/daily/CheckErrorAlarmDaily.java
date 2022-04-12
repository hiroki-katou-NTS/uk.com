package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily;

import lombok.Value;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

import java.util.List;
import java.util.Optional;

/**
 * 日別実績のエラーアラームをチェックする
 */
@Value
public class CheckErrorAlarmDaily {

    /** チェック対象コード一覧 */
    List<ErrorAlarmWorkRecordCode> targetCodes;

    /**
     * チェックする
     * @param require Require
     * @param employeeId 対象社員ID
     * @param period 対象期間
     * @return アラーム
     */
    public Iterable<AlarmRecordByEmployee> check(RequireCheck require, String employeeId, DatePeriod period) {

        return IteratorUtil.iterable(
                require.getEmployeeDailyPerErrors(employeeId, period, targetCodes),
                e -> toAlarmRecord(require, e));
    }

    private static AlarmRecordByEmployee toAlarmRecord(RequireCheck require, EmployeeDailyPerError dailyError) {

        String errorAlarmName = require.getErrorAlarmWorkRecord(dailyError.getErrorAlarmWorkRecordCode())
                .map(ea -> ea.getName().v())
                .orElseGet(() -> dailyError.getErrorAlarmWorkRecordCode().v() + " マスタ未登録");

        return AlarmRecordByEmployee.fromErrorAlarm(
                require,
                dailyError.getEmployeeID(),
                new DateInfo(dailyError.getDate()),
                AlarmListCategoryByEmployee.RECORD_DAILY,
                errorAlarmName,
                dailyError.getAttendanceItemList(),
                dailyError.getErrorAlarmMessage().map(AlarmListAlarmMessage::of).orElse(AlarmListAlarmMessage.empty()));
    }

    public interface RequireCheck extends AlarmRecordByEmployee.RequireFromErrorAlarm {

        Iterable<EmployeeDailyPerError> getEmployeeDailyPerErrors(
                String employeeId, DatePeriod period, List<ErrorAlarmWorkRecordCode> targetCodes);

        Optional<ErrorAlarmWorkRecord> getErrorAlarmWorkRecord(ErrorAlarmWorkRecordCode code);
    }
}
