package nts.uk.ctx.at.record.app.find.monthly.finder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.AbsenceLeaveRemainDataDto;
import nts.uk.ctx.at.record.app.find.monthly.root.AffiliationInfoOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.AnnLeaRemNumEachMonthDto;
import nts.uk.ctx.at.record.app.find.monthly.root.AnyItemOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.AttendanceTimeOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyCareHdRemainDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyChildCareHdRemainDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyDayoffRemainDataDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRemarksDto;
import nts.uk.ctx.at.record.app.find.monthly.root.RsvLeaRemNumEachMonthDto;
import nts.uk.ctx.at.record.app.find.monthly.root.SpecialHolidayRemainDataDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class MonthlyRecordWorkFinder extends MonthlyFinderFacade {

	@Inject
	private AffiliationInfoOfMonthlyFinder affi;
	
	@Inject
	private AttendanceTimeOfMonthlyFinder attendanceTime;
	
	@Inject
	private AnnLeaRemNumEachMonthFinder annLeaFinder;
	
	@Inject
	private RsvLeaRemNumEachMonthFinder rsvLeaFinder;
	
	@Inject
	private AnyItemOfMonthlyFinder anyItemFinder;

	@Inject
	private DayOffRemainMonthFinder dayOffFinder;
	
	@Inject
	private SpecialHolidayRemainMonthFinder specialHolidayFinder;
	
	@Inject
	private AbsenceLeaveRemainMonthFinder absenceLeaveFinder;
	
	@Inject
	private MonthlyRemarksFinder remarksFinder;
	
	@Inject
	private MonthlyCareRemainFinder careFinder;
	
	@Inject
	private MonthlyChildCareRemainFinder childCareFinder;
	
	@Override
	@SuppressWarnings("unchecked")
	public MonthlyRecordWorkDto find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		MonthlyRecordWorkDto dto = new MonthlyRecordWorkDto();
		dto.setClosureDate(ClosureDateDto.from(closureDate));
		dto.setClosureID(closureId.value);
		dto.setEmployeeId(employeeId);
		dto.setYearMonth(yearMonth);
		dto.setAffiliation(affi.find(employeeId, yearMonth, closureId, closureDate));
		dto.setAttendanceTime(attendanceTime.find(employeeId, yearMonth, closureId, closureDate));
		dto.setAnnLeave(annLeaFinder.find(employeeId, yearMonth, closureId, closureDate));
		dto.setRsvLeave(rsvLeaFinder.find(employeeId, yearMonth, closureId, closureDate));
		dto.setAnyItem(anyItemFinder.find(employeeId, yearMonth, closureId, closureDate));
		dto.setDayOff(dayOffFinder.find(employeeId, yearMonth, closureId, closureDate));
		dto.setAbsenceLeave(absenceLeaveFinder.find(employeeId, yearMonth, closureId, closureDate));
		dto.setSpecialHoliday(specialHolidayFinder.finds(employeeId, yearMonth, closureId, closureDate));
		dto.setRemarks(remarksFinder.finds(employeeId, yearMonth, closureId, closureDate));
		dto.setCare(careFinder.find(employeeId, yearMonth, closureId, closureDate));
		dto.setChildCare(childCareFinder.find(employeeId, yearMonth, closureId, closureDate));
		return dto;
	}

	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, YearMonthPeriod range) {
		return find(employeeId, ConvertHelper.yearMonthsBetween(range));
	}

	@Override
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, DatePeriod range) {
		return find(employeeId, ConvertHelper.yearMonthsBetween(range));
	}

	@Override
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, YearMonth yearMonth) {
		return find(employeeId, Arrays.asList(yearMonth));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId,
			Collection<YearMonth> yearMonth) {
		List<AffiliationInfoOfMonthlyDto> aff = affi.find(employeeId, yearMonth);
		List<AttendanceTimeOfMonthlyDto> att = attendanceTime.find(employeeId, yearMonth);
		List<AnyItemOfMonthlyDto> any = anyItemFinder.find(employeeId, yearMonth);
		List<AnnLeaRemNumEachMonthDto> ann = annLeaFinder.find(employeeId, yearMonth);
		List<RsvLeaRemNumEachMonthDto> rsv = rsvLeaFinder.find(employeeId, yearMonth);
		List<MonthlyDayoffRemainDataDto> dayOff = dayOffFinder.find(employeeId, yearMonth);
		List<AbsenceLeaveRemainDataDto> absenceLeave = absenceLeaveFinder.find(employeeId, yearMonth);
		List<SpecialHolidayRemainDataDto> specialHoliday = specialHolidayFinder.find(employeeId, yearMonth);
		List<MonthlyRemarksDto> remarks = remarksFinder.find(employeeId, yearMonth);
		List<MonthlyCareHdRemainDto> care = remarksFinder.find(employeeId, yearMonth);
		List<MonthlyChildCareHdRemainDto> childCare = remarksFinder.find(employeeId, yearMonth);
		return (List<T>) aff.stream().map(a -> {
			MonthlyRecordWorkDto dto = new MonthlyRecordWorkDto();
			dto.setClosureDate(a.getClosureDate());
			dto.setClosureID(a.getClosureID());
			dto.setEmployeeId(a.employeeId());
			dto.setYearMonth(a.yearMonth());
			dto.setAffiliation(a);
			dto.setAttendanceTime(filterItem(att, a));
			dto.setAnnLeave(filterItem(ann, a));
			dto.setRsvLeave(filterItem(rsv, a));
			dto.setAnyItem(filterItem(any, a));
			dto.setDayOff(filterItem(dayOff, a));
			dto.setAbsenceLeave(filterItem(absenceLeave, a));
			dto.setSpecialHoliday(filterItems(specialHoliday, a));
			dto.setRemarks(filterItems(remarks, a));
			dto.setCare(filterItem(care, a));
			dto.setChildCare(filterItem(childCare, a));
			return dto;
		}).collect(Collectors.toList());
	}

	private <T extends MonthlyItemCommon,U extends MonthlyItemCommon> T filterItem(List<T> att, U a) {
		return att.stream().filter(at -> 
				at.getClosureDate().equals(a.getClosureDate()) 
				&& at.employeeId().equals(a.employeeId()) 
				&& at.yearMonth().equals(a.yearMonth()) 
				&& at.getClosureID() == a.getClosureID()).findFirst().orElse(null);
	}
	
	private <T extends MonthlyItemCommon,U extends MonthlyItemCommon> List<T> filterItems(List<T> att, U a) {
		return att.stream().filter(at -> 
				at.getClosureDate().equals(a.getClosureDate()) 
				&& at.employeeId().equals(a.employeeId()) 
				&& at.yearMonth().equals(a.yearMonth()) 
				&& at.getClosureID() == a.getClosureID()).collect(Collectors.toList());
	}
}
