package nts.uk.ctx.at.function.ac.monthly.agreement;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreMaxTimeOfMonthlyImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeByEmpImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeByPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeByPeriodImport;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreMaxTimeMonthOut;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeByPeriodPub;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.PeriodAtrOfAgreement;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * @author dat.lh
 *
 */
@Stateless
public class AgreementTimeByPeriodAcFinder implements AgreementTimeByPeriodAdapter {
	@Inject
	private AgreementTimeByPeriodPub agreementTimeByPeriodPub;

	@Override
	public List<AgreementTimeByPeriodImport> algorithm(String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr) {
		return agreementTimeByPeriodPub.algorithm(companyId, employeeId, criteria, startMonth, year, periodAtr).stream()
				.map(x -> new AgreementTimeByPeriodImport(x.getStartMonth(), x.getEndMonth(), x.getAgreementTime(),
						x.getLimitAlarmTime().toString(), x.getLimitAlarmTime().toString(),
						x.getExceptionLimitErrorTime().toString(), x.getExceptionLimitAlarmTime().toString(),
						x.getStatus()))
				.collect(Collectors.toList());
	}

	@Override
	public List<AgreMaxTimeOfMonthlyImport> maxTime(String companyId, String employeeId, YearMonthPeriod period) {
		//List<AgreMaxTimeOfMonthly> data = 
		List<AgreMaxTimeMonthOut> data = agreementTimeByPeriodPub.maxTime(companyId, employeeId, period);
		if(data.isEmpty())
			return Collections.emptyList();
		return data.stream().map(c->convertToAgreMaxTimeOfMonthly(c)).collect(Collectors.toList());
	}

	@Override
	public Optional<AgreMaxAverageTimeMulti> maxAverageTimeMulti(String companyId, String employeeId,
			GeneralDate criteria, YearMonth yearMonth) {
		return agreementTimeByPeriodPub.maxAverageTimeMulti(companyId, employeeId, criteria, yearMonth);
	}
	
	private AgreMaxTimeOfMonthlyImport convertToAgreMaxTimeOfMonthly (AgreMaxTimeMonthOut export) {
		return new AgreMaxTimeOfMonthlyImport(
				export.getMaxTime().getAgreementTime(),
				new LimitOneMonth(export.getMaxTime().getMaxTime().v()),
				export.getMaxTime().getStatus()
				);
	}
	
	@Override	
	public List<AgreementTimeByEmpImport> algorithmImprove(String companyId, List<String> employeeIds, GeneralDate criteria,
														   Month startMonth, Year year, List<PeriodAtrOfAgreement> periodAtrs, Map<String, YearMonthPeriod> periodWorking) {
		return agreementTimeByPeriodPub.algorithmImprove(companyId, employeeIds, criteria, startMonth, year, periodAtrs, periodWorking).stream()
				.map(x -> new AgreementTimeByEmpImport(x.getEmployeeId(), x.getPeriodAtr(),
						new AgreementTimeByPeriodImport(x.getAgreementTime().getStartMonth(), x.getAgreementTime().getEndMonth(),
								x.getAgreementTime().getAgreementTime(),
								x.getAgreementTime().getLimitErrorTime().toString(), x.getAgreementTime().getLimitAlarmTime().toString(),
								x.getAgreementTime().getExceptionLimitErrorTime().toString(), x.getAgreementTime().getExceptionLimitAlarmTime().toString(),
								x.getAgreementTime().getStatus())))
				.collect(Collectors.toList());
	}

}
