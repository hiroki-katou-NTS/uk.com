package nts.uk.ctx.at.function.ac.holidaysremaining;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveRemainingAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveUsageStatusOfThisMonthImported;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AggrResultOfAnnualLeaveEachMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveRemainNumberPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public class AnnLeaveRemainingFinder implements AnnLeaveRemainingAdapter {

	@Inject
	private AnnLeaveRemainNumberPub annLeaveRemainNumberPub;
	
	@Override
	public List<AnnLeaveUsageStatusOfThisMonthImported> getAnnLeaveUsageOfThisMonth(String employeeId,
			DatePeriod datePeriod) {
		List<AggrResultOfAnnualLeaveEachMonth> aggrResults = 
				annLeaveRemainNumberPub.getAnnLeaveRemainAfterThisMonth(employeeId, datePeriod);
		if (aggrResults == null) return null;
		
		aggrResults.stream().map(item -> {
			return new AnnLeaveUsageStatusOfThisMonthImported(
					item.getYearMonth(), 
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumber().getUsedDays().getUsedDays().v(),
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumber().getUsedTime().get().getUsedTime().v(),
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumber().getTotalRemainingDays().v(),
					item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumber().getTotalRemainingTime().get().v()
					);
		}).collect(Collectors.toList());
		return null;
	}

}
