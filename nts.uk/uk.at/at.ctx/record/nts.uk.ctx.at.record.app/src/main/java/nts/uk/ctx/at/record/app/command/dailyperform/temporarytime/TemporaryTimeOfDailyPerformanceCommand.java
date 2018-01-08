package nts.uk.ctx.at.record.app.command.dailyperform.temporarytime;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.WorkLeaveTimeDto;
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
		return !data.isPresent() ? null : new TemporaryTimeOfDailyPerformance(getEmployeeId(), new WorkTimes(data.get().getWorkTimes()),
						ConvertHelper.mapTo(data.get().getWorkLeaveTime(), (c) -> toTimeLeaveWork(c)), getWorkDate());
	}

	private TimeLeavingWork toTimeLeaveWork(WorkLeaveTimeDto c) {
		return c == null ? null : new TimeLeavingWork(new WorkNo(c.getWorkNo()), Optional.of(toTimeActualStamp(c.getWorking())),
				Optional.of(toTimeActualStamp(c.getLeave())));
	}

	private TimeActualStamp toTimeActualStamp(WithActualTimeStampDto c) {
		return c == null ? null : new TimeActualStamp(toWorkStamp(c.getActualTime()), toWorkStamp(c.getTime()),
						c.getNumberOfReflectionStamp());
	}

	private WorkStamp toWorkStamp(TimeStampDto c) {
		return c == null ? null : new WorkStamp(new TimeWithDayAttr(c.getAfterRoundingTimesOfDay()),
						new TimeWithDayAttr(c.getTimesOfDay()), new WorkLocationCD(c.getPlaceCode()),
						ConvertHelper.getEnum(c.getStampSourceInfo(), StampSourceInfo.class));
	}
}
