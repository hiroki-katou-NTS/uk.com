package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import java.util.ArrayList;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.WorkLeaveTimeDto;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TimeLeavingOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<TimeLeavingOfDailyPerformanceDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of((TimeLeavingOfDailyPerformanceDto) item);
	}
	
	@Override
	public TimeLeavingOfDailyPerformance toDomain() {
		return !data.isPresent() ? null : new TimeLeavingOfDailyPerformance(
				getEmployeeId(), new WorkTimes(data.get().getWorkTimes()), 
				data.get().getWorkAndLeave() == null ? new ArrayList<>() : 
					ConvertHelper.mapTo(data.get().getWorkAndLeave(), c ->  toTimeLeaveWork(c)),
				getWorkDate());
	}

	private TimeLeavingWork toTimeLeaveWork(WorkLeaveTimeDto c) {
		return c == null ? null : new TimeLeavingWork(new WorkNo(c.getWorkNo()), toTimeActualStamp(c.getWorking()),
				toTimeActualStamp(c.getLeave()));
	}

	private Optional<TimeActualStamp> toTimeActualStamp(WithActualTimeStampDto c) {
		return c == null ? Optional.empty() : 
						Optional.of(new TimeActualStamp(
								toWorkStamp(c.getActualTime()), 
								toWorkStamp(c.getTime()),
								c.getNumberOfReflectionStamp()));
	}

	private WorkStamp toWorkStamp(TimeStampDto c) {
		return c == null ? null : new WorkStamp(
					c.getAfterRoundingTimesOfDay() == null ? null : new TimeWithDayAttr(c.getAfterRoundingTimesOfDay()),
					c.getTimesOfDay() == null ? null : new TimeWithDayAttr(c.getTimesOfDay()), 
					c.getPlaceCode() == null ? null : new WorkLocationCD(c.getPlaceCode()),
					c.getStampSourceInfo() == null ? StampSourceInfo.HAND_CORRECTION_BY_MYSELF : ConvertHelper.getEnum(c.getStampSourceInfo(), StampSourceInfo.class));
	}
}
