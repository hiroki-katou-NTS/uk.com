package nts.uk.ctx.at.record.app.find.dailyperform.shorttimework;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto.ShortTimeOfDailyDto;
import nts.uk.ctx.at.record.dom.shorttimework.repo.ShortTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class ShortTimeOfDailyFinder extends FinderFacade {

	@Inject
	private ShortTimeOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public ShortTimeOfDailyDto find(String employeeId, GeneralDate baseDate) {
		return ShortTimeOfDailyDto.getDto(this.repo.find(employeeId, baseDate).orElse(null));
	}

}
