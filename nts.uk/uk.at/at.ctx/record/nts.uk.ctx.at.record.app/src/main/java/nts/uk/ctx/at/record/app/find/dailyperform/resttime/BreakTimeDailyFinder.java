package nts.uk.ctx.at.record.app.find.dailyperform.resttime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.BreakTimeDailyDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
/** 日別実績の休憩時間帯 Finder */
public class BreakTimeDailyFinder extends FinderFacade {

	@Inject
	private BreakTimeOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public BreakTimeDailyDto find(String employeeId, GeneralDate baseDate) {
		List<BreakTimeOfDailyPerformance> domains = this.repo.findByKey(employeeId, baseDate);
		if (!domains.isEmpty()) {
			return BreakTimeDailyDto.getDto(domains);
		}
		return new BreakTimeDailyDto();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ConvertibleAttendanceItem> List<T> find(List<String> employeeId, DatePeriod baseDate) {
		return (List<T>) group(this.repo.finds(employeeId, baseDate));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Map<String, List<GeneralDate>> param) {
		return  (List<T>) group(this.repo.finds(param));
	}

	@Override
	public Object getDomain(String employeeId, GeneralDate baseDate) {
		return repo.findByKey(employeeId, baseDate);
	}
	
	private List<BreakTimeDailyDto> group(List<BreakTimeOfDailyPerformance> domains) {
		Map<String, Map<GeneralDate, List<BreakTimeOfDailyPerformance>>> remarks = domains.stream()
				.collect(Collectors.groupingBy(BreakTimeOfDailyPerformance::getEmployeeId,
						Collectors.collectingAndThen(Collectors.toList(), list -> list.stream()
								.collect(Collectors.groupingBy(BreakTimeOfDailyPerformance::getYmd, Collectors.toList())))));

		List<BreakTimeDailyDto> dto = new ArrayList<>();

		remarks.entrySet().forEach(es -> {
			es.getValue().entrySet().forEach(ves -> {
				dto.add(BreakTimeDailyDto.getDto(ves.getValue()));
			});
		});

		return dto;
	}
}
