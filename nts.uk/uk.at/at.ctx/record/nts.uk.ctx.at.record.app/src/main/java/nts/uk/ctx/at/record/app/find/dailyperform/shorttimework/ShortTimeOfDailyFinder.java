package nts.uk.ctx.at.record.app.find.dailyperform.shorttimework;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto.ShortTimeOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto.ShortWorkTimeSheetDto;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.shorttimework.repo.ShortTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class ShortTimeOfDailyFinder extends FinderFacade {

	@Inject
	private ShortTimeOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public ShortTimeOfDailyDto find(String employeeId, GeneralDate baseDate) {
		ShortTimeOfDailyDto result = new ShortTimeOfDailyDto();
		ShortTimeOfDailyPerformance domain = repo.find(employeeId, baseDate).orElse(null);
		if (domain != null) {
			result.setEmployeeId(domain.getEmployeeId());
			result.setYmd(domain.getYmd());
			result.setShortWorkingTimeSheets(ConvertHelper.mapTo(domain.getShortWorkingTimeSheets(),
					(c) -> new ShortWorkTimeSheetDto(c.getShortWorkTimeFrameNo().v(), c.getChildCareAttr().value,
							c.getStartTime() == null ? null : c.getStartTime().valueAsMinutes(),
							c.getEndTime() == null ? null : c.getEndTime().valueAsMinutes(),
							c.getDeductionTime() == null ? null : c.getDeductionTime().valueAsMinutes(),
							c.getShortTime() == null ? null : c.getShortTime().valueAsMinutes())));
		}
		return result;
	}

}
