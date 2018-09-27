package nts.uk.ctx.at.record.app.find.monthly.finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyCareHdRemainDto;
import nts.uk.ctx.at.record.dom.monthly.information.care.MonCareHdRemainRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class MonthlyCareRemainFinder extends MonthlyFinderFacade {
	
	@Inject
	private MonCareHdRemainRepository repo;

	@Override
	@SuppressWarnings("unchecked")
	public MonthlyCareHdRemainDto find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		return MonthlyCareHdRemainDto.from(this.repo.find(employeeId, yearMonth, closureId, closureDate).orElse(null));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<MonthlyCareHdRemainDto> finds(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		int lastDay = closureDate.getLastDayOfMonth() ? 1 : 0;
		return repo.findByYearMonthOrderByStartYmd(employeeId, yearMonth).stream()
				.filter(c -> c.getClosureId() == closureId && c.getIsLastDay() == lastDay
							&& c.getClosureDay().v() == closureDate.getClosureDay().v())
				.map(c -> MonthlyCareHdRemainDto.from(c)).collect(Collectors.toList());
	}
	
	@Override
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, DatePeriod range) {
		return find(employeeId, ConvertHelper.yearMonthsBetween(range));
	}

	@Override
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, YearMonth yearMonth) {
		return find(employeeId, Arrays.asList(yearMonth));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, Collection<YearMonth> yearMonth) {
		return (List<T>) repo.findBySidsAndYearMonths(new ArrayList<>(employeeId), new ArrayList<>(yearMonth))
				.stream().map(d -> MonthlyCareHdRemainDto.from(d)).collect(Collectors.toList());
	}
}
