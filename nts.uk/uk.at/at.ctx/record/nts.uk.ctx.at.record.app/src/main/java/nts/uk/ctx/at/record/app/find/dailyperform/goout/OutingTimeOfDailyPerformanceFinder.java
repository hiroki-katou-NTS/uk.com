package nts.uk.ctx.at.record.app.find.dailyperform.goout;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
/** OutingTimeOfDailyPerformanceDto Finder */
public class OutingTimeOfDailyPerformanceFinder extends FinderFacade {

	@Inject
	private OutingTimeOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public OutingTimeOfDailyPerformanceDto find(String employeeId, GeneralDate baseDate) {
		return OutingTimeOfDailyPerformanceDto.getDto(this.repo.findByEmployeeIdAndDate(employeeId, baseDate).orElse(null));
	}
}
