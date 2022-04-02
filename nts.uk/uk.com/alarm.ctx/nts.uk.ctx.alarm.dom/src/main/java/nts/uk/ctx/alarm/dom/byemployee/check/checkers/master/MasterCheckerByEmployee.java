package nts.uk.ctx.alarm.dom.byemployee.check.checkers.master;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;

/**
 * アラームリストのチェック条件(社員別・マスタ)
 */
@AllArgsConstructor
public class MasterCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee{

    /** 固定のチェック条件 */
    private List<FixedLogicSetting<FixLogicMasterByEmployee>> fixedLogics;
    
    
	@Override
	public AtomTask check(Require require, CheckingContextByEmployee context) {
		
        List<AlarmRecordByEmployee> alarmRecords = new ArrayList<>();
		
        fixedLogics.stream()
	        .map(f -> f.checkIfEnabled((logic, message) -> logic.check(require, context.getTargetEmployeeId(), message)))
	        .forEach(alarmRecords::addAll);

		return AtomTask.of(() -> {
		    require.save(alarmRecords);
		});
	}

    public interface RequireCheck extends FixLogicMasterByEmployee.RequireCheck {

    }
}
