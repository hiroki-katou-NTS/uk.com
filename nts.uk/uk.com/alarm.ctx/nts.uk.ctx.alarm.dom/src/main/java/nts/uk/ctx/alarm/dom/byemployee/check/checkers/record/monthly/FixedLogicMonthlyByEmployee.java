package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.monthly;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.alarm.dom.byemployee.check.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;
import nts.uk.ctx.alarm.dom.byemployee.check.result.DateInfo;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Value;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.shr.com.time.closure.ClosureMonth;

/**
 * 固定のチェック条件(社員別・月次)
 */
@RequiredArgsConstructor
public enum FixedLogicMonthlyByEmployee {

    本人未確認(1, (c) -> {
        return () -> c.period.stream()
                .filter(closureMonth -> c.require.getConfirmationMonth(c.getEmployeeId(), closureMonth).isPresent())
                .map(closureMonth -> c.alarm(closureMonth)).iterator();
    }),

    
    ;

    public final int value;

    private final Function<Context, Iterable<AlarmRecordByEmployee>> logic;

    /**
     * チェックする
     * 実装パターンが定まらないので、仮置き
     * @param require
     * @param checkingPeriod
     * @return
     */
    public Iterable<AlarmRecordByEmployee> check(RequireCheck require, CheckingPeriodMonthly checkingPeriod) {
        return () -> new ArrayList<AlarmRecordByEmployee>().iterator();
    }
    
    private String getName() {
        return "チェック項目名";
    }

    private String getAlarmCondition() {
        return "アラーム条件";
    }

    @Value
    private class Context {
        RequireCheck require;
        String employeeId;
        List<ClosureMonth> period;
        String message;
        
        public AlarmRecordByEmployee alarm(ClosureMonth closureMonth) {
            return new AlarmRecordByEmployee(
                    employeeId,
                    // どのように　アラームリスト上でどう出すかは後回し
                    new DateInfo(closureMonth),
                    AlarmListCategoryByEmployee.MASTER,
                    getName(),
                    getAlarmCondition(),
                    message);
        }
    }

    public interface RequireCheck {
        Optional<ConfirmationMonth> getConfirmationMonth(String employeeId, ClosureMonth closureMonth);
    }
}
