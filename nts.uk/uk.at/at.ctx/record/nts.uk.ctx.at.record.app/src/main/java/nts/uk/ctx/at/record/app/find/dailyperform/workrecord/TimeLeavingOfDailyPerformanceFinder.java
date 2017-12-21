package nts.uk.ctx.at.record.app.find.dailyperform.workrecord;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
/** 日別実績の出退勤 Finder */
public class TimeLeavingOfDailyPerformanceFinder extends FinderFacade {

	@SuppressWarnings("unchecked")
	@Override
	public TimeLeavingOfDailyPerformanceDto find() {
		// TODO Auto-generated method stub
		return new TimeLeavingOfDailyPerformanceDto();
	}

}
