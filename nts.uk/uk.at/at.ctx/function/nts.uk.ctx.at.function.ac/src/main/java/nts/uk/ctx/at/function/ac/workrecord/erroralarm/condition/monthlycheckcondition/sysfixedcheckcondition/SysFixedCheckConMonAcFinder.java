package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.monthlycheckcondition.sysfixedcheckcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.sysfixedcheckcondition.SysFixedCheckConMonAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessImport;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.enums.SelfConfirmError;
import nts.uk.ctx.at.record.pub.fixedcheckitem.ValueExtractAlarmWRPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.sysfixedcheckcondition.SysFixedCheckConMonPub;
import nts.uk.ctx.at.record.pub.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessExport;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
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
	
	@Override
	public List<ValueExtractAlarm> checkMonthlyUnconfirmeds(String employeeID, int yearMonth,IdentityConfirmProcessImport identityConfirmProcessImport) {
		IdentityConfirmProcessExport export= convertIdentityToExport(identityConfirmProcessImport);
		List<ValueExtractAlarmWRPubExport> datas = sysFixedCheckConMonPub.checkMonthlyUnconfirmeds(employeeID, yearMonth,export);
		List<ValueExtractAlarm> lstReturn = new ArrayList<>();
		if(!CollectionUtil.isEmpty(datas)) {
			for (ValueExtractAlarmWRPubExport obj : datas) {
				lstReturn.add(convertToExport(obj));
			}
		}
		return lstReturn;
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
	
	private IdentityConfirmProcessExport convertIdentityToExport(IdentityConfirmProcessImport imp) {
		Optional<SelfConfirmError> yourSelfConfirmError = Optional.empty();
		if(imp.getYourSelfConfirmError().isPresent()){
			yourSelfConfirmError = Optional.of(EnumAdaptor.valueOf(imp.getYourSelfConfirmError().get().value, SelfConfirmError.class));
		}
		return new IdentityConfirmProcessExport(
				imp.getCompanyId(),
				imp.isUseConfirmByYourself(),
				imp.isUseIdentityOfMonth(),
				yourSelfConfirmError
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
