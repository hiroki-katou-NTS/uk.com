package nts.uk.ctx.at.record.app.find.dailyperform.workrecord;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
/** 日別実績の出退勤 Finder */
public class TimeLeavingOfDailyPerformanceFinder extends FinderFacade {

	@Inject
	private TimeLeavingOfDailyPerformanceRepository repo;
	
	@SuppressWarnings("unchecked")
	@Override
	public TimeLeavingOfDailyPerformanceDto find(String employeeId, GeneralDate baseDate) {
		return TimeLeavingOfDailyPerformanceDto.getDto(this.repo.findByKey(employeeId, baseDate).orElse(null));
	}

}
