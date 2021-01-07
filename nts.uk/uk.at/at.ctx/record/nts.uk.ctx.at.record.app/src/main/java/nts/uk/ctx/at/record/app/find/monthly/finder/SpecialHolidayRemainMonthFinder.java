package nts.uk.ctx.at.record.app.find.monthly.finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.monthly.root.SpecialHolidayRemainDataDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainDataRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class SpecialHolidayRemainMonthFinder extends MonthlyFinderFacade {
	
	@Inject
	private SpecialHolidayRemainDataRepository repo;

	@Override
	@SuppressWarnings("unchecked")
	public SpecialHolidayRemainDataDto find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		return SpecialHolidayRemainDataDto.from(findD(Arrays.asList(employeeId), Arrays.asList(yearMonth)).stream()
				.filter(c -> c.getClosureId() == closureId.value && c.getClosureDate().getLastDayOfMonth().equals(closureDate.getLastDayOfMonth())
				&& c.getClosureDate().getClosureDay().v() == closureDate.getClosureDay().v())
			.collect(Collectors.toList()));
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

		List<SpecialHolidayRemainData> domains = findD(employeeId, yearMonth);
		List<SpecialHolidayRemainDataDto> dto = new ArrayList<>();
		
		Map<String, Map<YearMonth, Map<Integer, Map<ClosureDate, List<SpecialHolidayRemainData>>>>> groups = domains.stream()
				.collect(Collectors.groupingBy(SpecialHolidayRemainData::getSid,
						Collectors.collectingAndThen(Collectors.toList(), list -> list.stream()
								.collect(Collectors.groupingBy(SpecialHolidayRemainData::getYm, Collectors.collectingAndThen(Collectors.toList(), list2 -> list2.stream()
										.collect(Collectors.groupingBy(SpecialHolidayRemainData::getClosureId, Collectors.collectingAndThen(Collectors.toList(), list3 -> list3.stream()
												.collect(Collectors.groupingBy(SpecialHolidayRemainData::getClosureDate, Collectors.toList())))))))))));

		groups.entrySet().forEach(es -> {
			es.getValue().entrySet().forEach(ves -> {
				ves.getValue().entrySet().forEach(ves1 -> {
					ves1.getValue().entrySet().forEach(ves2 -> {
						dto.add(SpecialHolidayRemainDataDto.from(ves2.getValue()));
					});
				});
			});
		});
		
		return (List<T>) dto;
	}
	
	private List<SpecialHolidayRemainData> findD(Collection<String> employeeId, Collection<YearMonth> yearMonth){
		return repo.findBySidsAndYearMonths(new ArrayList<>(employeeId), new ArrayList<>(yearMonth));
	}
}
