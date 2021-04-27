package nts.uk.ctx.at.function.ac.monthly.agreement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreMaxAverageTimeImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreMaxAverageTimeMultiImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreTimeOfMonthlyImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreMaxTimeOfMonthlyImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeByPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeYearImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeOfManagePeriodImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.OneTimeImport;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeByPeriodPub;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeOfManagePeriodPub;
import nts.uk.ctx.at.record.pub.monthly.agreement.export.AgreementTimeOfManagePeriodExport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;

/**
 * @author dat.lh
 *
 */
@Stateless
public class AgreementTimeByPeriodAcFinder implements AgreementTimeByPeriodAdapter {
	@Inject
	private AgreementTimeByPeriodPub agreementTimeByPeriodPub;
	@Inject
	private AgreementTimeOfManagePeriodPub agreementTimeOfManagePeriodPub;

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
	public List<AgreementTimeOfManagePeriodImport> get(List<String> sids, YearMonthPeriod ymPeriod) {
		List<AgreementTimeOfManagePeriodImport> result = agreementTimeOfManagePeriodPub.get(sids, ymPeriod)
				.stream().map(x -> new AgreementTimeOfManagePeriodImport(x.getSid(),
						x.getYm(),
						EnumAdaptor.valueOf(x.getStatus(), AgreementTimeStatusOfMonthly.class),
						new AgreMaxTimeOfMonthlyImport(new AttendanceTimeMonth(x.getAgreementTime().getAgreementTime()),
								new AgreementOneMonthTime(x.getLegalMaxTime().getAgreementTime()),
								AgreMaxTimeStatusOfMonthly.NORMAL)))
				.collect(Collectors.toList());
		return result;
	}

	@Override
	public Map<String, List<AgreementTimeOfManagePeriodImport>> getAgreement(List<String> sids, Year year) {
		Map<String,List<AgreementTimeOfManagePeriodExport>> result = agreementTimeOfManagePeriodPub.get(sids, year);
		Map<String, List<AgreementTimeOfManagePeriodImport>> mapResult = new HashMap<>(); 
		result.forEach((a, b) -> {
			List<AgreementTimeOfManagePeriodImport> lstAgTime = b.stream().map(x -> new AgreementTimeOfManagePeriodImport(x.getSid(),
					x.getYm(),
					EnumAdaptor.valueOf(x.getStatus(), AgreementTimeStatusOfMonthly.class),
					new AgreMaxTimeOfMonthlyImport(new AttendanceTimeMonth(x.getAgreementTime().getAgreementTime()),
							new AgreementOneMonthTime(x.getLegalMaxTime().getAgreementTime()),
							AgreMaxTimeStatusOfMonthly.NORMAL))).collect(Collectors.toList());
			mapResult.put(a, lstAgTime);
		});
		return mapResult;
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
