package nts.uk.ctx.at.record.app.find.dailyperform.workrecord;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.ActualWorkTimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.AttendanceTimeByWorkOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.WorkTimeOfDailyDto;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.AttendanceTimeByWorkOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.repo.AttendanceTimeByWorkOfDailyRepository;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class AttendanceTimeByWorkOfDailyFinder extends FinderFacade {

	@Inject
	private AttendanceTimeByWorkOfDailyRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public AttendanceTimeByWorkOfDailyDto find(String employeeId, GeneralDate baseDate) {
		AttendanceTimeByWorkOfDailyDto dto = new AttendanceTimeByWorkOfDailyDto();
		AttendanceTimeByWorkOfDaily domain = this.repo.find(employeeId, baseDate).orElse(null);
		if(domain != null){
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setWorkTimes(ConvertHelper.mapTo(domain.getWorkTimes(), 
					(c) -> new WorkTimeOfDailyDto(
								c.getWorkFrameNo().v(), 
								new ActualWorkTimeSheetDto(
									new WithActualTimeStampDto(
										getTimeStample(c.getTimeSheet().getStart().getStamp().orElse(null)), 
										getTimeStample(c.getTimeSheet().getStart().getActualStamp()), 
										c.getTimeSheet().getStart().getNumberOfReflectionStamp()), 
									new WithActualTimeStampDto(
											getTimeStample(c.getTimeSheet().getEnd().getStamp().orElse(null)), 
											getTimeStample(c.getTimeSheet().getEnd().getActualStamp()), 
													c.getTimeSheet().getEnd().getNumberOfReflectionStamp())), 
								c.getWorkTime().valueAsMinutes())));
		}
		
		return dto;
	}

	private TimeStampDto getTimeStample(WorkStamp domain) {
		return domain == null ? null
				: new TimeStampDto(domain.getTimeWithDay().valueAsMinutes(),
						domain.getAfterRoundingTime().valueAsMinutes(), domain.getLocationCode().v(),
						domain.getStampSourceInfo().value);

	}

}
