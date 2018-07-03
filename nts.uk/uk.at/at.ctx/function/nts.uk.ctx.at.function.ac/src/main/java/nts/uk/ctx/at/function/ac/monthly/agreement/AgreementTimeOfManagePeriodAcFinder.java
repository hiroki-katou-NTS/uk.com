package nts.uk.ctx.at.function.ac.monthly.agreement;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeBreakdownImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeOfManagePeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeOfManagePeriodImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeOfMonthlyImport;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeOfManagePeriodPub;
import nts.uk.ctx.at.shared.dom.common.Year;

@Stateless
public class AgreementTimeOfManagePeriodAcFinder implements AgreementTimeOfManagePeriodAdapter {

	@Inject
	private AgreementTimeOfManagePeriodPub agreementTimeOfManagePeriodPub;

	@Override
	public List<AgreementTimeOfManagePeriodImport> findByYear(String employeeId, Year year) {
		return agreementTimeOfManagePeriodPub.findByYear(employeeId, year).stream()
		.map(m -> AgreementTimeOfManagePeriodImport.of(m.getEmployeeId(),
					m.getYearMonth(), m.getYear(),
					m.getAgreementTime() == null? null : AgreementTimeOfMonthlyImport.of(
							m.getAgreementTime().getAgreementTime(),
							m.getAgreementTime().getLimitErrorTime(),
							m.getAgreementTime().getLimitAlarmTime(),
							m.getAgreementTime().getExceptionLimitErrorTime(),
							m.getAgreementTime().getExceptionLimitAlarmTime(),
							m.getAgreementTime().getStatus()),
					m.getBreakdown() == null? null : AgreementTimeBreakdownImport.of(
							m.getBreakdown().getOverTime(),
							m.getBreakdown().getTransferOverTime(),
							m.getBreakdown().getHolidayWorkTime(),
							m.getBreakdown().getTransferTime(),
							m.getBreakdown().getFlexExcessTime(),
							m.getBreakdown().getWithinPrescribedPremiumTime(),
							m.getBreakdown().getWeeklyPremiumTime(),
							m.getBreakdown().getMonthlyPremiumTime()))
		).collect(Collectors.toList());
	}
	
}
