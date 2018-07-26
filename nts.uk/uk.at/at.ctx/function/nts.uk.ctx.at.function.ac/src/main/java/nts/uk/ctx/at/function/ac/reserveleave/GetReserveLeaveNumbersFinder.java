package nts.uk.ctx.at.function.ac.reserveleave;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.GetReserveLeaveNumbersAdpter;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReserveHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReservedYearHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.RsvLeaUsedCurrentMonImported;
import nts.uk.ctx.at.record.pub.monthly.vacation.reserveleave.GetConfirmedReserveLeave;
import nts.uk.ctx.at.record.pub.monthly.vacation.reserveleave.ReserveLeaveUsageExport;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.GetReserveLeaveNumbers;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.GetRsvLeaNumAfterCurrentMon;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.ReserveLeaveNowExport;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.RsvLeaUsedCurrentMonExport;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class GetReserveLeaveNumbersFinder implements GetReserveLeaveNumbersAdpter {

	@Inject
	private GetReserveLeaveNumbers getReserveLeaveNumbers;

	@Inject
	private GetConfirmedReserveLeave getConfirmedReserveLeave;

	@Inject
	private GetRsvLeaNumAfterCurrentMon getRsvLeaNumAfterCurrentMon;

	@Override
	public ReserveHolidayImported algorithm(String employeeId) {
		// requestList268
		ReserveLeaveNowExport reserveLeave = getReserveLeaveNumbers.algorithm(employeeId);

		return new ReserveHolidayImported(reserveLeave.getStartMonthRemain().v(), reserveLeave.getGrantNumber().v(),
				reserveLeave.getUsedNumber().v(), reserveLeave.getRemainNumber().v(),
				reserveLeave.getUndigestNumber().v());
	}

	@Override
	public List<ReservedYearHolidayImported> algorithm(String employeeId, YearMonthPeriod period) {
		// requestList258
		List<ReserveLeaveUsageExport> lstReserveLeaveUsage = getConfirmedReserveLeave.algorithm(employeeId, period);

		List<ReservedYearHolidayImported> lstReservedYearHoliday = new ArrayList<>();
		lstReserveLeaveUsage.forEach(item -> {
			ReservedYearHolidayImported reservedYearHoliday = new ReservedYearHolidayImported(item.getYearMonth(),
					item.getUsedDays().v(), item.getRemainingDays().v());
			lstReservedYearHoliday.add(reservedYearHoliday);
		});
		return lstReservedYearHoliday;
	}

	@Override
	public List<RsvLeaUsedCurrentMonImported> algorithm364(String employeeId, YearMonthPeriod period) {
		// requestList364
		List<RsvLeaUsedCurrentMonExport> lstRsvLeaUsedCurrentMon = getRsvLeaNumAfterCurrentMon.algorithm(employeeId,
				period);
		if (lstRsvLeaUsedCurrentMon == null)
			{return new ArrayList<RsvLeaUsedCurrentMonImported>();}
       return lstRsvLeaUsedCurrentMon.stream().map(item -> {
    	   return new RsvLeaUsedCurrentMonImported(item.getYearMonth(), item.getUsedNumber().v(), item.getRemainNumber().v());
       }).collect(Collectors.toList());
	}
}
