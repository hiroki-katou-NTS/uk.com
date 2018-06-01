package nts.uk.ctx.at.function.ac.holidaysremaining;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveOfThisMonthImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveRemainingAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveUsageStatusOfThisMonthImported;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AggrResultOfAnnualLeaveEachMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveOfThisMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveRemainNumberPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AnnLeaveRemainingFinder implements AnnLeaveRemainingAdapter {

	@Inject
	private AnnLeaveRemainNumberPub annLeaveRemainNumberPub;
	
	@Override
	public List<AnnLeaveUsageStatusOfThisMonthImported> getAnnLeaveUsageOfThisMonth(String employeeId,
			DatePeriod datePeriod) {
		List<AggrResultOfAnnualLeaveEachMonth> aggrResults = 
				annLeaveRemainNumberPub.getAnnLeaveRemainAfterThisMonth(employeeId, datePeriod);
		if (aggrResults == null) return null;
		
		return aggrResults.stream().map(item -> {
			
			return new AnnLeaveUsageStatusOfThisMonthImported(
					item.getYearMonth(), 
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumber().getUsedDays().getUsedDays().v(),
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumber().getUsedTime().isPresent() ?
						Optional.of(item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumber().getUsedTime().get().getUsedTime().v()) : Optional.empty(),
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumber().getTotalRemainingDays().v(),
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumber().getTotalRemainingTime().isPresent() ?
						Optional.of(item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumber().getTotalRemainingTime().get().v()) : Optional.empty()
					);
		}).collect(Collectors.toList());
	}

	@Override
	public AnnLeaveOfThisMonthImported getAnnLeaveOfThisMonth(String employeeId) {
		AnnLeaveOfThisMonth annLeave = annLeaveRemainNumberPub.getAnnLeaveOfThisMonth(employeeId);
		if(annLeave == null) return null;
		
		return new AnnLeaveOfThisMonthImported(
				annLeave.getGrantDate(),
				annLeave.getGrantDays(), 
				annLeave.getFirstMonthRemNumDays(),
				annLeave.getFirstMonthRemNumMinutes(),
				annLeave.getUsedDays().v(),
				annLeave.getUsedMinutes(),
				annLeave.getRemainDays().v(), 
				annLeave.getRemainMinutes()
				);
	}
}
