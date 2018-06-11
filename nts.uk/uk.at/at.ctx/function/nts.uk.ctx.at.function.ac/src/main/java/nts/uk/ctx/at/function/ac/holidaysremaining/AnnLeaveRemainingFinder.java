package nts.uk.ctx.at.function.ac.holidaysremaining;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaGrantNumberImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveOfThisMonthImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveRemainingAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveUsageStatusOfThisMonthImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnualLeaveUsageImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.NextHolidayGrantDateImported;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.AnnualLeaveUsageExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.GetConfirmedAnnualLeave;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AggrResultOfAnnualLeaveEachMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveOfThisMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveRemainNumberPub;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.GetAnnLeaGrantNumOfCurrentMon;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.NextHolidayGrantDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class AnnLeaveRemainingFinder implements AnnLeaveRemainingAdapter {

	@Inject
	private AnnLeaveRemainNumberPub annLeaveRemainNumberPub;

	@Inject
	private GetAnnLeaGrantNumOfCurrentMon getAnnLeaGrantNumOfCurrentMon;

	@Inject
	private GetConfirmedAnnualLeave getConfirmedAnnualLeave;

	@Override
	public List<AnnLeaveUsageStatusOfThisMonthImported> getAnnLeaveUsageOfThisMonth(String employeeId,
			DatePeriod datePeriod) {
		List<AggrResultOfAnnualLeaveEachMonth> aggrResults = annLeaveRemainNumberPub
				.getAnnLeaveRemainAfterThisMonth(employeeId, datePeriod);
		if (aggrResults == null)
			return null;

		return aggrResults.stream().map(item -> {

			return new AnnLeaveUsageStatusOfThisMonthImported(item.getYearMonth(),
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus()
							.getUsedNumber().getUsedDays().getUsedDays().v(),
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus()
							.getUsedNumber().getUsedTime().isPresent()
									? Optional.of(item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
											.getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumber()
											.getUsedTime().get().getUsedTime().v())
									: Optional.empty(),
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus()
							.getRemainingNumber().getTotalRemainingDays().v(),
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus()
							.getRemainingNumber().getTotalRemainingTime().isPresent()
									? Optional.of(item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
											.getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumber()
											.getTotalRemainingTime().get().v())
									: Optional.empty());
		}).collect(Collectors.toList());
	}

	@Override
	public AnnLeaveOfThisMonthImported getAnnLeaveOfThisMonth(String employeeId) {
		AnnLeaveOfThisMonth annLeave = annLeaveRemainNumberPub.getAnnLeaveOfThisMonth(employeeId);
		if (annLeave == null)
			return null;

		return new AnnLeaveOfThisMonthImported(annLeave.getGrantDate(), annLeave.getGrantDays(),
				annLeave.getFirstMonthRemNumDays(), annLeave.getFirstMonthRemNumMinutes(), annLeave.getUsedDays().v(),
				annLeave.getUsedMinutes(), annLeave.getRemainDays().v(), annLeave.getRemainMinutes());
	}

	@Override
	public NextHolidayGrantDateImported getNextHolidayGrantDate(String companyId, String employeeId) {
		NextHolidayGrantDate nextHoloday = annLeaveRemainNumberPub.getNextHolidayGrantDate(companyId, employeeId);
		if (nextHoloday == null)
			return null;

		return new NextHolidayGrantDateImported(nextHoloday.getGrantDate());
	}

	@Override
	public BigDecimal algorithm(String employeeId) {
		// requestList281
		return getAnnLeaGrantNumOfCurrentMon.algorithm(employeeId).getGrantDays().v();
	}

	@Override
	public List<AnnualLeaveUsageImported> algorithm(String employeeId, YearMonthPeriod period) {
		// requestList255
		List<AnnualLeaveUsageExport> algorithm = getConfirmedAnnualLeave.algorithm(employeeId, period);
		if (algorithm == null)
			return null;
		List<AnnualLeaveUsageImported> lstHolidayRemainData = new ArrayList<>();
		algorithm.forEach(item -> {
			AnnualLeaveUsageImported HolidayRemainData = new AnnualLeaveUsageImported(item.getYearMonth(),
					item.getUsedDays().v(), item.getUsedTime().get().v(), item.getRemainingDays().v(),
					item.getRemainingTime().get().v());
			lstHolidayRemainData.add(HolidayRemainData);
		});
		return lstHolidayRemainData;
	}
}
