package nts.uk.ctx.at.function.ac.monthly.agreement;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreMaxAverageTimeImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreMaxAverageTimeMultiImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreTimeOfMonthlyImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeByPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeYearImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.OneTimeImport;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeByPeriodPub;

/**
 * @author dat.lh
 *
 */
@Stateless
public class AgreementTimeByPeriodAcFinder implements AgreementTimeByPeriodAdapter {
	@Inject
	private AgreementTimeByPeriodPub agreementTimeByPeriodPub;

	@Override
	public Optional<AgreMaxAverageTimeMultiImport> maxAverageTimeMulti(String companyId, String employeeId,
			GeneralDate criteria, YearMonth yearMonth) {
		return agreementTimeByPeriodPub.maxAverageTimeMulti(employeeId, criteria, yearMonth)
				.map(c -> new AgreMaxAverageTimeMultiImport(c.getErrorTime(), 
						c.getAlarmTime(), 
						c.getAverageTimes().stream().map(x -> new AgreMaxAverageTimeImport(x.getPeriod(), 
																							x.getTotalTime(),
																							x.getAverageTime(), 
																							x.getStatus()))
											.collect(Collectors.toList())));
	}
	
	@Override
	public Optional<AgreementTimeYearImport> timeYear(String employeeId, GeneralDate criteria, Year year) {
		return this.agreementTimeByPeriodPub.timeYear(employeeId, criteria, year)
				.map(t -> new AgreementTimeYearImport(
						new AgreTimeOfMonthlyImport(t.getLimitTime().getAgreementTime(), new OneTimeImport(
								t.getLimitTime().getThreshold().getErrorTime(),
								t.getLimitTime().getThreshold().getAlarmTime(),
								t.getLimitTime().getThreshold().getUpperLimit())),
						new AgreTimeOfMonthlyImport(t.getRecordTime().getAgreementTime(), new OneTimeImport(
								t.getRecordTime().getThreshold().getErrorTime(),
								t.getRecordTime().getThreshold().getAlarmTime(),
								t.getRecordTime().getThreshold().getUpperLimit())),
						t.getStatus()
				));
	}
}
