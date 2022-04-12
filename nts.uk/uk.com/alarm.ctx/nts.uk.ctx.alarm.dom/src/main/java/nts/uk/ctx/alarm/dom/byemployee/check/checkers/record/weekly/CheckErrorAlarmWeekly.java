package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.weekly;

import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodWeekly;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkCheckResult;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyKey;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 週別実績のエラーアラームをチェックする
 */
public class CheckErrorAlarmWeekly {

    /**
     * チェックする
     * @param require
     * @param context
     * @return
     */
    public Iterable<AlarmRecordByEmployee> check(RequireCheck require, CheckingContextByEmployee context) {
        String employeeId = context.getTargetEmployeeId();
        CheckingPeriodWeekly checkingPeriod = context.getCheckingPeriod().getWeekly();
        List<AttendanceTimeOfWeeklyKey> keys = checkingPeriod.calculatePeriod(require, employeeId);

        return IteratorUtil.iterableFlatten(
                require.getAttendanceTimeOfWeekly(employeeId, keys),
                data -> check(require, employeeId, data)
            );
    }

    private static List<AlarmRecordByEmployee> check(RequireCheck require, String employeeId, AttendanceTimeOfWeekly data){
        List<ExtractionCondWeekly> conditions = require.getUsedExtractionCondWeekly();

        return conditions.stream()
            .map(cond -> {
                WorkCheckResult r = cond.checkTarget(require, data);
                Optional<AlarmRecordByEmployee> record = (r == WorkCheckResult.ERROR)
                        ? Optional.of(toAlarmRecord(require, employeeId, data.getPeriod(), cond))
                        : Optional.empty();
                return record;
            })
            .filter(opRec -> opRec.isPresent())
            .map(opRec -> opRec.get())
            .collect(Collectors.toList());
    }
    private static AlarmRecordByEmployee toAlarmRecord(RequireCheck require, String employeeId, DatePeriod period, ExtractionCondWeekly cond){

        return AlarmRecordByEmployee.fromErrorAlarm(
                require,
                employeeId,
                new DateInfo(period),
                AlarmListCategoryByEmployee.RECORD_WEEKLY,
                cond.getName().v(),
                Collections.emptyList(),
                cond.getMessage()
            );
    }

    public interface RequireCheck extends
            CheckingPeriodWeekly.Require,
            ExtractionCondWeekly.Require,
            AlarmRecordByEmployee.RequireFromErrorAlarm {
        List<ExtractionCondWeekly> getUsedExtractionCondWeekly();
        Iterable<AttendanceTimeOfWeekly> getAttendanceTimeOfWeekly(String employeeId, List<AttendanceTimeOfWeeklyKey> keys);
    }
}
