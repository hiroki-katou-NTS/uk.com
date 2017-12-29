package nts.uk.ctx.at.record.app.command.dailyperform.temporarytime;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TemporaryTimeOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<TemporaryTimeOfDailyPerformanceDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of((TemporaryTimeOfDailyPerformanceDto) item);
	}
	
	@Override
	public TemporaryTimeOfDailyPerformance toDomain() {
		return data.isPresent() ? new TemporaryTimeOfDailyPerformance(getEmployeeId(), new WorkTimes(data.get().getWorkTimes()), ConvertHelper.mapTo(
				data.get().getWorkLeaveTime(),
				(c) -> new TimeLeavingWork(new WorkNo(c.getWorkNo()), new TimeActualStamp(
						new WorkStamp(new TimeWithDayAttr(c.getWorking().getActualTime().getAfterRoundingTimesOfDay()),
								new TimeWithDayAttr(c.getWorking().getActualTime().getTimesOfDay()),
								new WorkLocationCD(c.getWorking().getActualTime().getPlaceCode()),
								ConvertHelper.getEnum(c.getWorking().getActualTime().getStampSourceInfo(),
										StampSourceInfo.class)),
						new WorkStamp(new TimeWithDayAttr(c.getWorking().getTime().getAfterRoundingTimesOfDay()),
								new TimeWithDayAttr(c.getWorking().getTime().getTimesOfDay()),
								new WorkLocationCD(c.getWorking().getTime().getPlaceCode()),
								ConvertHelper.getEnum(c.getWorking().getTime().getStampSourceInfo(),
										StampSourceInfo.class)),
						c.getWorking().getNumberOfReflectionStamp()),
						new TimeActualStamp(
								new WorkStamp(
										new TimeWithDayAttr(c.getLeave().getActualTime().getAfterRoundingTimesOfDay()),
										new TimeWithDayAttr(c.getLeave().getActualTime().getTimesOfDay()),
										new WorkLocationCD(c.getLeave().getActualTime().getPlaceCode()),
										ConvertHelper.getEnum(c.getLeave().getActualTime().getStampSourceInfo(),
												StampSourceInfo.class)),
								new WorkStamp(new TimeWithDayAttr(c.getLeave().getTime().getAfterRoundingTimesOfDay()),
										new TimeWithDayAttr(c.getLeave().getTime().getTimesOfDay()),
										new WorkLocationCD(c.getLeave().getTime().getPlaceCode()),
										ConvertHelper.getEnum(c.getLeave().getTime().getStampSourceInfo(),
												StampSourceInfo.class)),
								c.getLeave().getNumberOfReflectionStamp()))),
				getWorkDate()) : null;
	}
}
