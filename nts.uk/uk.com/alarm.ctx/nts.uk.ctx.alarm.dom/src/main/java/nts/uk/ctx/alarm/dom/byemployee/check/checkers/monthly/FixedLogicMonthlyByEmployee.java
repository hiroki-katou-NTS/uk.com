package nts.uk.ctx.alarm.dom.byemployee.check.checkers.monthly;

import lombok.RequiredArgsConstructor;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;

import java.util.List;
import java.util.function.BiFunction;

/**
 * 固定のチェック条件(社員別・月次)
 */
@RequiredArgsConstructor
public enum FixedLogicMonthlyByEmployee {

    本人未確認(1, (r, p) -> null),

    ;

    public final int value;

    private final BiFunction<RequireCheck, CheckingPeriodMonthly, Iterable<AlarmRecordByEmployee>> logic;

    /**
     * チェックする
     * @param require
     * @param checkingPeriod
     * @return
     */
    public Iterable<AlarmRecordByEmployee> check(RequireCheck require, CheckingPeriodMonthly checkingPeriod) {
        return logic.apply(require, checkingPeriod);
    }

    public interface RequireCheck {

    }
}
