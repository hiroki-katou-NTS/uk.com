package nts.uk.ctx.alarm.dom.byemployee.check.checkers.master;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.byemployee.check.result.AlarmRecordByEmployee;
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
	public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
		
        List<Iterable<AlarmRecordByEmployee>> alarmRecords = new ArrayList<>();
		
        alarmRecords.add(checkFixedLogics(require, context.getTargetEmployeeId()));
        
        return IteratorUtil.flatten(alarmRecords);
	}
	
    /**
     * 固定チェック条件
     */
    private Iterable<AlarmRecordByEmployee> checkFixedLogics(Require require, String employeeId) {
        return IteratorUtil.iterableFlatten(
                fixedLogics,
                f -> f.checkIfEnabled((logic, message) -> logic.check(require, employeeId, message)));
    }

    public interface RequireCheck extends FixLogicMasterByEmployee.RequireCheck {

    }
}
