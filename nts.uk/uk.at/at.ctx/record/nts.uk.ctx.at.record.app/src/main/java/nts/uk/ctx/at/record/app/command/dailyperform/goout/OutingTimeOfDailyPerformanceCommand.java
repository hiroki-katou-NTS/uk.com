package nts.uk.ctx.at.record.app.command.dailyperform.goout;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class OutingTimeOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<OutingTimeOfDailyPerformanceDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of((OutingTimeOfDailyPerformanceDto) item);
	}

	@Override
	public OutingTimeOfDailyPerformance toDomain() {
		return !data.isPresent() ? null
				: new OutingTimeOfDailyPerformance(getEmployeeId(), getWorkDate(), ConvertHelper.mapTo(
						data.get().getTimeZone(),
						(c) -> new OutingTimeSheet(new OutingFrameNo(c.getWorkNo()), createTimeActual(c.getOuting()),
								new AttendanceTime(c.getOutTimeCalc()), new AttendanceTime(c.getOutTIme()),
								ConvertHelper.getEnum(c.getReason(), GoingOutReason.class),
								createTimeActual(c.getComeBack()))));
	}

	private Optional<TimeActualStamp> createTimeActual(WithActualTimeStampDto c) {
		return c == null ? Optional.empty() : Optional.of(new TimeActualStamp(createWorkStamp(c.getActualTime()), createWorkStamp(c.getTime()),
				c.getNumberOfReflectionStamp()));
	}

	private WorkStamp createWorkStamp(TimeStampDto c) {
		return c == null ? null : new WorkStamp(
				c.getAfterRoundingTimesOfDay() == null ? null : new TimeWithDayAttr(c.getAfterRoundingTimesOfDay()),
				c.getTimesOfDay() == null ? null : new TimeWithDayAttr(c.getTimesOfDay()),
				c.getPlaceCode() == null ? null : new WorkLocationCD(c.getPlaceCode()),
				c.getStampSourceInfo() == null ? StampSourceInfo.HAND_CORRECTION_BY_MYSELF : ConvertHelper.getEnum(c.getStampSourceInfo(), StampSourceInfo.class));
	}
}
