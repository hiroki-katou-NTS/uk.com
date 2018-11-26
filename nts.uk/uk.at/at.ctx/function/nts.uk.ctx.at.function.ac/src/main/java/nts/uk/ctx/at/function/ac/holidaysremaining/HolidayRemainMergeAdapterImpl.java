package nts.uk.ctx.at.function.ac.holidaysremaining;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnualLeaveUsageImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.HolidayRemainMerEx;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.HolidayRemainMergeAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.StatusOfHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReservedYearHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.StatusHolidayImported;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMerge;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMergeRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.export.AbsenceleaveCurrentMonthOfEmployee;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.export.MonthlyAbsenceleaveRemainExport;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.export.DayoffCurrentMonthOfEmployee;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.export.MonthlyDayoffRemainExport;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.export.SpecialHolidayRemainDataOutput;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.export.SpecialHolidayRemainDataSevice;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.AnnualLeaveUsageExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.GetConfirmedAnnualLeave;
import nts.uk.ctx.at.record.pub.monthly.vacation.reserveleave.GetConfirmedReserveLeave;
import nts.uk.ctx.at.record.pub.monthly.vacation.reserveleave.ReserveLeaveUsageExport;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
@Stateless
public class HolidayRemainMergeAdapterImpl implements HolidayRemainMergeAdapter{

	@Inject
	private RemainMergeRepository repoRemainMer;
	@Inject
	private GetConfirmedAnnualLeave a;
	@Inject 
	private GetConfirmedReserveLeave b;
	@Inject
	private MonthlyDayoffRemainExport c;
	@Inject
	private MonthlyAbsenceleaveRemainExport d;
	@Inject
	private SpecialHolidayRemainDataSevice e;
	
	@Override
	public HolidayRemainMerEx getRemainMer(String employeeId, YearMonthPeriod period) {
		val lstYrMon = ConvertHelper.yearMonthsBetween(period);
		Map<YearMonth, List<RemainMerge>> mapRemainMer = repoRemainMer.findBySidsAndYrMons(employeeId, lstYrMon);
		//255
		List<AnnualLeaveUsageExport> lstAnn = a.getYearHdMonthlyVer2(employeeId, period, mapRemainMer);
		List<AnnualLeaveUsageImported> result255 = new ArrayList<>();
		for (AnnualLeaveUsageExport ann : lstAnn) {
			AnnualLeaveUsageImported HolidayRemainData = new AnnualLeaveUsageImported(ann.getYearMonth(),
					ann.getUsedDays().v(), ann.getUsedTime().map(i -> i.v()).orElse(null), ann.getRemainingDays().v(),
					ann.getRemainingTime().map(i -> i.v()).orElse(null));
			result255.add(HolidayRemainData);
		}
		
		//258
		List<ReserveLeaveUsageExport> lstRsv = b.getYearRsvMonthlyVer2(employeeId, period, mapRemainMer);
		List<ReservedYearHolidayImported> result258 = new ArrayList<>();
		for (ReserveLeaveUsageExport rsv : lstRsv) {
			result258.add(new ReservedYearHolidayImported(rsv.getYearMonth(),
					rsv.getUsedDays().v(), rsv.getRemainingDays().v()));
		}
		
		//259
		List<DayoffCurrentMonthOfEmployee> lstDayCur = c.lstDayoffCurrentMonthOfEmpVer2(employeeId, period, mapRemainMer);
		List<StatusHolidayImported> result259 = new ArrayList<>();
		for (DayoffCurrentMonthOfEmployee day : lstDayCur) {
				StatusHolidayImported statusHoliday = new StatusHolidayImported(day.getYm(), day.getOccurrenceDays(),
						day.getOccurrenceTimes(), day.getUseDays(), day.getUseTimes(), day.getUnUsedDays(),
						day.getUnUsedTimes(), day.getRemainingDays(), day.getRemainingTimes());
				result259.add(statusHoliday);
		}
		
		//260
		List<AbsenceleaveCurrentMonthOfEmployee> lstAbs = d.getDataCurrMonOfEmpVer2(employeeId, period, mapRemainMer);
		List<StatusOfHolidayImported> result260 = new ArrayList<>();
		for (AbsenceleaveCurrentMonthOfEmployee abs : lstAbs) {
			StatusOfHolidayImported sttOfHd = new StatusOfHolidayImported(abs.getYm(), abs.getOccurredDay(),
					abs.getUsedDays(), abs.getUnUsedDays(), abs.getRemainingDays());
			result260.add(sttOfHd);
		}
		
		//263
		List<SpecialHolidayRemainDataOutput> lstSpeHd = e.getSpeHdOfConfMonVer2(employeeId, period, mapRemainMer);
		List<SpecialHolidayImported> result263 = new ArrayList<>();
		for (SpecialHolidayRemainDataOutput speHd : lstSpeHd) {
			SpecialHolidayImported specialHoliday = new SpecialHolidayImported(speHd.getYm(), speHd.getUseDays(),
					speHd.getUseTimes(), speHd.getRemainDays(), speHd.getRemainTimes());
			result263.add(specialHoliday);
		}
		
		return new HolidayRemainMerEx(result255, result258, result259, result260, result263);
	}

}
