package nts.uk.ctx.at.record.app.find.dailyperform.resttime;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.RestTimeZoneOfDailyDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
/** 日別実績の休憩時間帯 Finder */
public class RestTimeZoneOfDailyFinder extends FinderFacade {

	@Inject
	private BreakTimeOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public RestTimeZoneOfDailyDto find(String employeeId, GeneralDate baseDate) {
		RestTimeZoneOfDailyDto dto = new RestTimeZoneOfDailyDto();
		List<BreakTimeOfDailyPerformance> domains = this.repo.findByKey(employeeId, baseDate);
		if (!domains.isEmpty()) {
			BreakTimeOfDailyPerformance domain = domains.get(0);
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setTimeZone(ConvertHelper.mapTo(domain.getBreakTimeSheets(),
					(c) -> new TimeSheetDto(c.getBreakFrameNo().v().intValue(),
							new TimeStampDto(c.getStartTime().getTimeWithDay().valueAsMinutes(),
									c.getStartTime().getAfterRoundingTime().valueAsMinutes(),
									c.getStartTime().getLocationCode().v(),
									c.getStartTime().getStampSourceInfo().value),

							new TimeStampDto(c.getEndTime().getTimeWithDay().valueAsMinutes(),
									c.getEndTime().getAfterRoundingTime().valueAsMinutes(),
									c.getEndTime().getLocationCode().v(),
									c.getEndTime().getStampSourceInfo().value))));
		}
		return dto;
	}

}
