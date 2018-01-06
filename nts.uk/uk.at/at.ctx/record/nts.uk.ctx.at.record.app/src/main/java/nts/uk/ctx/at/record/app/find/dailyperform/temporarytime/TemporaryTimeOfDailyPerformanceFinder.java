package nts.uk.ctx.at.record.app.find.dailyperform.temporarytime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.WorkLeaveTimeDto;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class TemporaryTimeOfDailyPerformanceFinder extends FinderFacade {

	@Inject
	private TemporaryTimeOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public TemporaryTimeOfDailyPerformanceDto find(String employeeId, GeneralDate baseDate) {
		TemporaryTimeOfDailyPerformanceDto dto = new TemporaryTimeOfDailyPerformanceDto();
		TemporaryTimeOfDailyPerformance domain = this.repo.findByKey(employeeId, baseDate).orElse(null);
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setWorkTimes(domain.getWorkTimes().v());
			dto.setWorkLeaveTime(ConvertHelper.mapTo(domain.getTimeLeavingWorks(), (c) -> newWorkLeaveTime(c)));
		}
		return dto;
	}

	private WorkLeaveTimeDto newWorkLeaveTime(TimeLeavingWork c) {
		return new WorkLeaveTimeDto(c.getWorkNo().v(), newTimeWithActual(c.getAttendanceStamp().get()),
					newTimeWithActual(c.getLeaveStamp().get()));
	}

	private WithActualTimeStampDto newTimeWithActual(TimeActualStamp c) {
		return c == null ? null : new WithActualTimeStampDto(
					newTimeStamp(c.getStamp().orElse(null)), 
					newTimeStamp(c.getActualStamp()),
					c.getNumberOfReflectionStamp());
	}

	private TimeStampDto newTimeStamp(WorkStamp c) {
		return c == null ? null : new TimeStampDto(
					c.getTimeWithDay().valueAsMinutes(), 
					c.getAfterRoundingTime().valueAsMinutes(),
					c.getLocationCode().v(),
					c.getStampSourceInfo().value);
	}

}
