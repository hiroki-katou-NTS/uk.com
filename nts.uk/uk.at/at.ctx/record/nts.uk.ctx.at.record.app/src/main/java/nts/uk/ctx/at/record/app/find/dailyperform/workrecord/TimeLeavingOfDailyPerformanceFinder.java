package nts.uk.ctx.at.record.app.find.dailyperform.workrecord;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.WorkLeaveTimeDto;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
/** 日別実績の出退勤 Finder */
public class TimeLeavingOfDailyPerformanceFinder extends FinderFacade {

	@Inject
	private TimeLeavingOfDailyPerformanceRepository repo;
	
	@SuppressWarnings("unchecked")
	@Override
	public TimeLeavingOfDailyPerformanceDto find(String employeeId, GeneralDate baseDate) {
		TimeLeavingOfDailyPerformanceDto dto = new TimeLeavingOfDailyPerformanceDto();
		TimeLeavingOfDailyPerformance domain = this.repo.findByKey(employeeId, baseDate).orElse(null);
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setWorkTimes(domain.getWorkTimes() != null ? domain.getWorkTimes().v() : null);
			dto.setWorkAndLeave(ConvertHelper.mapTo(domain.getTimeLeavingWorks(),
					(c) -> new WorkLeaveTimeDto(
							c.getWorkNo().v(), 
							getActualTimeStamp(c.getAttendanceStamp().orElse(null)),
							getActualTimeStamp(c.getLeaveStamp().orElse(null)))));
		}
		return dto;
	}

	private WithActualTimeStampDto getActualTimeStamp(TimeActualStamp c) {
		return c == null ? null : new WithActualTimeStampDto(
				getTimeStamp(c.getStamp().orElse(null)),
				getTimeStamp(c.getActualStamp()),
				c.getNumberOfReflectionStamp());
	}

	private TimeStampDto getTimeStamp(WorkStamp c) {
		return c == null ? null : new TimeStampDto(
				c.getTimeWithDay() == null ? null :c.getTimeWithDay().valueAsMinutes(),
				c.getAfterRoundingTime() == null ? null : c.getAfterRoundingTime().valueAsMinutes(),
				c.getLocationCode() == null ? null : c.getLocationCode().v(),
				c.getStampSourceInfo().value);
	}

}
