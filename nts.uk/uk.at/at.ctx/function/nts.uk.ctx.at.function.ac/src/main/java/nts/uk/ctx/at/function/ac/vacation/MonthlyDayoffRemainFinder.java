package nts.uk.ctx.at.function.ac.vacation;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.vacation.CurrentHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.MonthlyDayoffRemainAdapter;
import nts.uk.ctx.at.function.dom.adapter.vacation.StatusHolidayImported;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.export.DayoffCurrentMonthOfEmployee;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.export.MonthlyDayoffRemainExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffManagementQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.InterimRemainAggregateOutputData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MonthlyDayoffRemainFinder implements MonthlyDayoffRemainAdapter {

	@Inject
	private MonthlyDayoffRemainExport monthlyDayoffRemainExport;

	@Inject
	private BreakDayOffManagementQuery breakDayOffManagementQuery;

	@Override
	public List<StatusHolidayImported> lstDayoffCurrentMonthOfEmployee(String employeeId, YearMonth startMonth,
			YearMonth endMonth) {
		// 259
//		List<DayoffCurrentMonthOfEmployee> lstDayoffCurrentMonth = monthlyDayoffRemainExport
//				.lstDayoffCurrentMonthOfEmployee(employeeId, startMonth, endMonth);
		// TODO 259 Fail
		DayoffCurrentMonthOfEmployee itemImported = new DayoffCurrentMonthOfEmployee(AppContexts.user().employeeId(), GeneralDate.today().yearMonth().addMonths(-1), 0d, 0, 0d, 0, 0d, 0, 0d, 0, 0d, 0);
		List<DayoffCurrentMonthOfEmployee> lstDayoffCurrentMonth = new ArrayList<>();
		lstDayoffCurrentMonth.add(itemImported);		
		
		if (lstDayoffCurrentMonth == null)
			return null;
		List<StatusHolidayImported> lstStatusHoliday = new ArrayList<>();
		lstDayoffCurrentMonth.forEach(item -> {
			StatusHolidayImported statusHoliday = new StatusHolidayImported(item.getYm(), item.getOccurrenceDays(),
					item.getOccurrenceTimes(), item.getUseDays(), item.getUseTimes(), item.getUnUsedDays(),
					item.getUnUsedTimes(), item.getRemainingDays(), item.getRemainingTimes());
			lstStatusHoliday.add(statusHoliday);
		});
		return lstStatusHoliday;
	}

	@Override
	public List<CurrentHolidayImported> getInterimRemainAggregate(String employeeId, GeneralDate baseDate,
			YearMonth startMonth, YearMonth endMonth) {
		// 269
//		List<InterimRemainAggregateOutputData> lstInterimRemainAggregate = breakDayOffManagementQuery
//				.getInterimRemainAggregate(employeeId, baseDate, startMonth, endMonth);
		
		// TODO 269 Fail
		InterimRemainAggregateOutputData itemImported = new InterimRemainAggregateOutputData(
				GeneralDate.today().yearMonth().addMonths(1), 0d, 0d, 0d, 0d, 0d);
		List<InterimRemainAggregateOutputData> lstInterimRemainAggregate = new ArrayList<>();
		lstInterimRemainAggregate.add(itemImported);
		
		if (lstInterimRemainAggregate == null)
			return null;
		List<CurrentHolidayImported> lstCurrentHoliday = new ArrayList<>();
		lstInterimRemainAggregate.forEach(item -> {
			CurrentHolidayImported holiday = new CurrentHolidayImported(item.getYm(), item.getMonthStartRemain(),
					item.getMonthOccurrence(), item.getMonthUse(), item.getMonthExtinction(), item.getMonthEndRemain());
			lstCurrentHoliday.add(holiday);
		});
		return lstCurrentHoliday;
	}

}
