package nts.uk.ctx.at.record.app.find.dailyperform.resttime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.BreakTimeDailyDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
/** 日別実績の休憩時間帯 Finder */
public class BreakTimeDailyFinder extends FinderFacade {

	@Inject
	private BreakTimeOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public BreakTimeDailyDto find(String employeeId, GeneralDate baseDate) {
		List<BreakTimeOfDailyPerformance> domains = this.repo.findByKey(employeeId, baseDate);
		if (!domains.isEmpty()) {
			return BreakTimeDailyDto.toDto(domains.get(0));
		}
		return new BreakTimeDailyDto();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BreakTimeDailyDto> finds(String employeeId, GeneralDate baseDate) {
		return this.repo.findByKey(employeeId, baseDate).stream().map(x -> {
			return BreakTimeDailyDto.toDto(x);
		}).collect(Collectors.toList());
	}
}
