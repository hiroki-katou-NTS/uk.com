package nts.uk.ctx.at.record.app.find.dailyperform.goout;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeZoneDto;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
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
			dto.setTimeZone(
					ConvertHelper.mapTo(domain.getOutingTimeSheets(),
							(c) -> new OutingTimeZoneDto(c.getOutingFrameNo().v().intValue(), new WithActualTimeStampDto(
									new TimeStampDto(
											c.getGoOut().getStamp().get().getTimeWithDay().valueAsMinutes(),
											c.getGoOut().getStamp().get().getAfterRoundingTime().valueAsMinutes(), c
													.getGoOut().getStamp().get().getLocationCode().v(),
											c.getGoOut().getStamp().get().getStampSourceInfo().value),
									new TimeStampDto(c.getGoOut().getActualStamp().getTimeWithDay().valueAsMinutes(),
											c.getGoOut().getActualStamp().getAfterRoundingTime().valueAsMinutes(),
											c.getGoOut().getActualStamp().getLocationCode().v(),
											c.getGoOut().getActualStamp().getStampSourceInfo().value),
									c.getGoOut().getNumberOfReflectionStamp()),
									new WithActualTimeStampDto(
											new TimeStampDto(
													c.getComeBack().getStamp().get().getTimeWithDay().valueAsMinutes(),
													c.getComeBack().getStamp().get().getAfterRoundingTime()
															.valueAsMinutes(),
													c.getComeBack().getStamp().get().getLocationCode().v(),
													c.getComeBack().getStamp().get().getStampSourceInfo().value),
											new TimeStampDto(
													c.getComeBack().getActualStamp().getTimeWithDay().valueAsMinutes(),
													c.getComeBack().getActualStamp().getAfterRoundingTime()
															.valueAsMinutes(),
													c.getComeBack().getActualStamp().getLocationCode().v(),
													c.getComeBack().getActualStamp().getStampSourceInfo().value),
											c.getComeBack().getNumberOfReflectionStamp()),
									c.getReasonForGoOut().value)));
		}
		return dto;
	}

}
