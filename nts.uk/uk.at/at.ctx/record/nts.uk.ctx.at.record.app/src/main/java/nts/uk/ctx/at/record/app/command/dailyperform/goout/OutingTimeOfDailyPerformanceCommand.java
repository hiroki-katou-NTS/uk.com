package nts.uk.ctx.at.record.app.command.dailyperform.goout;

import java.util.Optional;

import lombok.Getter;
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
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
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
		return data.isPresent()
				? new OutingTimeOfDailyPerformance(getEmployeeId(), getWorkDate(), ConvertHelper.mapTo(data.get()
						.getTimeZone(),
						(c) -> new OutingTimeSheet(new OutingFrameNo(c.getWorkNo()), new TimeActualStamp(
								new WorkStamp(
										new TimeWithDayAttr(c.getOuting().getActualTime().getAfterRoundingTimesOfDay()),
										new TimeWithDayAttr(c.getOuting().getActualTime().getTimesOfDay()),
										new WorkLocationCD(c.getOuting().getActualTime().getPlaceCode()),
										ConvertHelper.getEnum(c.getOuting().getActualTime().getStampSourceInfo(),
												StampSourceInfo.class)),
								new WorkStamp(new TimeWithDayAttr(c.getOuting().getTime().getAfterRoundingTimesOfDay()),
										new TimeWithDayAttr(c.getOuting().getTime().getTimesOfDay()),
										new WorkLocationCD(c.getOuting().getTime().getPlaceCode()),
										ConvertHelper.getEnum(
												c.getOuting().getTime().getStampSourceInfo(), StampSourceInfo.class)),
								c.getOuting().getNumberOfReflectionStamp()), new AttendanceTime(c.getOutTimeCalc()),
								new AttendanceTime(c.getOutTIme()),
								ConvertHelper.getEnum(c.getReason(), GoingOutReason.class),
								new TimeActualStamp(new WorkStamp(
										new TimeWithDayAttr(
												c.getComeBack().getActualTime().getAfterRoundingTimesOfDay()),
										new TimeWithDayAttr(c.getComeBack().getActualTime().getTimesOfDay()),
										new WorkLocationCD(c.getComeBack().getActualTime().getPlaceCode()),
										ConvertHelper.getEnum(c.getComeBack().getActualTime().getStampSourceInfo(),
												StampSourceInfo.class)),
										new WorkStamp(
												new TimeWithDayAttr(
														c.getComeBack().getTime().getAfterRoundingTimesOfDay()),
												new TimeWithDayAttr(c.getComeBack().getTime().getTimesOfDay()),
												new WorkLocationCD(c.getComeBack().getTime().getPlaceCode()),
												ConvertHelper.getEnum(c.getComeBack().getTime().getStampSourceInfo(),
														StampSourceInfo.class)),
										c.getComeBack().getNumberOfReflectionStamp()))))
				: null;
	}
}
