package nts.uk.ctx.at.record.app.find.dailyperform.resttime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.RestTimeZoneOfDailyDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
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
		List<BreakTimeOfDailyPerformance> domains = this.repo.findByKey(employeeId, baseDate);
		if (!domains.isEmpty()) {
			toBreakTime(domains.get(0));
		}
		return new RestTimeZoneOfDailyDto();
	}

	private TimeStampDto getTimeStamp(WorkStamp c) {
		return c == null ? null : new TimeStampDto(c.getTimeWithDay().valueAsMinutes(),
				c.getAfterRoundingTime().valueAsMinutes(),
				c.getLocationCode().v(), c.getStampSourceInfo().value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RestTimeZoneOfDailyDto> finds(String employeeId, GeneralDate baseDate) {
		List<BreakTimeOfDailyPerformance> domains = this.repo.findByKey(employeeId, baseDate);
		return domains.stream().map(x -> {
			return toBreakTime(x);
		}).collect(Collectors.toList());
	}

	private RestTimeZoneOfDailyDto toBreakTime(BreakTimeOfDailyPerformance x) {
		RestTimeZoneOfDailyDto dto = new RestTimeZoneOfDailyDto();
		dto.setEmployeeId(x.getEmployeeId());
		dto.setYmd(x.getYmd());
		dto.setRestTimeType(x.getBreakType().value);
		dto.setTimeZone(ConvertHelper.mapTo(x.getBreakTimeSheets(), (c) -> new TimeSheetDto(
				c.getBreakFrameNo().v().intValue(),
				getTimeStamp(c.getStartTime()),
				getTimeStamp(c.getEndTime()),
				c.getBreakTime() == null ? null : c.getBreakTime().valueAsMinutes())));
		return dto;
	}

}
