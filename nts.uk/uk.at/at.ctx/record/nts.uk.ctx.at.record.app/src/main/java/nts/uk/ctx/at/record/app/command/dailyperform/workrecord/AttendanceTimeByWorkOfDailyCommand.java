package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.AttendanceTimeByWorkOfDailyDto;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.ActualWorkTimeSheet;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.AttendanceTimeByWorkOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.WorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.primitive.ActualWorkTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.primitive.WorkFrameNo;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class AttendanceTimeByWorkOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<AttendanceTimeByWorkOfDailyDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of((AttendanceTimeByWorkOfDailyDto) item);
	}
	
	@Override
	public AttendanceTimeByWorkOfDaily toDomain() {
		return !data.isPresent() ? null : new AttendanceTimeByWorkOfDaily(getEmployeeId(), getWorkDate(),
				ConvertHelper.mapTo(data.get().getWorkTimes(),
								c -> new WorkTimeOfDaily(new WorkFrameNo(c.getWorkFrameNo()),
										new ActualWorkTimeSheet(getStamp(c.getTimeSheet().getStart()),
												getStamp(c.getTimeSheet().getEnd())),
										new ActualWorkTime(c.getWorkTime()))));
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
						stamp.getStampSourceInfo() == null ? StampSourceInfo.HAND_CORRECTION_BY_MYSELF : ConvertHelper.getEnum(stamp.getStampSourceInfo(), StampSourceInfo.class));
	}
}
