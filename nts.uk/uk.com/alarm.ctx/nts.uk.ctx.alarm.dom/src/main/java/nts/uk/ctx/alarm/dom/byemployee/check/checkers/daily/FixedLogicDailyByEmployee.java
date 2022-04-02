package nts.uk.ctx.alarm.dom.byemployee.check.checkers.daily;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;
import java.util.function.Function;

/**
 * 固定のチェック条件(社員別・日次)
 */
@RequiredArgsConstructor
public enum FixedLogicDailyByEmployee {

    勤務種類未登録(1, c -> checkIntegrationOfDaily(
                c, iod -> iod.getWorkInformation().getRecordInfo().getWorkTypeCode(), code -> c.require.existsWorkType(code))),

    就業時間帯未登録(2, c -> checkIntegrationOfDaily(
                c, iod -> iod.getWorkInformation().getRecordInfo().getWorkTimeCode(), code -> c.require.existsWorkTime(code))),

    
    ;

    public final int value;

    private final Function<Context, Iterable<AlarmRecordByEmployee>> logic;

    /**
     * チェックする
     * @param require
     * @param employeeId
     * @param checkingPeriod
     * @param message
     * @return
     */
    public Iterable<AlarmRecordByEmployee> check(
            FixedLogicDailyByEmployee.RequireCheck require,
            String employeeId,
            CheckingPeriodDaily checkingPeriod,
            String message) {

        val context = new Context(require, employeeId, checkingPeriod.calculatePeriod(require, employeeId), message);

        return logic.apply(context);
    }

    private String getName() {
        return "チェック項目名";
    }

    private String getAlarmCondition() {
        return "アラーム条件";
    }

    public Context createContext(RequireCheck require, String employeeId, CheckingPeriodDaily checkingPeriod, String message) {
        return new Context(require, employeeId, checkingPeriod.calculatePeriod(require, employeeId), message);
    }

    /**
     * IntegrationOfDaily(日別実績)の整合性チェック
     * @param context
     * @param check
     * @return
     */
    private static <T> Iterable<AlarmRecordByEmployee> checkIntegrationOfDaily(
            Context context,
            Function<IntegrationOfDaily, T> fetch,
            Function<T, Boolean> check) {

        return () -> context.period.stream()
                .map(date ->  context.require.getIntegrationOfDaily(context.employeeId, date))
                .flatMap(OptionalUtil::stream)
                .map(iod -> Pair.of(iod, check.apply(fetch.apply(iod))))
                .filter(p -> !p.getRight())
                .map(p -> context.alarm(p.getLeft().getYmd()))
                .iterator();
    }
    
    @Value
    private class Context {
        RequireCheck require;
        String employeeId;
        DatePeriod period;
        String message;

        public AlarmRecordByEmployee alarm(GeneralDate date) {
            return new AlarmRecordByEmployee(
                    employeeId,
                    date.toString(),
                    AlarmListCategoryByEmployee.DAILY,
                    getName(),
                    getAlarmCondition(),
                    message);
        }
    }

    public interface RequireCheck extends CheckingPeriodDaily.Require{

        Optional<IntegrationOfDaily> getIntegrationOfDaily(String employeeId, GeneralDate date);

        boolean existsWorkType(WorkTypeCode workTypeCode);

        boolean existsWorkTime(WorkTimeCode workTimeCode);
    }
}
