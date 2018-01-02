package nts.uk.ctx.at.record.app.find.dailyperform.temporarytime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.WorkLeaveTimeDto;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
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
			dto.setWorkLeaveTime(ConvertHelper.mapTo(domain.getTimeLeavingWorks(),
					(c) -> new WorkLeaveTimeDto(c.getWorkNo().v(), new WithActualTimeStampDto(
							new TimeStampDto(c.getAttendanceStamp().getStamp().get().getTimeWithDay().valueAsMinutes(),
									c.getAttendanceStamp().getStamp().get().getAfterRoundingTime().valueAsMinutes(),
									c.getAttendanceStamp().getStamp().get().getLocationCode().v(),
									c.getAttendanceStamp().getStamp().get().getStampSourceInfo().value),
							new TimeStampDto(c.getAttendanceStamp().getActualStamp().getTimeWithDay().valueAsMinutes(),
									c.getAttendanceStamp().getActualStamp().getAfterRoundingTime().valueAsMinutes(),
									c.getAttendanceStamp().getActualStamp().getLocationCode().v(),
									c.getAttendanceStamp().getActualStamp().getStampSourceInfo().value),
							c.getAttendanceStamp().getNumberOfReflectionStamp()),
							new WithActualTimeStampDto(new TimeStampDto(
									c.getLeaveStamp().getStamp().get().getTimeWithDay().valueAsMinutes(),
									c.getLeaveStamp().getStamp().get().getAfterRoundingTime().valueAsMinutes(),
									c.getLeaveStamp().getStamp().get().getLocationCode().v(),
									c.getLeaveStamp().getStamp().get().getStampSourceInfo().value),
									new TimeStampDto(
											c.getLeaveStamp().getActualStamp().getTimeWithDay().valueAsMinutes(),
											c.getLeaveStamp().getActualStamp().getAfterRoundingTime().valueAsMinutes(),
											c.getLeaveStamp().getActualStamp().getLocationCode().v(),
											c.getLeaveStamp().getActualStamp().getStampSourceInfo().value),
									c.getLeaveStamp().getNumberOfReflectionStamp()))));
		}
		return dto;
	}

}
