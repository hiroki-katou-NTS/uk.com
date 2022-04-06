package nts.uk.ctx.alarm.dom.byemployee.check.checkers.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodDaily;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;

import java.util.ArrayList;
import java.util.List;

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
        val period = context.getCheckingPeriod().getDaily();

        List<Iterable<AlarmRecordByEmployee>> alarmRecords = new ArrayList<>();

        alarmRecords.add(checkFixedLogics(require, employeeId, period));

        return IteratorUtil.flatten(alarmRecords);
    }

    /**
     * 固定チェック条件
     * @param require
     * @param employeeId
     * @param period
     * @return
     */
    private Iterable<AlarmRecordByEmployee> checkFixedLogics(Require require, String employeeId, CheckingPeriodDaily period) {
        return IteratorUtil.iterableFlatten(
                fixedLogics,
                f -> f.checkIfEnabled((logic, message) -> logic.check(require, employeeId, period, message)));
    }

    public interface RequireCheck extends FixedLogicDailyByEmployee.RequireCheck {

    }
}
