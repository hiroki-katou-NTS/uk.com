package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
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
		if(item != null){
			this.data.add((RestTimeZoneOfDailyDto) item);
		}
	}
	
	@Override
	public List<BreakTimeOfDailyPerformance> toDomain() {
		return ConvertHelper.mapTo(this.data, (c) -> new BreakTimeOfDailyPerformance(this.getEmployeeId(),
				EnumAdaptor.valueOf(c.getRestTimeType(), BreakType.class),
				ConvertHelper.mapTo(c.getTimeZone(),
						(d) -> new BreakTimeSheet(new BreakFrameNo(d.getTimeSheetNo()),
								createWorkStamp(d.getStart()),
								createWorkStamp(d.getEnd()),
								// TODO: calculate break time
								new AttendanceTime(d.getBreakTime()))),
				this.getWorkDate()));
	}

	private WorkStamp createWorkStamp(TimeStampDto d) {
		return d == null ? null : new WorkStamp(
				d.getAfterRoundingTimesOfDay() == null ? null : new TimeWithDayAttr(d.getAfterRoundingTimesOfDay()),
				d.getTimesOfDay() == null ? null : new TimeWithDayAttr(d.getTimesOfDay()),
				d.getPlaceCode() == null ? null : new WorkLocationCD(d.getPlaceCode()),
				d.getStampSourceInfo() == null ? null : EnumAdaptor.valueOf(d.getStampSourceInfo(), StampSourceInfo.class));
	}
}
