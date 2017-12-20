package nts.uk.ctx.at.record.app.find.dailyperform.goout;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
/** OutingTimeOfDailyPerformanceDto Finder */
public class OutingTimeOfDailyPerformanceFinder extends FinderFacade {

	@SuppressWarnings("unchecked")
	@Override
	public OutingTimeOfDailyPerformanceDto find() {
		// TODO Auto-generated method stub
		return new OutingTimeOfDailyPerformanceDto();
	}

}
