package nts.uk.ctx.at.shared.app.find.employmentrules.workclosuredate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.employmentrules.workclosuredate.CalculateMonthlyPeriodService;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class CalculateMonthlyPeriodFinder {
	
	@Inject
	private CalculateMonthlyPeriodService calculateMonthlyPeriodService;
	
	public OutputCalculateMonthly getMonthlyPeriodResult(int closureId) {
		GeneralDate baseDate = GeneralDate.today();
		DatePeriod datePeriod  =  calculateMonthlyPeriodService.calculateMonthlyPeriod(closureId, baseDate);
		return new OutputCalculateMonthly(datePeriod.start(),datePeriod.end());
	}

}
