package nts.uk.ctx.at.request.ac.record.agreement;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.GetAgreementTimePub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreeTimeOfMonthExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeImport;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class AgreementTimeAdapterImpl implements AgreementTimeAdapter {
	
	@Inject
	private GetAgreementTimePub getAgreementTimePub;
	
	@Override
	public List<AgreementTimeImport> getAgreementTime(String companyId, List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId) {
		return getAgreementTimePub.get(companyId, employeeIds, yearMonth, closureId).stream()
			.map(x -> new AgreementTimeImport(
					x.getEmployeeId(), 
					x.getConfirmed().map(y -> new AgreeTimeOfMonthExport(
							y.getAgreementTime().v(), 
							y.getLimitErrorTime().v(), 
							y.getLimitAlarmTime().v(), 
							y.getExceptionLimitErrorTime().map(z -> z.v()), 
							y.getExceptionLimitAlarmTime().map(z -> z.v()), 
							y.getStatus().value)), 
					x.getAfterAppReflect().map(y -> new AgreeTimeOfMonthExport(
							y.getAgreementTime().v(), 
							y.getLimitErrorTime().v(), 
							y.getLimitAlarmTime().v(), 
							y.getExceptionLimitErrorTime().map(z -> z.v()), 
							y.getExceptionLimitAlarmTime().map(z -> z.v()), 
							y.getStatus().value)), 
					x.getErrorMessage()))
			.collect(Collectors.toList());
	}

}
