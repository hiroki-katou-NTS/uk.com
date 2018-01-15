package nts.uk.ctx.at.record.app.find.dailyperform.goout;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeZoneDto;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
/** OutingTimeOfDailyPerformanceDto Finder */
public class OutingTimeOfDailyPerformanceFinder extends FinderFacade {

	@Inject
	private OutingTimeOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public OutingTimeOfDailyPerformanceDto find(String employeeId, GeneralDate baseDate) {
		OutingTimeOfDailyPerformanceDto dto = new OutingTimeOfDailyPerformanceDto();
		OutingTimeOfDailyPerformance domain = this.repo.findByEmployeeIdAndDate(employeeId, baseDate).orElse(null);
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setTimeZone(ConvertHelper.mapTo(domain.getOutingTimeSheets(),
					(c) -> new OutingTimeZoneDto(
							c.getOutingFrameNo().v(),
							toWithActualTimeStamp(c.getGoOut().orElse(null)),
							toWithActualTimeStamp(c.getComeBack().orElse(null)),
							c.getReasonForGoOut().value, 
							c.getOutingTimeCalculation() == null ? null : c.getOutingTimeCalculation().valueAsMinutes(),
							c.getOutingTime() == null ? null : c.getOutingTime().valueAsMinutes())));
		}
		return dto;
	}

	private WithActualTimeStampDto toWithActualTimeStamp(TimeActualStamp c) {
		return c == null ? null : new WithActualTimeStampDto(
				getTimeStamp(c.getStamp().orElse(null)),
				getTimeStamp(c.getActualStamp()),
				c.getNumberOfReflectionStamp());
	}

	private TimeStampDto getTimeStamp(WorkStamp c) {
		return c == null ? null : new TimeStampDto(
				c.getTimeWithDay() == null ? null : c.getTimeWithDay().valueAsMinutes(),
				c.getAfterRoundingTime() == null ? null : c.getAfterRoundingTime().valueAsMinutes(),
				c.getLocationCode() == null ? null : c.getLocationCode().v(), 
				c.getStampSourceInfo() == null ? null : c.getStampSourceInfo().value);
	}

}
