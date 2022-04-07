package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily;

import lombok.Value;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

import java.util.List;
import java.util.stream.Collectors;

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

    public interface RequireCheck {

        Iterable<EmployeeDailyPerError> getEmployeeDailyPerErrors(
                String employeeId, DatePeriod period, List<ErrorAlarmWorkRecordCode> targetCodes);

        String getAttendanceItemName(int attendanceItemId);
    }

    private static AlarmRecordByEmployee toAlarmRecord(RequireCheck require, EmployeeDailyPerError e) {

        String checkItemName = e.getAttendanceItemList().stream()
                .map(id -> require.getAttendanceItemName(id))
                .collect(Collectors.joining(", "));

        return new AlarmRecordByEmployee(
                e.getEmployeeID(),
                e.getDate().toString("yyyy/MM/dd"),
                AlarmListCategoryByEmployee.RECORD_DAILY,
                checkItemName,
                "",
                e.getErrorAlarmMessage().map(m -> m.v()).orElse("")
        );
    }
}
