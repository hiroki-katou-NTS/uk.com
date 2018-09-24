package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.monthlycheckcondition.sysfixedcheckcondition;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.sysfixedcheckcondition.SysFixedCheckConMonAdapter;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.record.pub.fixedcheckitem.ValueExtractAlarmWRPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.sysfixedcheckcondition.SysFixedCheckConMonPub;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
@Stateless
public class SysFixedCheckConMonAcFinder implements SysFixedCheckConMonAdapter {
	
	@Inject
	private SysFixedCheckConMonPub sysFixedCheckConMonPub;

	@Override
	public Optional<ValueExtractAlarm> checkAgreement(String employeeID, int yearMonth,int closureId,ClosureDate closureDate) {
		Optional<ValueExtractAlarmWRPubExport> data = sysFixedCheckConMonPub.checkAgreement(employeeID, yearMonth,closureId,closureDate);
		if(data.isPresent()) {
			return Optional.of(convertToExport(data.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<ValueExtractAlarm> checkMonthlyUnconfirmed(String employeeID, int yearMonth) {
		Optional<ValueExtractAlarmWRPubExport> data = sysFixedCheckConMonPub.checkMonthlyUnconfirmed(employeeID, yearMonth);
		if(data.isPresent()) {
			return Optional.of(convertToExport(data.get()));
		}
		return Optional.empty();
	}

	private ValueExtractAlarm convertToExport(ValueExtractAlarmWRPubExport export) {
		return new ValueExtractAlarm(
				export.getWorkplaceID().orElse(null),
				export.getEmployeeID(),
				export.getAlarmValueDate().toString(),
				export.getClassification(),
				export.getAlarmItem(),
				export.getAlarmValueMessage(),
				export.getComment().orElse(null)
				);
	}
	@Override
	public Optional<ValueExtractAlarm> checkDeadlineCompensatoryLeaveCom(String employeeID, Closure closing,
			CompensatoryLeaveComSetting compensatoryLeaveComSetting) {
		
		Optional<ValueExtractAlarmWRPubExport> data = sysFixedCheckConMonPub.checkDeadlineCompensatoryLeaveCom(employeeID, closing, compensatoryLeaveComSetting);
		if(data.isPresent()) {
			return Optional.of(convertToExport(data.get()));
		}
		return Optional.empty();
	}
}
