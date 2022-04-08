package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily;

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
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.errorcount.ErrorAlarmCounter;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodDaily;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * アラームリストのチェック条件(社員別・日次)
 */
@AllArgsConstructor
@Getter
public class DailyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    /** 会社ID */
    private final String companyId;

    /** コード */
    private final AlarmListCheckerCode code;

    /** エラーアラームのチェック条件 */
    private CheckErrorAlarmDaily errorAlarm;

    /** エラーアラームの発生カウント */
    private List<ErrorAlarmCounter<ErrorAlarmWorkRecordCode, GeneralDate>> errorAlarmCounters;

    /** 固定のチェック条件 */
    private List<FixedLogicSetting<FixedLogicDailyByEmployee>> fixedLogics;

    /**
     * チェックする
     * @param require
     * @param context
     * @return
     */
    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {

        String employeeId = context.getTargetEmployeeId();
        val period = context.getCheckingPeriod().getDaily().calculatePeriod(require, employeeId);

        List<Iterable<AlarmRecordByEmployee>> alarmRecords = Arrays.asList(
                errorAlarm.check(require, employeeId, period),
                checkErrorAlarmCount(require, employeeId, period),
                checkFixedLogics(require, employeeId, period)
        );

        return IteratorUtil.flatten(alarmRecords);
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

        Function<ErrorAlarmWorkRecordCode, Iterable<GeneralDate>> errorAlarmChecker = code -> {
            return IteratorUtil.iterable(
                    new CheckErrorAlarmDaily(Arrays.asList(code)).check(require, employeeId, period),
                    ea -> ea.getDateInfo().toGeneralDate());
        };

        Function<ErrorAlarmWorkRecordCode, Optional<String>> getErrorAlarmName = code -> {
            return require.getErrorAlarmWorkRecord(code).map(e -> e.getName().v());
        };

        return IteratorUtil.iterableFlatten(
                errorAlarmCounters,
                c -> c.check(
                        employeeId,
                        period,
                        AlarmListCategoryByEmployee.RECORD_DAILY,
                        getErrorAlarmName,
                        periodToDateInfo, errorAlarmChecker));
    }

    /**
     * 固定チェック条件
     * @param require
     * @param employeeId
     * @param period
     * @return
     */
    private Iterable<AlarmRecordByEmployee> checkFixedLogics(Require require, String employeeId, DatePeriod period) {
        return IteratorUtil.iterableFlatten(
                fixedLogics,
                f -> f.checkIfEnabled((logic, message) -> logic.check(require, employeeId, period, message)));
    }

    public interface RequireCheck extends
            CheckingPeriodDaily.Require,
            CheckErrorAlarmDaily.RequireCheck,
            FixedLogicDailyByEmployee.RequireCheck {

    }
}
