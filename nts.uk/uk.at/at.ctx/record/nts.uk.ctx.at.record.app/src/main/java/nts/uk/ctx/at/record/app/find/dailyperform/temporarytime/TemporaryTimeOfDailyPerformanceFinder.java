package nts.uk.ctx.at.record.app.find.dailyperform.temporarytime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class TemporaryTimeOfDailyPerformanceFinder extends FinderFacade {

	@Inject
	private TemporaryTimeOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public TemporaryTimeOfDailyPerformanceDto find(String employeeId, GeneralDate baseDate) {
		return TemporaryTimeOfDailyPerformanceDto.getDto(this.repo.findByKey(employeeId, baseDate).orElse(null));
	}

}
