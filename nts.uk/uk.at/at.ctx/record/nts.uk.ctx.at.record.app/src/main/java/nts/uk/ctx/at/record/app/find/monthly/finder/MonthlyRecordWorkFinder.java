package nts.uk.ctx.at.record.app.find.monthly.finder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.AffiliationInfoOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.AnnLeaRemNumEachMonthDto;
import nts.uk.ctx.at.record.app.find.monthly.root.AnyItemOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.AttendanceTimeOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.app.find.monthly.root.RsvLeaRemNumEachMonthDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

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
		return dto;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, DatePeriod range) {
		return (List<T>) ConvertHelper.yearMonthsBetween(range).stream().map(ym -> find(employeeId, ym))
				.flatMap(List::stream).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, YearMonthPeriod range) {
		return (List<T>) ConvertHelper.yearMonthsBetween(range).stream().map(ym -> find(employeeId, ym))
				.flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, YearMonth yearMonth) {
		List<AffiliationInfoOfMonthlyDto> aff = affi.find(employeeId, yearMonth);
		List<AttendanceTimeOfMonthlyDto> att = attendanceTime.find(employeeId, yearMonth);
		List<AnyItemOfMonthlyDto> any = anyItemFinder.find(employeeId, yearMonth);
		List<AnnLeaRemNumEachMonthDto> ann = annLeaFinder.find(employeeId, yearMonth);
		List<RsvLeaRemNumEachMonthDto> rsv = anyItemFinder.find(employeeId, yearMonth);
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
			return dto;
		}).collect(Collectors.toList());
	}

	private <T extends MonthlyItemCommon,U extends MonthlyItemCommon> T filterItem(List<T> att, U a) {
		return att.stream().filter(at -> at.getClosureDate().equals(a.getClosureDate()) 
				&& at.getClosureID() == a.getClosureID()).findFirst().orElse(null);
	}
}
