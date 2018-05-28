package nts.uk.ctx.at.record.app.find.monthly.finder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.AttendanceTimeOfMonthlyDto;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AttendanceTimeOfMonthlyFinder extends MonthlyFinderFacade {

	@Inject
	private AttendanceTimeOfMonthlyRepository repo;
	
	@Override
	@SuppressWarnings("unchecked")
	public AttendanceTimeOfMonthlyDto find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		return AttendanceTimeOfMonthlyDto.from(this.repo.find(employeeId, yearMonth, closureId, closureDate).orElse(null));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, DatePeriod range) {
		return (List<T>) ConvertHelper.yearMonthsBetween(range).stream().map(ym -> find(employeeId, ym))
				.flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, YearMonth yearMonth) {
		return (List<T>) employeeId.stream()
				.map(e -> this.repo.findByYearMonthOrderByStartYmd(e, yearMonth).stream()
						.map(d -> AttendanceTimeOfMonthlyDto.from(d)).collect(Collectors.toList()))
				.flatMap(List::stream).collect(Collectors.toList());
	}
}
