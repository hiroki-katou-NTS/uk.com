package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.multiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.errorcount.ErrorAlarmCounter;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * アラームリストのチェック条件(社員別・複数日)
 */
@AllArgsConstructor
@Getter
public class MultiDayCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    /** 会社ID */
    private final String companyId;

    /** コード */
    private final AlarmListCheckerCode code;

    /** エラーアラームの発生カウント */
    private List<ErrorAlarmCounter<ErrorAlarmWorkRecordCode, GeneralDate>> errorAlarmCounters;


    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {

        String employeeId = context.getTargetEmployeeId();
        val period = context.getCheckingPeriod().getDaily().calculatePeriod(require, employeeId);

        return checkErrorAlarmCount(require, employeeId, period);
    }

    /**
     * エラーアラームの発生カウントでチェックする
     * @param require
     * @param employeeId
     * @param period
     * @return
     */
    private Iterable<AlarmRecordByEmployee> checkErrorAlarmCount(Require require, String employeeId, DatePeriod period) {

        BiFunction<GeneralDate, GeneralDate, DateInfo> periodToDateInfo = (start, end) -> {
            return new DateInfo(new DatePeriod(start, end));
        };

        Function<ErrorAlarmWorkRecordCode, Optional<String>> getErrorAlarmName = code -> {
            return require.getErrorAlarmWorkRecord(code).map(e -> e.getName().v());
        };

        Function<ErrorAlarmWorkRecordCode, Iterable<GeneralDate>> errorAlarmChecker = code -> {
            return ExecuteCheckErrorAlarmDaily.execute(require, employeeId, period, code);
        };

        return IteratorUtil.iterableFlatten(
                errorAlarmCounters,
                c -> c.check(
                        employeeId,
                        period,
                        AlarmListCategoryByEmployee.RECORD_DAILY,
                        getErrorAlarmName,
                        periodToDateInfo,
                        errorAlarmChecker));
    }

    public interface RequireCheck extends ExecuteCheckErrorAlarmDaily.Require {

    }
}
