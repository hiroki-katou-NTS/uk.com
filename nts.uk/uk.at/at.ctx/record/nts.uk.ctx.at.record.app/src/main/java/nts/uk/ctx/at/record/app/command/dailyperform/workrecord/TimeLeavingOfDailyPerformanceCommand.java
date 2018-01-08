package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
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
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
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
				data.get().getWorkAndLeave() == null ? null : ConvertHelper.mapTo(data.get().getWorkAndLeave(),c -> 
					new TimeLeavingWork(new WorkNo(c.getWorkNo()), Optional.of(getStamp(c.getWorking())), Optional.of(getStamp(c.getLeave())))),
				getWorkDate());
	}

	private TimeActualStamp getStamp(WithActualTimeStampDto stamp) {
		return stamp == null ? null
				: new TimeActualStamp(getWorkStamp(stamp.getActualTime()), getWorkStamp(stamp.getTime()),
						stamp.getNumberOfReflectionStamp());
	}

	private WorkStamp getWorkStamp(TimeStampDto stamp) {
		return stamp == null ? null
				: new WorkStamp(
						stamp.getAfterRoundingTimesOfDay() == null ? null
								: new TimeWithDayAttr(stamp.getAfterRoundingTimesOfDay()),
						stamp.getTimesOfDay() == null ? null : new TimeWithDayAttr(stamp.getTimesOfDay()),
						new WorkLocationCD(stamp.getPlaceCode()),
						ConvertHelper.getEnum(stamp.getStampSourceInfo(), StampSourceInfo.class));
	}
}
