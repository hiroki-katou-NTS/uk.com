package nts.uk.ctx.at.function.ac.holidaysremaining;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AbsenceReruitmentManaAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.CurrentHolidayRemainImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.StatusOfHolidayImported;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.export.AbsenceleaveCurrentMonthOfEmployee;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.export.MonthlyAbsenceleaveRemainExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentManaQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.InterimRemainAggregateOutputData;

@Stateless
public class AbsenceReruitmentManaFinder implements AbsenceReruitmentManaAdapter {

	@Inject
	private AbsenceReruitmentManaQuery absenceReruitmentManaQuery;

	@Inject
	private MonthlyAbsenceleaveRemainExport monAbleaRemainEx;

	@Override
	public List<CurrentHolidayRemainImported> getAbsRecRemainAggregate(String employeeId, GeneralDate baseDate,
			YearMonth startMonth, YearMonth endMonth) {
		// requestList270
		List<InterimRemainAggregateOutputData> lstAbsRecRemainAggregate = absenceReruitmentManaQuery
				.getAbsRecRemainAggregate(employeeId, baseDate, startMonth, endMonth);
		// TODO requestList270 fail
//		InterimRemainAggregateOutputData itemImported = new InterimRemainAggregateOutputData(GeneralDate.today().yearMonth().addMonths(1), 0d, 0d, 0d, 0d, 0d);
//		List<InterimRemainAggregateOutputData> lstAbsRecRemainAggregate = new ArrayList<>();
//		lstAbsRecRemainAggregate.add(itemImported);
		
		if (lstAbsRecRemainAggregate == null)
			return null;
		List<CurrentHolidayRemainImported> lstCurrentHoliday = new ArrayList<>();
		lstAbsRecRemainAggregate.forEach(item -> {
			CurrentHolidayRemainImported CurrentHoliday = new CurrentHolidayRemainImported(item.getYm(), item.getMonthStartRemain(),
					item.getMonthOccurrence(), item.getMonthUse(), item.getMonthExtinction(), item.getMonthEndRemain());
			lstCurrentHoliday.add(CurrentHoliday);
		});
		return lstCurrentHoliday;
	}

	@Override
	public List<StatusOfHolidayImported> getDataCurrentMonthOfEmployee(String employeeId, YearMonth startMonth,
			YearMonth endMonth) {
		// requestList260
		List<AbsenceleaveCurrentMonthOfEmployee> lstDataCurrentMonthOfEmployee = monAbleaRemainEx
				.getDataCurrentMonthOfEmployee(employeeId, startMonth, endMonth);
		
		// TODO requestList260 fail
//		AbsenceleaveCurrentMonthOfEmployee itemImported = new AbsenceleaveCurrentMonthOfEmployee(
//				AppContexts.user().employeeId(), GeneralDate.today().yearMonth().addMonths(-1), 0d, 0d, 0d, 0d, 0d);
//		List<AbsenceleaveCurrentMonthOfEmployee> lstDataCurrentMonthOfEmployee = new ArrayList<>();
//		lstDataCurrentMonthOfEmployee.add(itemImported);
		
		if (lstDataCurrentMonthOfEmployee == null)
			return null;
		List<StatusOfHolidayImported> lstStatusOfHoliday = new ArrayList<>();
		lstDataCurrentMonthOfEmployee.forEach(item -> {
			StatusOfHolidayImported statusOfHoliday = new StatusOfHolidayImported(item.getYm(), item.getOccurredDay(),
					item.getUsedDays(), item.getUnUsedDays(), item.getRemainingDays());
			lstStatusOfHoliday.add(statusOfHoliday);
		});

		return lstStatusOfHoliday;
	}
	/**
	 * @author hoatt
	 * Doi ung response KDR001
	 * RequestList260 社員の月毎の確定済み振休を取得する - ver2
	 * @param employeeId
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	@Override
	public List<StatusOfHolidayImported> getDataCurrMonOfEmpVer2(String employeeId, YearMonth startMonth,
			YearMonth endMonth) {
		List<AbsenceleaveCurrentMonthOfEmployee> lstDataCurrMonOfEmp = monAbleaRemainEx.getDataCurrMonOfEmpVer2(employeeId, startMonth, endMonth);
		if (lstDataCurrMonOfEmp == null)
			return null;
		List<StatusOfHolidayImported> lstSttOfHd = new ArrayList<>();
		lstDataCurrMonOfEmp.forEach(item -> {
			StatusOfHolidayImported sttOfHd = new StatusOfHolidayImported(item.getYm(), item.getOccurredDay(),
					item.getUsedDays(), item.getUnUsedDays(), item.getRemainingDays());
			lstSttOfHd.add(sttOfHd);
		});
		return lstSttOfHd;
	}

}
