package nts.uk.ctx.at.function.ac.monthlyremain;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.monthlyremain.AnnualLeaveUsageImport;
import nts.uk.ctx.at.function.dom.adapter.monthlyremain.DayoffCurrentMonthOfEmployeeImport;
import nts.uk.ctx.at.function.dom.adapter.monthlyremain.GetConfirmMonthlyAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlyremain.ReserveLeaveUsageImport;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.export.DayoffCurrentMonthOfEmployee;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.export.MonthlyDayoffRemainExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.AnnualLeaveUsageExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.GetConfirmedAnnualLeave;
import nts.uk.ctx.at.record.pub.monthly.vacation.reserveleave.GetConfirmedReserveLeave;
import nts.uk.ctx.at.record.pub.monthly.vacation.reserveleave.ReserveLeaveUsageExport;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
@Stateless
public class GetConfirmMonthly implements GetConfirmMonthlyAdapter{

	@Inject
	private GetConfirmedAnnualLeave getConfirmedAnnualLeave;
	
	@Inject
	private GetConfirmedReserveLeave getConfirmedReserveLeave;
	
	@Inject
	private MonthlyDayoffRemainExport monthlyDayoffRemainExport;
	
	@Override
	public List<AnnualLeaveUsageImport> getListAnnualLeaveUsageImport(String employeeId, YearMonthPeriod period) {
		List<AnnualLeaveUsageExport> annualLeaveUsageExports =  getConfirmedAnnualLeave.algorithm(employeeId, period);
		return annualLeaveUsageExports.stream().map(x-> 
			new AnnualLeaveUsageImport(x.getYearMonth(),
					x.getUsedDays(),
					x.getUsedTime(),
					x.getRemainingDays(),
					x.getRemainingTime())).collect(Collectors.toList());
	}

	@Override
	public List<ReserveLeaveUsageImport> getListReserveLeaveUsageImport(String employeeId, YearMonthPeriod period) {
		List<ReserveLeaveUsageExport> reserveLeaveUsageExports = getConfirmedReserveLeave.algorithm(employeeId, period);
		return reserveLeaveUsageExports.stream().map(x-> 
		new ReserveLeaveUsageImport(x.getYearMonth(),
				x.getUsedDays(),
				x.getRemainingDays()))
				.collect(Collectors.toList());
	}

	@Override
	public List<DayoffCurrentMonthOfEmployeeImport> lstDayoffCurrentMonthOfEmployee(String employeeId,
			YearMonth startMonth, YearMonth endMonth) {
		List<DayoffCurrentMonthOfEmployee> dayoffCurrentMonthOfEmployees =  monthlyDayoffRemainExport.lstDayoffCurrentMonthOfEmployee(employeeId, startMonth, endMonth);
		return dayoffCurrentMonthOfEmployees.stream().map(x-> 
		new DayoffCurrentMonthOfEmployeeImport(
				x.getSId(),
				x.getYm(),
				x.getOccurrenceDays(),
				x.getOccurrenceTimes(),
				x.getUseDays(),
				x.getUseTimes(),
				x.getRemainingDays(),
				x.getRemainingTimes(),
				x.getCarryForWardDays(),
				x.getCarryForWordTimes(),
				x.getUnUsedDays(),
				x.getUnUsedTimes()
				)).collect(Collectors.toList());
	}

}
