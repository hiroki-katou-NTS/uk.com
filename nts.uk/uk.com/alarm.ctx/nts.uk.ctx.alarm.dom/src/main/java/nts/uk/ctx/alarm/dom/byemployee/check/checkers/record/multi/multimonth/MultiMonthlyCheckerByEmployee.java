package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.multimonth;

import java.util.List;

import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.shr.com.time.closure.ClosureMonth;

/**
 * アラームリストのチェック条件(社員別・複数月) 
 */
@Value
public class MultiMonthlyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee{
	
	private String companyId;
	
	private AlarmListCheckerCode code;
	
	private String name;
	
	private CheckMultiMonthErrorAlarm errorAlarm;
	
	@Override
	public Iterable<AlarmRecordByEmployee> check(
			AlarmListCheckerByEmployee.Require require,
			CheckingContextByEmployee context) {
		//iteratorで月別実績取ってくる
		val closures = context.getCheckingPeriod().getMonthly().calculatePeriod(require, context.getTargetEmployeeId());
		val records = require.getIntegrationOfMonthly(context.getTargetEmployeeId(), closures);
		return errorAlarm.check(require, context, records);
	}
	
	public interface RequireCheck extends CheckMultiMonthErrorAlarm.Require{
		List<IntegrationOfMonthly> getIntegrationOfMonthly(String employeeId, List<ClosureMonth> closureMonth);

	}
}
