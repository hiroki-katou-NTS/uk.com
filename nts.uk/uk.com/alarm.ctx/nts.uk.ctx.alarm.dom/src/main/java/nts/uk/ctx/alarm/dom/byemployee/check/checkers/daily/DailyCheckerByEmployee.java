package nts.uk.ctx.alarm.dom.byemployee.check.checkers.daily;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;

import java.util.ArrayList;
import java.util.List;

/**
 * アラームリストのチェック条件(社員別・日次)
 */
@AllArgsConstructor
public class DailyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    private final String companyId;

    private final AlarmListCheckerCode code;

    /** 固定のチェック条件 */
    private List<FixedLogic> fixedLogics;

    /**
     * チェックする
     * @param require
     * @param context
     * @return
     */
    @Override
    public AtomTask check(Require require, CheckingContextByEmployee context) {

        val period = context.getCheckingPeriod().getDaily();

        List<AlarmRecordByEmployee> alarmRecords = new ArrayList<>();

        fixedLogics.stream()
                .map(f -> f.logic.check(require, context.getTargetEmployeeId(), period, f.message))
                .forEach(alarmRecords::addAll);

        return AtomTask.of(() -> {
            require.save(alarmRecords);
        });
    }

    public interface RequireCheck extends FixedLogicDailyByEmployee.RequireCheck {

    }

    @Value
    public static class FixedLogic {

        /** チェックロジック */
        FixedLogicDailyByEmployee logic;

        /** メッセージ */
        String message;
    }
}
