package nts.uk.ctx.at.record.app.find.dailyperform.temporarytime;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class TemporaryTimeOfDailyPerformanceFinder extends FinderFacade {

	@SuppressWarnings("unchecked")
	@Override
	public TemporaryTimeOfDailyPerformanceDto find() {
		// TODO Auto-generated method stub
		return new TemporaryTimeOfDailyPerformanceDto();
	}

}
