package nts.uk.ctx.alarm.dom.byemployee.check.checkers.appapproval;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodDaily;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;

/**
 * アラームリストのチェック条件(社員別・申請承認) 
 */
@AllArgsConstructor
public class AppApprovalCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee{

    /** 固定のチェック条件 */
    private List<FixedLogicSetting<FixedLogicAppApprovalByEmployee>> fixedLogics;
    
	@Override
	public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
		//こいつは、社員IDと期間(日付事に)をIterableする。
        String employeeId = context.getTargetEmployeeId();
        val period = context.getCheckingPeriod().getDaily();

        List<Iterable<AlarmRecordByEmployee>> alarmRecords = new ArrayList<>();

        alarmRecords.add(checkFixedLogics(require, employeeId, period));

        return IteratorUtil.flatten(alarmRecords);
	}

    /**
     * 固定チェック条件
     * @return
     */
    private Iterable<AlarmRecordByEmployee> checkFixedLogics(Require require, String employeeId, CheckingPeriodDaily period) {
        return IteratorUtil.iterableFlatten(
                fixedLogics,
                f -> f.checkIfEnabled((logic, message) -> logic.check(require, employeeId, message)));
    }

    public interface RequireCheck extends FixedLogicAppApprovalByEmployee.RequireCheck {

    }
}
