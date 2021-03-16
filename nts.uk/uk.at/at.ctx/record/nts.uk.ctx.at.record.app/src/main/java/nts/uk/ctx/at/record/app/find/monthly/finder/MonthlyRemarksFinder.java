package nts.uk.ctx.at.record.app.find.monthly.finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRemarksDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remarks.RemarksMonthlyRecord;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remarks.RemarksMonthlyRecordRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class MonthlyRemarksFinder extends MonthlyFinderFacade {
	
	@Inject
	private RemarksMonthlyRecordRepository repo;

	@Override
	@SuppressWarnings("unchecked")
	public MonthlyRemarksDto find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		return MonthlyRemarksDto.from(this.repo.findByEmployees(Arrays.asList(employeeId), yearMonth, closureId, closureDate));
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
		
		List<RemarksMonthlyRecord> domains = repo.findBySidsAndYearMonths(new ArrayList<>(employeeId), new ArrayList<>(yearMonth));
		List<MonthlyRemarksDto> dto = new ArrayList<>();
		
		Map<String, Map<YearMonth, Map<ClosureId, Map<ClosureDate, List<RemarksMonthlyRecord>>>>> groups = domains.stream()
				.collect(Collectors.groupingBy(RemarksMonthlyRecord::getEmployeeId,
						Collectors.collectingAndThen(Collectors.toList(), list -> list.stream()
								.collect(Collectors.groupingBy(RemarksMonthlyRecord::getRemarksYM, Collectors.collectingAndThen(Collectors.toList(), list2 -> list2.stream()
										.collect(Collectors.groupingBy(RemarksMonthlyRecord::getClosureId, Collectors.collectingAndThen(Collectors.toList(), list3 -> list3.stream()
												.collect(Collectors.groupingBy(RemarksMonthlyRecord::getClosureDate, Collectors.toList())))))))))));

		groups.entrySet().forEach(es -> {
			es.getValue().entrySet().forEach(ves -> {
				ves.getValue().entrySet().forEach(ves1 -> {
					ves1.getValue().entrySet().forEach(ves2 -> {
						dto.add(MonthlyRemarksDto.from(ves2.getValue()));
					});
				});
			});
		});
		
		return (List<T>) dto;
	}
}
