package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.RestTimeZoneOfDailyDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class BreakTimeOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private List<RestTimeZoneOfDailyDto> data = new ArrayList<>();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data.add((RestTimeZoneOfDailyDto) item);
	}
	
	@Override
	public List<BreakTimeOfDailyPerformance> toDomain() {
		return ConvertHelper.mapTo(this.data, (c) -> new BreakTimeOfDailyPerformance(c.getEmployeeId(),
				EnumAdaptor.valueOf(c.getRestTimeType(), BreakType.class),
				ConvertHelper.mapTo(c.getTimeZone(),
						(d) -> new BreakTimeSheet(new BreakFrameNo(d.getTimeSheetNo()),
								new WorkStamp(new TimeWithDayAttr(d.getStart().getAfterRoundingTimesOfDay()),
										new TimeWithDayAttr(d.getStart().getTimesOfDay()),
										new WorkLocationCD(d.getStart().getPlaceCode()),
										EnumAdaptor.valueOf(d.getStart().getStampSourceInfo(), StampSourceInfo.class)),
								new WorkStamp(new TimeWithDayAttr(d.getEnd().getAfterRoundingTimesOfDay()),
										new TimeWithDayAttr(d.getEnd().getTimesOfDay()),
										new WorkLocationCD(d.getEnd().getPlaceCode()),
										EnumAdaptor.valueOf(d.getEnd().getStampSourceInfo(), StampSourceInfo.class)),
								// TODO: calculate break time
								new AttendanceTime(d.getBreakTime()))),
				c.getYmd()));
	}
}
